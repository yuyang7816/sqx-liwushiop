package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/18
 */
public enum CouponIssueStatus {

    /**
     * 优惠券发布状态（0开启 1关闭）
     */
    ENABLE(0, "开启"),
    DISABLE(1, "关闭")
    ;

    private final Integer code;
    private final String desc;

    CouponIssueStatus(Integer code, String desc) {
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
