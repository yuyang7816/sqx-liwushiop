package com.sqx.domain.repository.service.giftMall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_order(订单)】的数据库操作Service
* @createDate 2024-07-19 09:40:05
*/
public interface IGiftOrderService extends IService<GiftOrder> {

    /**
     * 订单支付回调
     * @param orderId
     * @param money
     * @param way
     */
    void orderNotify(Long orderId, BigDecimal money, String way);

    GiftOrder getByOrderNo(String orderNo);

    BigDecimal sum(List<Integer> statusList, LocalDateTime startTime, LocalDateTime endTime);
}
