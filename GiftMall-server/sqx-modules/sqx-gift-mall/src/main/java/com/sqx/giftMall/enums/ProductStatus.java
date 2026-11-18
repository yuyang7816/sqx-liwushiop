package com.sqx.giftMall.enums;

/**
 * @author chengming
 * @since 2024/7/17
 */
public enum ProductStatus {
    /**
     * 商品状态(默认0正常 1下架)
     */
    ENABLE(0, "正常"),
    DISABLE(1, "下架"),
    OTHERS(3, "商品sku不存在时状态")
    ;

    private final Integer code;
    private final String desc;

    ProductStatus(Integer code, String desc) {
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
