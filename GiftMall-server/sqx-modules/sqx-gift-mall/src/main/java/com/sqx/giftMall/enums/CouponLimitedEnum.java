package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/18
 */
public enum CouponLimitedEnum {
    /**
     * 是否限量（1限量 2不限量）
     */
    LIMITED(1, "限量"),
    UNLIMITED(2, "不限量")
    ;

    private final Integer code;
    private final String desc;

    CouponLimitedEnum(Integer code, String desc) {
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
