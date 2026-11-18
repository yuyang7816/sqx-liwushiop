package com.sqx.user.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Data
public class UpdateUserBO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 性别 1男 2女
     */
    @ApiModelProperty(value = "性别 1男 2女")
    private Integer sex;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 微信小程序openid
     */
    @ApiModelProperty(value = "微信小程序openid")

    private String openId;

    /**
     * 微信app openid
     */
    @ApiModelProperty(value = "微信公众号openid")
    private String wxOpenId;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 苹果id
     */
    @ApiModelProperty(value = "苹果id")
    private String appleId;

    /**
     * 手机类型 1安卓 2ios
     */
    @ApiModelProperty(value = "手机类型 1安卓 2ios")
    private Integer sysPhone;

    /**
     * 状态 1正常 2禁用
     */
    @ApiModelProperty(value = "状态 1正常 2禁用")
    private Integer status;

    /**
     * 来源 app 小程序 公众号
     */
    @ApiModelProperty(value = "来源 app 小程序 公众号")
    private String platform;

    @ApiModelProperty(value = "app推送id")
    private String clientId;

    /**
     * 邀请码
     */
    @ApiModelProperty(value = "邀请码")
    private String invitationCode;

    @ApiModelProperty(value = "佣金比例")
    private Double commissionRate;
}
