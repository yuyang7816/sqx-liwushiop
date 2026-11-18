package com.sqx.giftMall.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/19
 */
public enum OrderStatus {
    /**
     * 订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送 13已领取 14已拒绝 16待发货 17已开发票）
     */
    WAIT_FOR_PAY(1, "待付款"),
    HAVE_PAY(2, "已付款"),
    SHIPPED(3, "已发货"),
    RECEIVED(4, "已收货"),
    CANCEL(5, "已取消"),
    BEING_REFUNDED(6, "退款中"),
    REFUNDED(7, "已退款"),
    GROUP(9, "拼团中"),
    COMMENTED(10, "已评价"),
    WAIT_FOR_GIVE(11, "待赠送"),
    GIVEED(12, "已赠送"),
    OBTAINED(13, "已领取"),
    REFUSE(14, "已拒绝"),
    WAIT_SHIP(16, "待发货"),
    INVOICE(17, "已开发票"),
    ;

    private final Integer code;
    private final String desc;

    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 根据 code 获取描述
    public static String getDescByCode(Integer code) {
        for (OrderStatus status : values()) {
            if (Objects.equals(status.code(), code)) {
                return status.desc();
            }
        }

        throw new IllegalArgumentException("错误的订单状态: " + code);
    }

    public static List<Integer> allStatus() {
        return Arrays.stream(OrderStatus.values())
                .map(e -> e.code)
                .collect(Collectors.toList());
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
