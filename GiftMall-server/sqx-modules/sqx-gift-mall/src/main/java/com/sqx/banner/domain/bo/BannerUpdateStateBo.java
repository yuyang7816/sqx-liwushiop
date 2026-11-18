package com.sqx.banner.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2025/5/16
 */
@Data
public class BannerUpdateStateBo {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "状态: 1正常 2隐藏")
    private Integer state;
}
