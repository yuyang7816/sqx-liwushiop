package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/17
 */
public enum SpecialOfferEnum {
    /**
     * 是否特价商品 0是 1不是
     */
    YES(0, "是"),
    NO(1, "不是")
    ;

    private final Integer code;
    private final String desc;

    SpecialOfferEnum(Integer code, String desc) {
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
