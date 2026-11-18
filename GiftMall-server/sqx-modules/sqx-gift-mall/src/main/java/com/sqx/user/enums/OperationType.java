package com.sqx.user.enums;

/**
 * @author chengming
 * @since 2025/9/11
 *
 * 金额操作美剧
 */
public enum OperationType {
    INCR("incr", "增加"),
    DECR("decr", "减少")
    ;

    private final String code;
    private final String desc;

    OperationType(String code, String desc) {
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
