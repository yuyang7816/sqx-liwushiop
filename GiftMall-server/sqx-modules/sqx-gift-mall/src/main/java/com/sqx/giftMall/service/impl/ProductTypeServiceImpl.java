package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftProductType;
import com.sqx.domain.repository.service.giftMall.IGiftProductTypeService;
import com.sqx.giftMall.domain.bo.ProductTypeListBo;
import com.sqx.giftMall.domain.bo.ProductTypeSaveBo;
import com.sqx.giftMall.domain.bo.ProductTypeUpdateBo;
import com.sqx.giftMall.service.ProductTypeService;
import com.sqx.giftMall.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    private IGiftProductTypeService giftProductTypeService;

    @Override
    public Result list(Integer page, Integer limit, ProductTypeListBo productTypeListBo) {
        Page<GiftProductType> initPage = PageUtil.initPage(page, limit);

        QueryWrapper<GiftProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(GiftProductType::getSort);

        Page<GiftProductType> typePage = giftProductTypeService.page(initPage, queryWrapper);
        return Result.success(typePage);
    }

    @Override
    public Result info(Long id) {
        GiftProductType giftProductType = giftProductTypeService.getById(id);
        if (Objects.isNull(giftProductType)) {
            throw new SqxException("商品分类不存在");
        }

        return Result.success(giftProductType);
    }

    @Override
    public Result save(ProductTypeSaveBo productTypeSaveBo) {
        if (checkTypeNameExist(productTypeSaveBo.getName())) {
            throw new SqxException("商品分类名称已存在");
        }

        GiftProductType giftProductType = new GiftProductType();
        BeanUtils.copyProperties(productTypeSaveBo, giftProductType);
        giftProductTypeService.save(giftProductType);

        return Result.success();
    }

    @Override
    public Result update(ProductTypeUpdateBo productTypeUpdateBo) {
        if (!checkTypeExistById(productTypeUpdateBo.getId())){
            throw new SqxException("商品分类不存在");
        }

        if (checkTypeNameExistExcludeSelf(productTypeUpdateBo.getId(), productTypeUpdateBo.getName())) {
            throw new SqxException("商品分类名称已存在");
        }

        GiftProductType giftProductType = new GiftProductType();
        BeanUtils.copyProperties(productTypeUpdateBo, giftProductType);

        giftProductTypeService.updateById(giftProductType);
        return Result.success();
    }

    private boolean checkTypeNameExistExcludeSelf(Long id, String name) {
        QueryWrapper<GiftProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftProductType::getName, name)
                .ne(GiftProductType::getId, id);

        return giftProductTypeService.exists(queryWrapper);
    }

    @Override
    public Result delete(Long id) {
        if (!checkTypeExistById(id)){
            throw new SqxException("商品分类不存在");
        }

        giftProductTypeService.removeById(id);
        return Result.success();
    }

    @Override
    public Result listNoPage(ProductTypeListBo productTypeListBo) {

        QueryWrapper<GiftProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(GiftProductType::getSort);

        List<GiftProductType> typeList = giftProductTypeService.list(queryWrapper);
        return Result.success(typeList);
    }

    /**
     * 校验商品分类是否存在
     * @param id 商品分类id
     * @return
     */
    private boolean checkTypeExistById(Long id) {
        QueryWrapper<GiftProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductType::getId, id);

        return giftProductTypeService.exists(queryWrapper);
    }

    /**
     * 校验商品分类名称是否存在
     * @param name 商品分类名称
     * @return
     */
    private boolean checkTypeNameExist(String name) {
        QueryWrapper<GiftProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductType::getName, name);

        return giftProductTypeService.exists(queryWrapper);
    }
}
