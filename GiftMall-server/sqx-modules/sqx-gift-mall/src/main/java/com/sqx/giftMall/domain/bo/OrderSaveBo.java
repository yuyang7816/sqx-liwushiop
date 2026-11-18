package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class OrderSaveBo implements Serializable {
    private static final long serialVersionUID = -1066848244267131239L;

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
     * 商品个数
     */
    @ApiModelProperty("商品个数")
    private Integer number;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long productId;

    /**
     * 商品类型id
     */
    @ApiModelProperty("商品类型id")
    private Long typeId;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal price;

    /**
     * 商品skuid
     */
    @ApiModelProperty("商品skuid")
    private Long skuId;

    /**
     * 商品sku信息
     */
    @ApiModelProperty("商品sku信息")
    private String skuDetailJson;

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
     * 祝福语
     */
    @ApiModelProperty("祝福语")
    private String blessing;

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
}
