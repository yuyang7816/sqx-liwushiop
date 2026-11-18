package com.sqx.auth.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/6
 */
@Data
public class WxJsRegisterBo {

    @ApiModelProperty(value = "openid")
    private String openId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邀请人邀请码")
    private String superiorInvitationCode;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
