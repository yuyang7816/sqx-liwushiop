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
 * 佣金信息
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@Getter
@Setter
@TableName("referral_commission")
@ApiModel(value = "ReferralCommission", description = "佣金信息")
public class ReferralCommission implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "佣金比例")
    @TableField("rate")
    private BigDecimal rate;

    @ApiModelProperty(value = "佣金金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "类型 0: 提现 1: 订单佣金")
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
