package com.sqx.giftMall.service;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.OrderListBo;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.giftMall.domain.bo.*;
import com.sqx.giftMall.domain.vo.GiftOrderVo;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/7/19
 */
public interface OrderService {
    Result page(Integer page, Integer limit, OrderListBo orderListBo);

    Result selfList(Integer page, Integer limit, Long userId, Integer status);

    Result selfRefundList(Integer page, Integer limit, Long userId);

    Result save(OrderSaveBo orderSaveBo);

    Result saveList(OrderSaveListBo orderSaveListBo);

    Result orderCancel(Long orderId);

    Result express(OrderExpressBo orderExpressBo);

    Result obtain(ObtainBo obtainBo);

    Result refuse(Long id);

    Result confirm(Long id);

    Result pay(PaymentBo paymentBo);

    void orderNotify(Long orderId, BigDecimal money, String way);

    Result purchased(Integer page, Integer limit, Long userId, Integer status);

    Result received(Integer page, Integer limit, Long userId, Integer status);

    Result<GiftOrderVo> info(Long orderId);

    void give(GiveOrderBo giveOrderBo);

    GiftShare share(ShareOrderBo shareOrderBo);

    Result use(UseOrderBo useOrderBo);

    void bindCard(BingCardBo bingCardBo);

    Result updateExpress(OrderExpressBo orderExpressBo);

    JSONObject getLogistics(String expressNumber);
}
