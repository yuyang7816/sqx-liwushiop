package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/18
 */
public enum UserCouponStatus {

    /**
     * 用户优惠券状态（1未使用 2已使用 3已过期）
     */
    UNUSED(1, "1未使用"),
    USED(2, "已使用"),
    EXPIRED(3, "已过期")
    ;

    private final Integer code;
    private final String desc;

    UserCouponStatus(Integer code, String desc) {
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
