package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户佣金
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Getter
@Setter
@TableName("referral_user_commission")
@ApiModel(value = "ReferralUserCommission", description = "用户佣金")
public class ReferralUserCommission implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "佣金")
    @TableField("commission")
    private BigDecimal commission;

}
