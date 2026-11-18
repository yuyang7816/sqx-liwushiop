package com.sqx.banner.domain.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author sqx
 * @since 2025-05-16 13:49:04
 */
@Data
public class BannerUpdateBo {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "分类1banner图 2金刚区分类")
    private Integer classify;

    @ApiModelProperty(value = "跳转地址 ")
    private String url;

    @ApiModelProperty(value = "顺序")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;
}
