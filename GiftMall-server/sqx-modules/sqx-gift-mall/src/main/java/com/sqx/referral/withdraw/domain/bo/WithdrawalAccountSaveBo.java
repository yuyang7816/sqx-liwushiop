package com.sqx.referral.withdraw.domain.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
@Data
public class WithdrawalAccountSaveBo {
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "账户账号")
    private String accountNumber;

    @ApiModelProperty(value = "账户类型 1支付宝 2微信")
    private Integer type;
}
