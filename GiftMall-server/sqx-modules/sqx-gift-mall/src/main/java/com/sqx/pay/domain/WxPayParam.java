package com.sqx.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author chengming
 * @since 2025/4/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxPayParam extends BasePayParam {

    @NotBlank(message = "交易类型不能为空")
    @ApiModelProperty(value = "交易类型 JSAPI-公众号支付 NATIVE-原生扫码支付 APP-app支付")
    private String tradeType;

    @ApiModelProperty(value = "openid")
    private String openId;
}
