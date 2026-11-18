package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class PaymentBo implements Serializable {
    private static final long serialVersionUID = 1849517262813504824L;

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("订单id")
    @NotNull(message = "请选择订单")
    private Long orderId;

    @ApiModelProperty("支付渠道 wechat: 微信 ali: 支付宝 local: 零钱支付")
    @NotNull(message = "请选择支付渠道")
    private String paymentWay;

    @ApiModelProperty("支付类型 1App支付 2Web支付 3jsApi")
    @NotNull(message = "请选择支付类型")
    private Integer paymentType;
}
