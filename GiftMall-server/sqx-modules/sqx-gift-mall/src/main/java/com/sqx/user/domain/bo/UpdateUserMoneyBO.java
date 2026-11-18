package com.sqx.user.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/9/11
 */
@Data
public class UpdateUserMoneyBO {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    @ApiModelProperty(value = "操作类型 incr=增加 decr=减少")
    @Pattern(regexp = "^(incr|decr)$", message = "operation 必须是 'incr' 或 'decr'")
    private String operation;
}
