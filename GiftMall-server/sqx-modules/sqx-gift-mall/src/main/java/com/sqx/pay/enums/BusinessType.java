package com.sqx.pay.enums;

/**
 * @author chengming
 * @since 2025/4/21
 * 支付业务类型
 */
public enum BusinessType {
    VIP("vip", "会员"),
    RECHARGE("recharge", "充值余额"),
    ORDER("order", "订单"),
    ;

    private final String code;
    private final String desc;

    BusinessType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
