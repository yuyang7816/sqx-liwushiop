package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import com.sqx.domain.repository.entity.giftMall.GiftProductAttr;
import com.sqx.domain.repository.entity.giftMall.GiftProductAttrValue;
import com.sqx.domain.repository.service.giftMall.IGiftProductAttrService;
import com.sqx.domain.repository.mapper.giftMall.GiftProductAttrMapper;
import com.sqx.domain.repository.service.giftMall.IGiftProductAttrValueService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【gift_product_attr(商品属性)】的数据库操作Service实现
* @createDate 2024-07-16 17:22:00
*/
@Service
public class IGiftProductAttrServiceImpl extends ServiceImpl<GiftProductAttrMapper, GiftProductAttr>
    implements IGiftProductAttrService {
    @Resource
    private IGiftProductAttrValueService giftProductAttrValueService;

    @Override
    public List<GiftProductAttrDto> findByProductId(Long id) {
        QueryWrapper<GiftProductAttr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttr::getProductId, id);
        List<GiftProductAttr> productAttrList = this.list(queryWrapper);

        List<GiftProductAttrDto> giftProductAttrDtoList = new ArrayList<>();
        for (GiftProductAttr productAttr : productAttrList) {
            List<GiftProductAttrValue> giftProductAttrValueList = giftProductAttrValueService.findByProductId(id);

            GiftProductAttrDto dto = new GiftProductAttrDto();
            BeanUtils.copyProperties(productAttr, dto);
            dto.setGiftProductAttrValueList(giftProductAttrValueList);
            giftProductAttrDtoList.add(dto);
        }

        return giftProductAttrDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveBody(GiftProductAttrDto giftProductAttrDto) {
        GiftProductAttr giftProductAttr = new GiftProductAttr();
        BeanUtils.copyProperties(giftProductAttrDto, giftProductAttr);
        this.save(giftProductAttr);

        List<GiftProductAttrValue> giftProductAttrValueList = giftProductAttrDto.getGiftProductAttrValueList();

        List<GiftProductAttrValue> giftProductAttrValues = giftProductAttrValueList.stream().peek(item -> {
            item.setProductId(giftProductAttr.getProductId());
            item.setAttrId(giftProductAttr.getId());
        }).collect(Collectors.toList());

        giftProductAttrValueService.saveBatch(giftProductAttrValues);

        return Result.success(giftProductAttr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateBody(Long productId, List<GiftProductAttrDto> giftProductAttrDtoList) {
        if (CollectionUtils.isNotEmpty(giftProductAttrDtoList)) {
            // 修改商品规格信息
            List<GiftProductAttr> giftProductAttrList = giftProductAttrDtoList.stream().map(item -> {
                GiftProductAttr attr = new GiftProductAttr();
                BeanUtils.copyProperties(item, attr);

                return attr;
            }).collect(Collectors.toList());

            this.updateBatchById(giftProductAttrList);

            // 修改商品规格值
            GiftProductAttrDto attrDto = giftProductAttrDtoList.get(0);

            QueryWrapper<GiftProductAttrValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(GiftProductAttrValue::getProductId, productId);
            giftProductAttrValueService.remove(queryWrapper);

            List<GiftProductAttrValue> giftProductAttrValueList = attrDto.getGiftProductAttrValueList();
            List<GiftProductAttrValue> giftProductAttrValues = giftProductAttrValueList.stream().peek(item -> {
                item.setProductId(productId);
                item.setAttrId(attrDto.getId());
            }).collect(Collectors.toList());

            giftProductAttrValueService.saveBatch(giftProductAttrValues);
        }

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAttrAndValue(Long productId) {
        // 删除所有attr
        QueryWrapper<GiftProductAttr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttr::getProductId, productId);
        this.remove(queryWrapper);

        // 删除所有value
        IGiftProductAttrServiceImpl currentProxy = (IGiftProductAttrServiceImpl) AopContext.currentProxy();
        currentProxy.removeAttrValueByProductId(productId);
    }

    /**
     * 删除所有value
     * @param productId 商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeAttrValueByProductId(Long productId) {
        QueryWrapper<GiftProductAttrValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttrValue::getProductId, productId);

        giftProductAttrValueService.remove(queryWrapper);
    }
}




