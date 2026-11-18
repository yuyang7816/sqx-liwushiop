package com.sqx.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author chengming
 * @since 2025/4/22
 *
 * 业务参数
 */
@Data
public class BusinessParam {

    @NotBlank(message = "业务类型不能为空")
    @ApiModelProperty(value = "业务类型 vip: 购买会员 recharge: 充值余额 order: 订单")
    private String businessType;

    @NotBlank(message = "支付成功后重定向uri不能为空")
    @ApiModelProperty(value = "支付成功后重定向uri")
    private String redirectUri;

    @NotNull(message = "订单金额不能为空")
    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;

    @NotBlank(message = "支付类型 不能为空")
    @ApiModelProperty(value = "支付类型  应用：APP 网页：WEB 小程序：JSAPI")
    private String tradeType;

    @ApiModelProperty(value = "微信支付参数（微信支付时必填）")
    private WxPayParam wxPayParam;

    @ApiModelProperty(value = "支付宝支付参数（支付宝支付时必填）")
    private AliPayParam aliPayParam;

    private Long userId;

    /* 其他业务参数 */
    @ApiModelProperty(value = "其他业务参数")
    private Map<String, Object> extensions;
}
