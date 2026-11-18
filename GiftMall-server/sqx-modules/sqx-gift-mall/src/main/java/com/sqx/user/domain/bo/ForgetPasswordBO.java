package com.sqx.user.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Data
public class ForgetPasswordBO {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "新密码")
    private String password;
}
