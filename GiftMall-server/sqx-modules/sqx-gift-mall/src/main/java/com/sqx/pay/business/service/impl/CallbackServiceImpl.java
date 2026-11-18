package com.sqx.pay.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.app.UserMoneyDetails;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.PayDetails;
import com.sqx.domain.repository.mapper.app.UserMoneyDao;
import com.sqx.domain.repository.mapper.app.UserMoneyDetailsDao;
import com.sqx.domain.repository.mapper.giftMall.PayDetailsMapper;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.giftMall.enums.OrderStatus;
import com.sqx.giftMall.enums.UserMoneyDetailsClassify;
import com.sqx.pay.business.service.CallbackService;
import com.sqx.pay.enums.BusinessType;
import com.sqx.referral.user.service.ReferralUserCommissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/21
 *
 * 回调业务
 */
@Slf4j
@Service
public class CallbackServiceImpl implements CallbackService {

    @Resource
    private PayDetailsMapper payDetailsMapper;
    @Resource
    private UserMoneyDao userMoneyDao;
    @Resource
    private UserMoneyDetailsDao userMoneyDetailsDao;
    @Resource
    private IGiftOrderService iGiftOrderService;
    @Resource
    private ReferralUserCommissionService referralUserCommissionService;

    @Override
    public void successProcess(String orderNo, String tradeNo) {
        log.info("回调业务处理");

        PayDetails payDetails = getPayDetails(orderNo);
        if (0 != payDetails.getState()) {
            return;
        }

        if (Objects.equals(payDetails.getBusinessType(), BusinessType.RECHARGE.getCode())) {
            handlerRecharge(payDetails, tradeNo);
        } else if (Objects.equals(payDetails.getBusinessType(), BusinessType.VIP.getCode())) {

        } else if (Objects.equals(payDetails.getBusinessType(), BusinessType.ORDER.getCode())) {
            handlerOrder(payDetails, tradeNo);
        } else {
            throw new BizException("不支持的业务类型");
        }
    }

    @Override
    public void refund(String orderNo) {
        GiftOrder giftOrder = iGiftOrderService.getByOrderNo(orderNo);
        if (Objects.isNull(giftOrder)) {
            log.error("退款失败，订单不存在： {}", orderNo);
            throw new BizException("退款失败，订单不存在");
        }

        // 修改订单状态
        giftOrder.setStatus(OrderStatus.REFUNDED.code());
        iGiftOrderService.updateById(giftOrder);

        // 余额支付记录钱包详情
        if (5 == giftOrder.getPayWay()) {
            userMoneyDao.incr(giftOrder.getUserId(), giftOrder.getPayMoney());

            UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
            userMoneyDetails.setUserId(giftOrder.getUserId());
            userMoneyDetails.setMoney(giftOrder.getPayMoney());
            userMoneyDetails.setTitle("订单退款");
            userMoneyDetails.setContent(String.format("订单【%s】退款【%s】元", orderNo, giftOrder.getPayMoney()));
            userMoneyDetails.setClassify(4);
            userMoneyDetails.setType(1);
            userMoneyDetails.setState(2);
            userMoneyDetails.setCreateTime(LocalDateTime.now());

            userMoneyDetailsDao.insert(userMoneyDetails);
        }
    }

    /**
     * 订单回调业务
     * @param payDetails 支付详情
     * @param tradeNo 第三方流水号
     */
    @Transactional(rollbackFor = Exception.class)
    public void handlerOrder(PayDetails payDetails, String tradeNo) {
        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftOrder::getOrderNo, payDetails.getOrderNo());
        GiftOrder giftOrder = iGiftOrderService.getOne(queryWrapper);
        if (Objects.isNull(giftOrder)) {
            log.error("订单不存在");
            return;
        }

        giftOrder.setPayWay(payDetails.getPlatform());
        giftOrder.setPayTime(LocalDateTime.now());
        giftOrder.setStatus(OrderStatus.WAIT_FOR_GIVE.code());
        iGiftOrderService.updateById(giftOrder);

        // 更新支付详情
        UpdateWrapper<PayDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(PayDetails::getOrderNo, payDetails.getOrderNo())
                .set(PayDetails::getTradeNo, tradeNo)
                .set(PayDetails::getState, 1)
                .set(PayDetails::getPayTime, LocalDateTime.now());

        payDetailsMapper.update(new PayDetails(), updateWrapper);

        // 佣金
        referralUserCommissionService.saveUserCommission(giftOrder.getId());
    }

    /**
     * 充值余额回调业务
     * @param payDetails 支付详情
     */
    @Transactional(rollbackFor = Exception.class)
    public void handlerRecharge(PayDetails payDetails, String tradeNo) {
        // 更新支付详情
        UpdateWrapper<PayDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(PayDetails::getOrderNo, payDetails.getOrderNo())
                .set(PayDetails::getTradeNo, tradeNo)
                .set(PayDetails::getState, 1)
                .set(PayDetails::getPayTime, LocalDateTime.now());

        payDetailsMapper.update(new PayDetails(), updateWrapper);

        // 新增用户余额
        userMoneyDao.incr(payDetails.getUserId(), payDetails.getAmount());

        // 保存用户钱包详情
        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(payDetails.getUserId());
        userMoneyDetails.setMoney(payDetails.getAmount());
        userMoneyDetails.setTitle("充值余额");
        userMoneyDetails.setContent(String.format("充值余额【%s】元", payDetails.getAmount()));
        userMoneyDetails.setClassify(UserMoneyDetailsClassify.RECHARGE.getCode());
        userMoneyDetails.setType(1);
        userMoneyDetails.setState(2);
        userMoneyDetails.setCreateTime(LocalDateTime.now());

        userMoneyDetailsDao.insert(userMoneyDetails);
    }

    private PayDetails getPayDetails(String orderNo) {
        QueryWrapper<PayDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayDetails::getOrderNo, orderNo);
        PayDetails payDetails = payDetailsMapper.selectOne(queryWrapper);
        if (Objects.isNull(payDetails)) {
            throw new BizException("支付详情不存在");
        }

        return payDetails;
    }
}
