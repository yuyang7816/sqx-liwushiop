package com.sqx.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2025/4/21
 * 基础支付参数
 */
@Data
public class BasePayParam {

    @NotNull(message = "客户端id不能为空")
    @ApiModelProperty(value = "客户端id")
    private String clientId;
}
