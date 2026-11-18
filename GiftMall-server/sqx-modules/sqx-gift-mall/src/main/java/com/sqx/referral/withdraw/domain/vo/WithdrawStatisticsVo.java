package com.sqx.referral.withdraw.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class WithdrawStatisticsVo {

    @ApiModelProperty("累计提现金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("累计提现次数")
    private Long totalCount;

    @ApiModelProperty("待转帐次数")
    private Long waitCount;
}
