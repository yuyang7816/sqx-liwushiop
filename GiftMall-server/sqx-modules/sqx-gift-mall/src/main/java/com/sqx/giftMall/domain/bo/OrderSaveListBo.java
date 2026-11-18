package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class OrderSaveListBo implements Serializable {
    private static final long serialVersionUID = 4979776923484602300L;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 订单备注
     */
    @ApiModelProperty("订单备注")
    private String description;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    /**
     * 订单类型（1普通订单 2拼团 3秒杀）
     */
    @ApiModelProperty("订单类型（1普通订单 2拼团 3秒杀）")
    private Integer orderType;

    /**
     * 用户优惠券id
     */
    @ApiModelProperty("用户优惠券id")
    private Long userCouponsId;

    /**
     * 优惠券优惠金额
     */
    @ApiModelProperty("优惠券优惠金额")
    private BigDecimal userCouponMoney;

    /**
     * 卡片id
     */
    @ApiModelProperty("卡片id")
    private Long cardId;

    /**
     * 卡片图片地址
     */
    @ApiModelProperty("卡片图片地址")
    private String cardImg;

    @ApiModelProperty("商品信息")
    private List<ProductInfo> productInfoList;

    @Data
    public static class ProductInfo {
        @ApiModelProperty("商品id")
        private Long productId;

        @ApiModelProperty("商品价格")
        private BigDecimal productPrice;

        @ApiModelProperty("商品数量")
        private Integer num;

        @ApiModelProperty("商品类型id")
        private Long typeId;

        @ApiModelProperty("商品skuid")
        private Long skuId;

        @ApiModelProperty("商品sku信息")
        private String skuDetailJson;
    }
}
