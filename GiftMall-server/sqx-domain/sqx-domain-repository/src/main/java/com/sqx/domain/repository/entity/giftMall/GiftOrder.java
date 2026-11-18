package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 * @TableName gift_order
 */
@TableName(value ="gift_order")
@Data
public class GiftOrder implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 支付单号
     */
    @TableField(value = "pay_no")
    private String payNo;

    /**
     * 订单备注
     */
    @TableField(value = "description")
    private String description;

    /**
     * 商品个数
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 支付金额
     */
    @TableField(value = "pay_money")
    private BigDecimal payMoney;

    /**
     * 订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 支付平台 1微信 2支付宝 3paypal 4stripe 5余额
     */
    @TableField(value = "pay_way")
    private Integer payWay;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 完成时间
     */
    @TableField(value = "finish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;

    /**
     * 是否可以退款（1可以退款 2不可退款）
     */
    @TableField(value = "refund_support")
    private Integer refundSupport;

    /**
     * 退款理由
     */
    @TableField(value = "refund_reason")
    private String refundReason;

    /**
     * 拒绝退款理由
     */
    @TableField(value = "refund_refused_reason")
    private String refundRefusedReason;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 商品类型id
     */
    @TableField(value = "type_id")
    private Long typeId;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 商品skuid
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 商品sku信息
     */
    @TableField(value = "sku_detail_json")
    private String skuDetailJson;

    /**
     * 订单类型（1普通订单 2拼团 3秒杀）
     */
    @TableField(value = "order_type")
    private Integer orderType;

    /**
     * 用户优惠券id
     */
    @TableField(value = "user_coupons_id")
    private Long userCouponsId;

    /**
     * 优惠券优惠金额
     */
    @TableField(value = "user_coupon_money")
    private BigDecimal userCouponMoney;

    /**
     * 收货人
     */
    @TableField(value = "consignee")
    private String consignee;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 省市区
     */
    @TableField(value = "provinces")
    private String provinces;

    /**
     * 详细地址
     */
    @TableField(value = "address_detail")
    private String addressDetail;

    /**
     * 邮费
     */
    @TableField(value = "postage_price")
    private BigDecimal postagePrice;

    /**
     * 快递名称
     */
    @TableField(value = "express_name")
    private String expressName;

    /**
     * 快递单号
     */
    @TableField(value = "express_number")
    private String expressNumber;

    /**
     * 发货时间
     */
    @TableField(value = "express_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expressTime;

    /**
     * 祝福语
     */
    @TableField(value = "blessing")
    private String blessing;

    /**
     * 卡片id
     */
    @TableField(value = "card_id")
    private Long cardId;

    /**
     * 卡片图片地址
     */
    @TableField(value = "card_img")
    private String cardImg;

    /**
     * 接收人id
     */
    @TableField(value = "give_user_id")
    private Long giveUserId;

    /**
     * 接收人名称
     */
    @TableField(value = "give_user_name")
    private String giveUserName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "省")
    private String province;

    @ApiModelProperty(name = "市")
    private String city;

    @ApiModelProperty(name = "区/县")
    private String district;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}