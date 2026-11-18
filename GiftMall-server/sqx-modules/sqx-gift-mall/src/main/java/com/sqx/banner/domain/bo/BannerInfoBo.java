package com.sqx.banner.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author sqx
 * @since 2025-05-16 13:49:04
 */
@Data
public class BannerInfoBo {
    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

}
