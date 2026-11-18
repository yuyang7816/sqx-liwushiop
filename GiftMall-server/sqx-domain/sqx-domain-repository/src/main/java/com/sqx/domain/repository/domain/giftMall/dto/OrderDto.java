package com.sqx.domain.repository.domain.giftMall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 803200107756882532L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 支付单号
     */
    @ApiModelProperty("支付单号")
    private Long payNo;

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
     * 订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送）
     */
    @ApiModelProperty("订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送）")
    private Integer status;

    /**
     * 支付方式（1app微信 2微信公众号 3微信小程序 4app支付宝 5H5支付宝 6零钱）
     */
    @ApiModelProperty("支付方式（1app微信 2微信公众号 3微信小程序 4app支付宝 5H5支付宝 6零钱）")
    private Integer payWay;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 完成时间
     */
    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;

    /**
     * 是否可以退款（1可以退款 2不可退款）
     */
    @ApiModelProperty("是否可以退款（1可以退款 2不可退款）")
    private Integer refundSupport;

    /**
     * 退款理由
     */
    @ApiModelProperty("退款理由")
    private String refundReason;

    /**
     * 拒绝退款理由
     */
    @ApiModelProperty("拒绝退款理由")
    private String refundRefusedReason;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productImg;

    /**
     * 商品类型id
     */
    @ApiModelProperty("商品类型id")
    private Long typeId;

    @ApiModelProperty("商品类型名称")
    private String typeName;

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

    @ApiModelProperty("用户优惠券名称")
    private String userCouponsName;

    /**
     * 优惠券优惠金额
     */
    @ApiModelProperty("优惠券优惠金额")
    private BigDecimal userCouponMoney;

    /**
     * 收货人
     */
    @ApiModelProperty("收货人")
    private String consignee;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 省市区
     */
    @ApiModelProperty("省市区")
    private String provinces;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String addressDetail;

    /**
     * 邮费
     */
    @ApiModelProperty("邮费")
    private BigDecimal postagePrice;

    /**
     * 快递名称
     */
    @ApiModelProperty("快递名称")
    private String expressName;

    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    private String expressNumber;

    /**
     * 发货时间
     */
    @ApiModelProperty("发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expressTime;

    /**
     * 祝福语
     */
    @ApiModelProperty("祝福语")
    private String blessing;

    /**
     * 卡片id
     */
    @ApiModelProperty("祝福语")
    private Long cardId;

    /**
     * 卡片图片地址
     */
    @ApiModelProperty("卡片图片地址")
    private String cardImg;

    /**
     * 接收人id
     */
    @ApiModelProperty("接收人id")
    private Long giveUserId;

    /**
     * 接收人名称
     */
    @ApiModelProperty("接收人名称")
    private String giveUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("产品列表")
    List<GiftOrderProductVo> productList;
}
