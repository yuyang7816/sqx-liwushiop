package com.sqx.domain.repository.entity.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.WithdrawalAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 用户
 *
 */
@Data
@ApiModel("用户")
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户id")
	@TableId(type = IdType.AUTO,value = "user_id")
	private Long userId;
	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	@TableField("user_name")
	private String userName;

	/**
	 * 手机号
	 */
	@ApiModelProperty("手机号")
	private String phone;

	/**
	 * 头像
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 性别 1男 2女
	 */
	@ApiModelProperty("性别 1男 2女")
	private Integer sex;
	/**
	 * 年龄
	 */
	@ApiModelProperty("年龄")
	private Integer age;

	/**
	 * 微信小程序openid
	 */
	@ApiModelProperty("微信小程序openid")
	@TableField("open_id")
	private String openId;

	/**
	 * 微信app openid
	 */
	@ApiModelProperty("微信公众号openid")
	@TableField("wx_open_id")
	private String wxOpenId;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField("update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 苹果id
	 */
	@TableField("apple_id")
	private String appleId;

	/**
	 * 手机类型 1安卓 2ios
	 */
	@TableField("sys_phone")
	private Integer sysPhone;

	/**
	 * 状态 1正常 2禁用
	 */
	private Integer status;

	/**
	 * 来源 app JS小程序 MP公众号
	 */
	private String platform;

	/**
	 * 邀请码
	 */
	@TableField("invitation_code")
	private String invitationCode;

	/**
	 * 邀请人id
	 */
	@TableField("inviter_id")
	private Long inviterId;

	private String clientid;

	/**
	 * 佣金比例
	 */
	private Double commissionRate;

	@TableField(exist = false)
	private BigDecimal userMoney;

	@TableField(exist = false)
	private BigDecimal commission;

	/**
	 * 邀请人信息
	 */
	@TableField(exist = false)
	private String inviterUserName;
	@TableField(exist = false)
	private String inviterUserPhone;
	@TableField(exist = false)
	private String inviterUserAvatar;
	@TableField(exist = false)
	private String inviterUserCode;

	// 微信提现账户
	@TableField(exist = false)
	private String wxWithdrawalAccount;

	// 支付宝提现账户
	@TableField(exist = false)
	private String aliWithdrawalAccount;

	// 提现账户列表
	@TableField(exist = false)
	private List<WithdrawalAccount> withdrawalAccounts;
}
