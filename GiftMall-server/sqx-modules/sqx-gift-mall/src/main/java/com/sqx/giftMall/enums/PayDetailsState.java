package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/22
 */
public enum PayDetailsState {
    /**
     * 支付详情 0待支付 1支付成功 2支付失败
     */
    WAIT(0, "待支付"),
    SUCCESS(1, "支付成功"),
    FAILED(2, "支付失败"),
    ;
    private final Integer code;
    private final String desc;

    PayDetailsState(Integer code, String desc) {
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
