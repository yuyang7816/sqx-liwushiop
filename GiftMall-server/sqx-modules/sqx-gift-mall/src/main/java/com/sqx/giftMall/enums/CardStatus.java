package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/15
 */
public enum CardStatus {
    ENABLE(0, "启用"),
    DISABLE(1, "禁用")
    ;

    private final Integer code;
    private final String desc;

    CardStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return this.code;
    }

    public String desc() {
        return this.desc;
    }
}
