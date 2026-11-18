package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftCoupon;
import com.sqx.domain.repository.entity.giftMall.GiftCouponIssue;
import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.sqx.domain.repository.service.giftMall.IGiftCouponIssueService;
import com.sqx.domain.repository.service.giftMall.IGiftCouponService;
import com.sqx.domain.repository.service.giftMall.IGiftUserCouponService;
import com.sqx.giftMall.domain.bo.CouponIssueListBo;
import com.sqx.giftMall.domain.bo.CouponIssueSaveBo;
import com.sqx.giftMall.domain.bo.CouponIssueUpdateBo;
import com.sqx.giftMall.domain.vo.GiftCouponIssueVo;
import com.sqx.giftMall.enums.*;
import com.sqx.giftMall.service.CouponIssueService;
import com.sqx.giftMall.utils.PageUtil;
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
public class CouponIssueServiceImpl implements CouponIssueService {
    @Resource
    private IGiftCouponIssueService giftCouponIssueService;
    @Resource
    private IGiftUserCouponService giftUserCouponService;
    @Resource
    private IGiftCouponService giftCouponService;

    @Override
    public Result page(Integer page, Integer limit, CouponIssueListBo couponIssueListBo) {
        Page<GiftCouponIssue> initPage = PageUtil.initPage(page, limit);

        QueryWrapper<GiftCouponIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(couponIssueListBo.getLimited()),GiftCouponIssue::getLimited, couponIssueListBo.getLimited())
                .eq(Objects.nonNull(couponIssueListBo.getStatus()), GiftCouponIssue::getStatus, couponIssueListBo.getStatus());

        Page<GiftCouponIssue> couponIssuePage = giftCouponIssueService.page(initPage, queryWrapper);
        return Result.success(couponIssuePage);
    }

    @Override
    public Result info(Long id) {
        GiftCouponIssue giftCouponIssue = giftCouponIssueService.getById(id);
        if (Objects.isNull(giftCouponIssue)) {
            throw new SqxException("优惠券发布信息不存在");
        }

        return Result.success(giftCouponIssue);
    }

    @Override
    public Result save(CouponIssueSaveBo couponIssueSaveBo) {
        checkCouponIssueTime(couponIssueSaveBo.getStartTime(), couponIssueSaveBo.getEndTime());

        GiftCouponIssue giftCouponIssue = new GiftCouponIssue();
        BeanUtils.copyProperties(couponIssueSaveBo, giftCouponIssue);
        giftCouponIssue.setRemainNumber(couponIssueSaveBo.getNumber());
        giftCouponIssue.setStatus(CouponIssueStatus.ENABLE.code());

        if (!giftCouponIssueService.save(giftCouponIssue)) {
            throw new SqxException("保存失败");
        }

        return Result.success();
    }

    @Override
    public Result update(CouponIssueUpdateBo couponIssueUpdateBo) {
        checkCouponIssueTime(couponIssueUpdateBo.getStartTime(), couponIssueUpdateBo.getEndTime());
        if (!checkCouponIssueExistById(couponIssueUpdateBo.getId())) {
            throw new SqxException("优惠券发布信息不存在");
        }

        GiftCouponIssue giftCouponIssue = new GiftCouponIssue();
        BeanUtils.copyProperties(couponIssueUpdateBo, giftCouponIssue);
        if (!giftCouponIssueService.updateById(giftCouponIssue)) {
            throw new SqxException("修改失败");
        }

        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if (!checkCouponIssueExistById(id)) {
            throw new SqxException("优惠券发布信息不存在");
        }

        giftCouponIssueService.removeById(id);
        return Result.success();
    }

    @Override
    public Result useList(Long productId, Long userId) {
        List<GiftCouponIssueVo> voList = new ArrayList<GiftCouponIssueVo>();

        QueryWrapper<GiftCouponIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .gt(GiftCouponIssue::getRemainNumber, 0)
                .ge(GiftCouponIssue::getStartTime, LocalDateTime.now())
                .le(GiftCouponIssue::getEndTime, LocalDateTime.now())
                .eq(GiftCouponIssue::getStatus,CouponIssueStatus.ENABLE.code());

        List<GiftCouponIssue> couponIssueList = giftCouponIssueService.list(queryWrapper);

        // 查询用户领券信息
        List<GiftUserCoupon> userCouponList = getUserCouponList(userId);
        Map<Long, GiftUserCoupon> userCouponMap = userCouponList.stream().collect(HashMap::new, (map, item) -> map.put(item.getCouponId(), item), HashMap::putAll);

        // 查询优惠券信息
        List<GiftCoupon> couponList = getCouponList(couponIssueList);
        Map<Long, GiftCoupon> couponMap = couponList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

        // 筛选可领取的券
        for (GiftCouponIssue couponIssue : couponIssueList) {
            GiftCouponIssueVo vo = new GiftCouponIssueVo();
            BeanUtils.copyProperties(couponIssue, vo);

            // 是否已领取
            if (userCouponMap.containsKey(couponIssue.getCouponId()) && Objects.equals(couponIssue.getLimited(), CouponLimitedEnum.LIMITED.code())) {
                vo.setUserGetCouponStatus(UserGetCouponStatus.RECEIVED.code());
            }

            // 填充优惠券信息
            if (couponMap.containsKey(couponIssue.getCouponId())) {
                vo.setGiftCoupon(couponMap.get(couponIssue.getCouponId()));
            }

            voList.add(vo);
        }

        return Result.success(voList);
    }

    /**
     * 查询优惠券信息
     * @return
     */
    private List<GiftCoupon> getCouponList(List<GiftCouponIssue> couponIssueList) {
        List<Long> couponIdList = couponIssueList.stream()
                .map(GiftCouponIssue::getCouponId)
                .collect(Collectors.toList());

        QueryWrapper<GiftCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftCoupon::getStatus, CouponStatus.ENABLE.code())
                .in(GiftCoupon::getId, couponIdList);

        return giftCouponService.list(queryWrapper);
    }

    /**
     * 查询用户领券信息
     * @param userId 用户id
     * @return
     */
    private List<GiftUserCoupon> getUserCouponList(Long userId) {
        QueryWrapper<GiftUserCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftUserCoupon::getUserId, userId)
                .eq(GiftUserCoupon::getStatus, UserCouponStatus.UNUSED.code());

       return giftUserCouponService.list(queryWrapper);
    }

    /**
     * 校验优惠券发布信息是否存在
     * @param id
     * @return
     */
    private boolean checkCouponIssueExistById(Long id) {
        QueryWrapper<GiftCouponIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCouponIssue::getId, id);

        return giftCouponIssueService.exists(queryWrapper);
    }

    private void checkCouponIssueTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new SqxException("领券结束日期不能早于领券开始日期");
        }
    }
}
