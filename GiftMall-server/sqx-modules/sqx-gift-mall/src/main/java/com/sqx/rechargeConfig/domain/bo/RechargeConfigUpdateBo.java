package com.sqx.rechargeConfig.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 14:48:43
 */
@Data
public class RechargeConfigUpdateBo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
}
