package com.sqx.pay.business.service;

/**
 * @author chengming
 * @since 2025/4/21
 */
public interface CallbackService {

    /**
     * 支付成功业务
     * @param orderNo 订单号
     * @param tradeNo 第三方流水号
     */
    void successProcess(String orderNo, String tradeNo);

    /**
     * 退款业务
     * @param orderNo 退款
     */
    void refund(String orderNo);
}
