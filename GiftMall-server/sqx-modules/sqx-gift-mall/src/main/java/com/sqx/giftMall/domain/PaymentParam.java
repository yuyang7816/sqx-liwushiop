package com.sqx.giftMall.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class PaymentParam {

    /**
     * 微信用户openId
     * 微信支付类型为2Web支付 3jsApi必传
     */
    private String openId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 微信支付类型 类型 1App支付 2Web支付 3jsApi 4支付宝App支付 5支付宝Web支付
     */
    private Integer paymentType;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付方式 wx: 微信 ali: 支付宝
     */
    private String payWay;
}
