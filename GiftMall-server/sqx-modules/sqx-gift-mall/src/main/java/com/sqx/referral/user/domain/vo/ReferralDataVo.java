package com.sqx.referral.user.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/8/4
 */
@Data
public class ReferralDataVo {

    @ApiModelProperty("邀请人数")
    private Long num;

    @ApiModelProperty("佣金")
    private BigDecimal commission;
}
