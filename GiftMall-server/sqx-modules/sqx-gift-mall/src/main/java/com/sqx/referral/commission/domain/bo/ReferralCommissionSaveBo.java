package com.sqx.referral.commission.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@Data
public class ReferralCommissionSaveBo {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "佣金比例")
    private BigDecimal rate;

    @ApiModelProperty(value = "佣金金额")
    private BigDecimal amount;

}
