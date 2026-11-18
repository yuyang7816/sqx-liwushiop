package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提现记录
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Getter
@Setter
@TableName("withdraw_record")
@ApiModel(value = "WithdrawRecord", description = "提现记录")
public class WithdrawRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "实际到账金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "手续费")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "提现账户 1微信 2支付宝")
    private Integer accountType;

    @ApiModelProperty(value = "状态 1发起提现 2提现成功 3拒绝提现")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "拒绝理由")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "转账时间")
    @TableField("withdraw_time")
    private LocalDateTime withdrawTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
