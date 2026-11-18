package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.domain.giftMall.dto.GiftUserCouponDto;
import com.sqx.domain.repository.entity.giftMall.GiftCoupon;
import com.sqx.domain.repository.entity.giftMall.GiftCouponIssue;
import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.sqx.domain.repository.mapper.giftMall.GiftUserCouponMapper;
import com.sqx.domain.repository.service.giftMall.IGiftCouponIssueService;
import com.sqx.domain.repository.service.giftMall.IGiftCouponService;
import com.sqx.domain.repository.service.giftMall.IGiftUserCouponService;
import com.sqx.giftMall.domain.bo.UserCouponSaveBo;
import com.sqx.giftMall.domain.vo.UserCouponVo;
import com.sqx.giftMall.enums.CouponIssueStatus;
import com.sqx.giftMall.enums.CouponStatus;
import com.sqx.giftMall.enums.UserCouponStatus;
import com.sqx.giftMall.service.UserCouponService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {
    @Resource
    private GiftUserCouponMapper giftUserCouponMapper;
    @Resource
    private IGiftUserCouponService giftUserCouponService;
    @Resource
    private IGiftCouponService giftCouponService;
    @Resource
    private IGiftCouponIssueService giftCouponIssueService;

    @Override
    public Result list(Integer page, Integer limit, String couponName) {
        Page<GiftUserCoupon> initPage = PageUtil.initPage(page, limit);

        IPage<GiftUserCouponDto> iPage = giftUserCouponMapper.list(initPage, couponName);
        return Result.success(iPage);
    }

    @Override
    public Result receivedCouponList(Integer page, Integer limit, Long userId) {
        List<UserCouponVo> voList = new ArrayList<>();

        Page<GiftUserCoupon> initPage = PageUtil.initPage(page, limit);
        QueryWrapper<GiftUserCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCoupon::getUserId, userId)
                .eq(GiftUserCoupon::getStatus, UserCouponStatus.UNUSED.code());

        IPage<GiftUserCoupon> couponPage = giftUserCouponService.page(initPage, queryWrapper);
        if (CollectionUtils.isNotEmpty(couponPage.getRecords())) {
            // 查询优惠券信息
            List<Long> couponIdList = couponPage.getRecords().stream().map(GiftUserCoupon::getCouponId).collect(Collectors.toList());
            List<GiftCoupon> couponList = getCouponList(couponIdList);
            Map<Long, GiftCoupon> couponMap = couponList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

            for (GiftUserCoupon userCoupon : couponPage.getRecords()) {
                UserCouponVo vo = new UserCouponVo();
                BeanUtils.copyProperties(userCoupon, vo);

                // 填充优惠券信息
                if (couponMap.containsKey(userCoupon.getCouponId())) {
                    vo.setGiftCoupon(couponMap.get(userCoupon.getCouponId()));
                }

                voList.add(vo);
            }
        }

        return Result.success(voList);
    }

    @Override
    public Result save(UserCouponSaveBo userCouponSaveBo) {
        GiftUserCoupon userCoupon = new GiftUserCoupon();

        // 优惠券信息
        GiftCoupon giftCoupon = giftCouponService.getById(userCouponSaveBo.getCouponId());
        checkGiftCoupon(giftCoupon);

        // 优惠券发布信息
        GiftCouponIssue giftCouponIssue = giftCouponIssueService.getById(userCouponSaveBo.getCouponIssueId());
        checkCouponIssue(giftCouponIssue);

        userCoupon.setCouponId(userCouponSaveBo.getCouponId());
        userCoupon.setUserId(userCouponSaveBo.getUserId());
        userCoupon.setCouponIssueId(userCouponSaveBo.getCouponIssueId());
        userCoupon.setStatus(UserCouponStatus.UNUSED.code());
        userCoupon.setType(giftCoupon.getType());
        userCoupon.setFailureTime(fillCouponFailureTime(giftCoupon.getValidDay()));

        giftUserCouponService.save(userCoupon);

        return Result.success();
    }

    private void checkCouponIssue(GiftCouponIssue giftCouponIssue) {
        if (Objects.isNull(giftCouponIssue)) {
            throw new SqxException("优惠券未发布");
        }

        if (LocalDateTime.now().isBefore(giftCouponIssue.getStartTime())) {
            throw new SqxException("优惠券尚未开始领取");
        }

        if (LocalDateTime.now().isAfter(giftCouponIssue.getEndTime())) {
            throw new SqxException("优惠券发布时间已结束");
        }

        if (0 == giftCouponIssue.getRemainNumber()) {
            throw new SqxException("优惠券已被领完");
        }

        if (Objects.equals(giftCouponIssue.getStatus(), CouponIssueStatus.DISABLE.code())) {
            throw new SqxException("优惠券未发布");
        }
    }

    private void checkGiftCoupon(GiftCoupon giftCoupon) {
        if (Objects.isNull(giftCoupon)) {
            throw new SqxException("优惠券不存在");
        }

        if (Objects.equals(giftCoupon.getStatus(), CouponStatus.DISABLE.code())) {
            throw new SqxException("优惠券已失效，不能领取");
        }
    }

    /**
     * 填充优惠券过期时间
     * @param validDay 优惠券有效期
     * @return
     */
    private LocalDateTime fillCouponFailureTime(Integer validDay) {
        return LocalDateTime.now().plusDays(validDay);
    }

    /**
     * 查询优惠券信息
     * @param couponIdList 优惠券id列表
     * @return
     */
    private List<GiftCoupon> getCouponList(List<Long> couponIdList) {
        QueryWrapper<GiftCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftCoupon::getStatus, CouponStatus.ENABLE.code())
                .in(GiftCoupon::getId, couponIdList);

        return giftCouponService.list(queryWrapper);
    }
}
