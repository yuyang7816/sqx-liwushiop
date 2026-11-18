package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/19
 */
public enum PaymentWay {
    /**
     * 支付方式
     * wechat: 微信
     * ali: 支付宝
     */
    WECHAT("wechat", "微信"),
    ALI("ali", "支付宝"),
    LOCAL("local", "零钱支付")
    ;

    private final String code;
    private final String desc;

    PaymentWay(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
