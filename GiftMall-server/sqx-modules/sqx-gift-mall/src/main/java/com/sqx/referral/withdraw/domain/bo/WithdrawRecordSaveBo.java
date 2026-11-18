package com.sqx.referral.withdraw.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Data
public class WithdrawRecordSaveBo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现账户 1支付宝 2微信")
    private Integer accountType;
}
