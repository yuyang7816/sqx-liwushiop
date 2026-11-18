package com.sqx.referral.user.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Data
public class ReferralUserCommissionInfoBo {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

}
