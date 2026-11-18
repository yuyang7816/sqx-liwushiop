package com.sqx.auth.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/6/9
 */
@Data
public class ResetPasswordBO {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "新密码")
    private String password;
}
