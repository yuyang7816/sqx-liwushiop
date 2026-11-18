package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/18
 */
public enum UserGetCouponStatus {

    /**
     * 用户领取优惠券状态 0已领取 1未领取
     */
    RECEIVED(0, "已领取"),
    NOT_RECEIVE(1, "未领取")
    ;

    private final Integer code;
    private final String desc;

    UserGetCouponStatus(Integer code, String desc) {
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
