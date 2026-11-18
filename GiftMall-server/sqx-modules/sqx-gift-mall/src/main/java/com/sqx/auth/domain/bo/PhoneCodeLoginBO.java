package com.sqx.auth.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Data
public class PhoneCodeLoginBO {

    @ApiModelProperty(value = "手机号")
    private String phoneNo;

    @ApiModelProperty(value = "验证码")
    private String verifyCode;
}
