package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付详情
 * @TableName pay_details
 */
@TableName(value ="pay_details")
@Data
public class PayDetails implements Serializable {
    @ApiModelProperty(value = "充值id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "支付平台 1微信 2支付宝 3paypal 4stripe 5余额")
    @TableField("platform")
    private Integer platform;

    @ApiModelProperty(value = "支付类型 应用：APP 网页：WEB 小程序：JSAPI")
    @TableField("trade_type")
    private String tradeType;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "第三方平台支付流水号")
    @TableField("trade_no")
    private String tradeNo;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "业务类型 vip: 购买会员 recharge: 充值余额 order: 订单")
    @TableField("business_type")
    private String businessType;

    @ApiModelProperty(value = "0待支付 1支付成功 2失败")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "支付时间")
    @TableField("pay_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "扩展字段")
    @TableField("extend")
    private String extend;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}