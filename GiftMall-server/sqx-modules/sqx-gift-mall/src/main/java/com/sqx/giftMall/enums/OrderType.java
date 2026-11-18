package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/19
 */
public enum OrderType {
    /**
     * 订单类型（1普通订单 2拼团 3秒杀)
     */
    GENERAL(1, "普通订单"),
    GROUP(2, "拼团"),
    SEC_KILL(3, "秒杀"),
    ;

    private final Integer code;
    private final String desc;

    OrderType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
