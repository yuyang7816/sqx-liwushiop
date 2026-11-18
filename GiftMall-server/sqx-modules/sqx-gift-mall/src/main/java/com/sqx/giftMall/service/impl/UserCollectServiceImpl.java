package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftUserCollect;
import com.sqx.domain.repository.service.giftMall.IGiftProductService;
import com.sqx.domain.repository.service.giftMall.IGiftUserCollectService;
import com.sqx.giftMall.domain.bo.UserCollectSaveBo;
import com.sqx.giftMall.domain.bo.UserCollectUpdateBo;
import com.sqx.giftMall.domain.vo.GiftUserCollectVo;
import com.sqx.giftMall.service.UserCollectService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Service
public class UserCollectServiceImpl implements UserCollectService {
    @Resource
    private IGiftUserCollectService giftUserCollectService;
    @Resource
    private IGiftProductService giftProductService;

    @Override
    public Result page(Integer page, Integer limit, Long userId) {
        Page<GiftUserCollect> initPage = PageUtil.initPage(page, limit);
        List<GiftUserCollectVo> voList = new ArrayList<>();

        QueryWrapper<GiftUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCollect::getUserId, userId)
                .orderByDesc(GiftUserCollect::getCreateTime);

        Page<GiftUserCollect> collectPage = giftUserCollectService.page(initPage, queryWrapper);
        if (CollectionUtils.isNotEmpty(collectPage.getRecords())) {
            for (GiftUserCollect userCollect : collectPage.getRecords()) {
                GiftUserCollectVo vo = new GiftUserCollectVo();
                BeanUtils.copyProperties(userCollect, vo);

                GiftProduct giftProduct = giftProductService.getById(userCollect.getProductId());
                if (Objects.nonNull(giftProduct)) {
                    vo.setGiftProduct(giftProduct);
                }

                voList.add(vo);
            }
        }

        IPage<GiftUserCollectVo> iPage = PageUtil.copyPage(collectPage, voList);
        return Result.success(iPage);
    }

    @Override
    public Result info(Long id) {
        GiftUserCollect giftUserCollect = giftUserCollectService.getById(id);
        if (Objects.isNull(giftUserCollect)) {
            throw new SqxException("收藏信息不存在");
        }

        return Result.success(giftUserCollect);
    }

    @Override
    public Result save(UserCollectSaveBo userCollectSaveBo) {
        GiftUserCollect userCollect = new GiftUserCollect();
        BeanUtils.copyProperties(userCollectSaveBo, userCollect);

        if (!giftUserCollectService.save(userCollect)) {
           throw new SqxException("收藏失败");
        }

        return Result.success();
    }

    @Override
    public Result update(UserCollectUpdateBo userCollectUpdateBo) {
        if (!checkUserCollectExistById(userCollectUpdateBo.getId())) {
            throw new SqxException("收藏信息不存在");
        }

        UpdateWrapper<GiftUserCollect> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftUserCollect::getId, userCollectUpdateBo.getId())
                .eq(GiftUserCollect::getUserId, userCollectUpdateBo.getUserId())
                .set(Objects.nonNull(userCollectUpdateBo.getProductId()), GiftUserCollect::getProductId, userCollectUpdateBo.getProductId());

        giftUserCollectService.update(new GiftUserCollect(), updateWrapper);

        return Result.success();
    }

    @Override
    public Result delete(Long userId, Long productId) {
        QueryWrapper<GiftUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCollect::getUserId, userId)
                .eq(GiftUserCollect::getProductId, productId);

        giftUserCollectService.remove(queryWrapper);
        return Result.success();
    }

    @Override
    public Result check(Long productId, Long userId) {
        QueryWrapper<GiftUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCollect::getUserId, userId)
                .eq(GiftUserCollect::getProductId, productId);

        return Result.success(giftUserCollectService.exists(queryWrapper));
    }

    /**
     * 校验用户收藏是否存在
     * @param id 用户收藏id
     * @return
     */
    private boolean checkUserCollectExistById(Long id) {
        QueryWrapper<GiftUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftUserCollect::getId, id);

        return giftUserCollectService.exists(queryWrapper);
    }
}
