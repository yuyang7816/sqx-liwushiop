package com.sqx.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.domain.repository.service.giftMall.IGiftShareService;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.giftMall.enums.OrderStatus;
import com.sqx.pay.service.AlipayService;
import com.sqx.pay.service.LocalpayService;
import com.sqx.pay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2025/10/13
 * <p>
 * 订单定时任务
 */
@Slf4j
@Component
public class OrderScheduled {

    @Resource
    private IGiftOrderService iGiftOrderService;
    @Resource
    private CommonInfoService commonInfoService;
    @Resource
    private IGiftShareService iGiftShareService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private AlipayService alipayService;
    @Resource
    private LocalpayService localpayService;

    @Scheduled(cron = "0 */5 * * * *")
    public void shareOrderTimeOut() {
        String shareTimeout = commonInfoService.getCommonValue(324);
        LocalDateTime timeout = LocalDateTime.now().minusHours(Long.parseLong(shareTimeout));

        // 查询所有未领取的分享订单
        List<GiftOrder> timeoutOrderList = getTimeoutOrder(timeout);
        if (CollectionUtils.isEmpty(timeoutOrderList)) {
            return;
        }

        for (GiftOrder order : timeoutOrderList) {
            // 修改分享状态
            iGiftShareService.update(new GiftShare(), new LambdaUpdateWrapper<GiftShare>()
                    .eq(GiftShare::getOrderNo, order.getOrderNo())
                    .set(GiftShare::getStatus, 5));

            // 修改订单状态
            iGiftOrderService.update(new GiftOrder(), new LambdaUpdateWrapper<GiftOrder>()
                    .eq(GiftOrder::getOrderNo, order.getOrderNo())
                    .set(GiftOrder::getStatus, OrderStatus.REFUNDED.code()));

            // 退款 TODO
            if (1 == order.getPayWay()) {

            } else if (2 == order.getPayWay()) {

            } else if (5 == order.getPayWay()) {
                localpayService.refund(order.getOrderNo());
            }
        }
    }

    private List<GiftOrder> getTimeoutOrder(LocalDateTime timeout) {
        LambdaQueryWrapper<GiftOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftOrder::getStatus, OrderStatus.WAIT_FOR_GIVE.code())
                .lt(GiftOrder::getPayTime, timeout);

        return iGiftOrderService.list(queryWrapper);
    }

    private List<GiftShare> getTimeoutShare(LocalDateTime timeout) {
        LambdaQueryWrapper<GiftShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftShare::getStatus, 1)
                .lt(GiftShare::getCreateTime, timeout);

        return iGiftShareService.list(queryWrapper);
    }
}
