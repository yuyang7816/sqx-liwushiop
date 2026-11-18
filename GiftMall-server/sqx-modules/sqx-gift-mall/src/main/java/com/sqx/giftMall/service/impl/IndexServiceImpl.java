package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.bo.RechargeRecordBo;
import com.sqx.domain.repository.domain.giftMall.dto.RechargeRecordVo;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.domain.repository.entity.giftMall.GiftUserCollect;
import com.sqx.domain.repository.entity.giftMall.PayDetails;
import com.sqx.domain.repository.mapper.app.UserMoneyDetailsDao;
import com.sqx.domain.repository.mapper.giftMall.GiftCartMapper;
import com.sqx.domain.repository.mapper.giftMall.PayDetailsMapper;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.domain.repository.service.giftMall.IGiftShareService;
import com.sqx.domain.repository.service.giftMall.IGiftUserCollectService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.domain.bo.StatisticsBo;
import com.sqx.giftMall.domain.vo.*;
import com.sqx.giftMall.enums.OrderStatus;
import com.sqx.giftMall.service.IndexService;
import com.sqx.giftMall.utils.DateUtil;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.giftMall.utils.model.StartAndEndTime;
import com.sqx.pay.enums.BusinessType;
import com.sqx.pay.enums.PlatformEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2025/6/27
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private IGiftUserCollectService iGiftUserCollectService;
    @Resource
    private IGiftShareService iGiftShareService;
    @Resource
    private GiftCartMapper giftCartMapper;
    @Resource
    private UserMoneyDetailsDao userMoneyDetailsDao;
    @Resource
    private PayDetailsMapper payDetailsMapper;
    @Resource
    private IUserService iUserService;
    @Resource
    private IGiftOrderService iGiftOrderService;

    @Override
    public MyDataVo myData(Long userId) {
        // 收藏
        long collectNum = iGiftUserCollectService.count(new LambdaQueryWrapper<GiftUserCollect>().eq(GiftUserCollect::getUserId, userId));

        // 收到的礼物
        long receiveGiftNum = iGiftShareService.count(new LambdaQueryWrapper<GiftShare>().eq(GiftShare::getReceiveUserId, userId));

        // 购物车商品数
        long cartNum = giftCartMapper.sum(userId);

        MyDataVo vo = new MyDataVo();
        vo.setCollectNum(collectNum);
        vo.setReceiveGiftNum(receiveGiftNum);
        vo.setCartNum(cartNum);
        return vo;
    }

    @Override
    public RechargeStatisticsVo rechargeStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange(statisticsBo.getFindDate(), statisticsBo.getFindType());

        LambdaQueryWrapper<PayDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PayDetails::getBusinessType, BusinessType.RECHARGE.getCode())
                .eq(PayDetails::getState, 1)
                .between(PayDetails::getCreateTime, statisticsBo.getStartTime(), statisticsBo.getEndTime());
        List<PayDetails> payDetailsList = payDetailsMapper.selectList(queryWrapper);

        Map<Integer, BigDecimal> platformMap = payDetailsList.stream()
                .collect(Collectors.groupingBy(PayDetails::getPlatform,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                PayDetails::getAmount,
                                BigDecimal::add
                        )
                ));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<RechargeStatisticsVo.RechargeStatistics> rechargeStatisticsList = new ArrayList<>();
        for (String code : PlatformEnum.allCode()) {
            if (PlatformEnum.LOCAL.getCode().equals(code)) {
                continue;
            }

            RechargeStatisticsVo.RechargeStatistics rechargeStatistics = new RechargeStatisticsVo.RechargeStatistics();
            rechargeStatistics.setCode(code);
            rechargeStatistics.setDesc(PlatformEnum.getDescByCode(code));
            rechargeStatistics.setAmount(platformMap.getOrDefault(Integer.valueOf(code), BigDecimal.ZERO));

            rechargeStatisticsList.add(rechargeStatistics);
            totalAmount = totalAmount.add(rechargeStatistics.getAmount());
        }

        RechargeStatisticsVo vo = new RechargeStatisticsVo();
        vo.setRechargeRecords(rechargeStatisticsList);
        vo.setTotalAmount(totalAmount);
        return vo;
    }

    @Override
    public IPage<RechargeRecordVo> rechargeRecord(RechargeRecordBo rechargeRecordBo) {
        Page<PayDetails> initPage = PageUtil.initPage(rechargeRecordBo.getPage(), rechargeRecordBo.getLimit());

        IPage<RechargeRecordVo> voIPage = payDetailsMapper.rechargeRecord(initPage, rechargeRecordBo);
        return voIPage;
    }

    @Override
    public IndexStatisticsVo indexStatistics() {
        Long totalUserCount = getTotalUserCount();
        Long todayUserCount = getTodayUserCount();
        Long monthUserCount = getMonthUserCount();
        Long yearUserCount = getYearUserCount();

        Long dayOrderCount = getDayOrderCount();
        BigDecimal dayAmount = getDayAmount();
        BigDecimal monthAmount = getMonthAmount();
        BigDecimal yearAmount = getYearAmount();

        IndexStatisticsVo vo = new IndexStatisticsVo();
        vo.setTotalUserCount(totalUserCount);
        vo.setTodayUserCount(todayUserCount);
        vo.setMonthUserCount(monthUserCount);
        vo.setYearUserCount(yearUserCount);
        vo.setDayOrderCount(dayOrderCount);
        vo.setDayAmount(dayAmount);
        vo.setMonthAmount(monthAmount);
        vo.setYearAmount(yearAmount);

        return vo;
    }

    @Override
    public UserStatisticsVo userStatistics(StatisticsBo statisticsBo) {
        statisticsBo.initFormattedDateRange(statisticsBo.getFindDate(), statisticsBo.getFindType());

        Long totalUserCount = getUserCount(statisticsBo.getStartTime(), statisticsBo.getEndTime());

        UserStatisticsVo vo = new UserStatisticsVo();
        vo.setTotalCount(totalUserCount);
        return vo;
    }

    @Override
    public OrderStatisticsVo orderStatistics(StatisticsBo statisticsBo) {
        List<GiftOrder> orderList = iGiftOrderService.list();

        Map<Integer, Long> map = orderList.stream().collect(Collectors.groupingBy(GiftOrder::getStatus, Collectors.counting()));

        Long totalCount = 0L;
        List<OrderStatisticsVo.OrderStatistics> orderStatisticsList = new ArrayList<>();
        for (Integer status : OrderStatus.allStatus()) {
            OrderStatisticsVo.OrderStatistics orderStatistics = new OrderStatisticsVo.OrderStatistics();
            orderStatistics.setStatus(status);
            orderStatistics.setDesc(OrderStatus.getDescByCode(status));

            orderStatistics.setCount(map.getOrDefault(status, 0L));

            orderStatisticsList.add(orderStatistics);
            totalCount += orderStatistics.getCount();
        }

        OrderStatisticsVo vo = new OrderStatisticsVo();
        vo.setOrderStatisticsList(orderStatisticsList);
        vo.setTotalCount(totalCount);
        return vo;
    }

    private Long getUserCount(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startTime, endTime);

        return iUserService.count(queryWrapper);
    }

    private BigDecimal getYearAmount() {
        StartAndEndTime startAndEndTime = DateUtil.year();
        List<Integer> statusList = Arrays.asList(OrderStatus.WAIT_FOR_PAY.code(), OrderStatus.REFUNDED.code());

        return iGiftOrderService.sum(statusList, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());
    }

    private BigDecimal getMonthAmount() {
        StartAndEndTime startAndEndTime = DateUtil.month();
        List<Integer> statusList = Arrays.asList(OrderStatus.WAIT_FOR_PAY.code(), OrderStatus.REFUNDED.code());

        return iGiftOrderService.sum(statusList, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());
    }

    private BigDecimal getDayAmount() {
        StartAndEndTime startAndEndTime = DateUtil.today();
        List<Integer> statusList = Arrays.asList(OrderStatus.WAIT_FOR_PAY.code(), OrderStatus.REFUNDED.code());

        return iGiftOrderService.sum(statusList, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());
    }

    private Long getDayOrderCount() {
        StartAndEndTime startAndEndTime = DateUtil.today();

        LambdaQueryWrapper<GiftOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(GiftOrder::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iGiftOrderService.count(queryWrapper);
    }

    private Long getYearUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.year();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getMonthUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.month();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getTodayUserCount() {
        StartAndEndTime startAndEndTime = DateUtil.today();

        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(UserEntity::getCreateTime, startAndEndTime.getStartTime(), startAndEndTime.getEndTime());

        return iUserService.count(queryWrapper);
    }

    private Long getTotalUserCount() {
        return iUserService.count();
    }
}
