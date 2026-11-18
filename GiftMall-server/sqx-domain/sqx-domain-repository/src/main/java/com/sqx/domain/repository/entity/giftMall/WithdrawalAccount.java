package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 提现账号
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
@Getter
@Setter
@TableName("withdrawal_account")
@ApiModel(value = "WithdrawalAccount", description = "提现账号")
public class WithdrawalAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "账户名称")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "账户账号")
    @TableField("account_number")
    private String accountNumber;

    @ApiModelProperty(value = "账户类型 1支付宝 2微信")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
