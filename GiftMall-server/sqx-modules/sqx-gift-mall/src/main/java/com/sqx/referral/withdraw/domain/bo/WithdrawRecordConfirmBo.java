package com.sqx.referral.withdraw.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/5
 */
@Data
public class WithdrawRecordConfirmBo {

    private Long id;

    @ApiModelProperty("状态 1发起提现 2提现成功 3拒绝提现")
    private Integer status;

    @ApiModelProperty("拒绝理由")
    private String reason;
}
