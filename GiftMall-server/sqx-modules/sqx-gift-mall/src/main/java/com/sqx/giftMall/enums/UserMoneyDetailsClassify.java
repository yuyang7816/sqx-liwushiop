package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2025/6/18
 */
public enum UserMoneyDetailsClassify {

    ORDER(2, "购买"),
    RECHARGE(3, "充值余额"),

    ;

    private Integer code;
    private String desc;

    UserMoneyDetailsClassify(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
