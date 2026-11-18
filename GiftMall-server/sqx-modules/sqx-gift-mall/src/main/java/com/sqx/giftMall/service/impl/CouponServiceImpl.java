package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftCoupon;
import com.sqx.domain.repository.entity.giftMall.GiftCouponIssue;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.sqx.domain.repository.service.giftMall.IGiftCouponIssueService;
import com.sqx.domain.repository.service.giftMall.IGiftCouponService;
import com.sqx.domain.repository.service.giftMall.IGiftProductService;
import com.sqx.domain.repository.service.giftMall.IGiftUserCouponService;
import com.sqx.giftMall.domain.bo.CouponListBo;
import com.sqx.giftMall.domain.bo.CouponSaveBo;
import com.sqx.giftMall.domain.bo.CouponUpdateBo;
import com.sqx.giftMall.domain.vo.GiftCouponInfoVo;
import com.sqx.giftMall.enums.CouponStatus;
import com.sqx.giftMall.enums.UserCouponStatus;
import com.sqx.giftMall.service.CouponService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private IGiftCouponService giftCouponService;
    @Resource
    private IGiftProductService giftProductService;
    @Resource
    private IGiftCouponIssueService giftCouponIssueService;
    @Resource
    private IGiftUserCouponService giftUserCouponService;

    @Override
    public Result page(Integer page, Integer limit, CouponListBo couponListBo) {
        Page<GiftCoupon> initPage = PageUtil.initPage(page, limit);

        QueryWrapper<GiftCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(couponListBo.getType()), GiftCoupon::getType, couponListBo.getType())
                .eq(Objects.nonNull(couponListBo.getStatus()), GiftCoupon::getStatus, couponListBo.getStatus())
                .like(StringUtils.isNotBlank(couponListBo.getCouponName()),GiftCoupon::getCouponName, couponListBo.getCouponName())
                .orderByAsc(GiftCoupon::getSort);

        Page<GiftCoupon> couponPage = giftCouponService.page(initPage, queryWrapper);
        return Result.success(couponPage);
    }

    @Override
    public Result info(Long id) {
        GiftCoupon giftCoupon = giftCouponService.getById(id);
        if (Objects.isNull(giftCoupon)) {
            throw new SqxException("优惠券信息不存在");
        }

        String[] productIds = giftCoupon.getProductIds().split(",");
        List<GiftProduct> productList = getProductListByIds(productIds);

        GiftCouponInfoVo vo = new GiftCouponInfoVo();
        BeanUtils.copyProperties(giftCoupon, vo);
        vo.setProductList(productList);

        return Result.success(vo);
    }

    @Override
    public Result save(CouponSaveBo couponSaveBo) {
        GiftCoupon giftCoupon = new GiftCoupon();
        BeanUtils.copyProperties(couponSaveBo, giftCoupon);
        giftCoupon.setStatus(CouponStatus.ENABLE.code());
        if (Objects.isNull(couponSaveBo.getSort())) {
            giftCoupon.setSort(99);
        }

        giftCouponService.save(giftCoupon);
        return Result.success();
    }

    @Override
    public Result update(CouponUpdateBo couponUpdateBo) {
        if (!checkCouponExistById(couponUpdateBo.getId())) {
            throw new SqxException("优惠券信息不存在");
        }

        GiftCoupon giftCoupon = new GiftCoupon();
        BeanUtils.copyProperties(couponUpdateBo, giftCoupon);
        giftCouponService.updateById(giftCoupon);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if (!checkCouponExistById(id)) {
            throw new SqxException("优惠券信息不存在");
        }

        // 校验优惠券是否发布中
        if (checkCouponIssueExist(id)) {
            throw new SqxException("优惠券发布中，不能删除");
        }

        // 校验优惠券是否已被用户领取且未核销
        if (checkUserHasCoupon(id)) {
            throw new SqxException("优惠券存在未核销用户，不能删除");
        }

        giftCouponService.removeById(id);
        return Result.success();
    }

    /**
     * 校验优惠券是否已被用户领取且未核销
     * @param couponId 优惠券id
     * @return
     */
    private boolean checkUserHasCoupon(Long couponId) {
        QueryWrapper<GiftUserCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCoupon::getCouponId, couponId)
                .eq(GiftUserCoupon::getStatus, UserCouponStatus.UNUSED.code());

        return giftUserCouponService.exists(queryWrapper);
    }

    /**
     * 校验优惠券是否发布中
     * @param couponId 优惠券id
     * @return
     */
    private boolean checkCouponIssueExist(Long couponId) {
        QueryWrapper<GiftCouponIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftCouponIssue::getCouponId, couponId)
                .gt(GiftCouponIssue::getRemainNumber, 0);

        return giftCouponIssueService.exists(queryWrapper);
    }

    /**
     * 校验优惠券信息是否存在
     * @param id 优惠券id
     * @return
     */
    private boolean checkCouponExistById(Long id) {
        QueryWrapper<GiftCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCoupon::getId, id);

        return giftCouponService.exists(queryWrapper);
    }

    /**
     * 获取商品列表
     * @param productIds 商品id列表
     * @return
     */
    private List<GiftProduct> getProductListByIds(String[] productIds) {
        List<String> productIdList = Arrays.stream(productIds).collect(Collectors.toList());

        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GiftProduct::getId, productIdList);

        return giftProductService.list(queryWrapper);
    }
}
