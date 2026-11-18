package com.sqx.auth.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Data
public class PhoneRegisterBO {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phoneNo;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机类型 1安卓 2ios")
    private Integer sysPhone;

    @ApiModelProperty(value = "来源 APP 小程序 公众号")
    private String platform;

    @ApiModelProperty(value = "邀请人邀请码")
    private String superiorInvitationCode;
}
