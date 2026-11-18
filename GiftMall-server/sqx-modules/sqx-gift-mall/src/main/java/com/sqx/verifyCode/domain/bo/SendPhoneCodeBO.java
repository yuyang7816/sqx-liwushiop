package com.sqx.verifyCode.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Data
public class SendPhoneCodeBO {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "业务码", example = "common")
    @NotBlank(message = "业务码不能为空")
    private String bizCode;
}
