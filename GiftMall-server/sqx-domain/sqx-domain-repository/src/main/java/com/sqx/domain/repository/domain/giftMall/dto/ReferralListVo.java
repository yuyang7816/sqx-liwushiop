package com.sqx.domain.repository.domain.giftMall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/8/4
 */
@Data
public class ReferralListVo {

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("金额")
    private BigDecimal amount;
}
