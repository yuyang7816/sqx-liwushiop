package com.sqx.banner.domain.bo;


import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sqx
 * @since 2025-05-16 13:49:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BannerPageBo extends PageParam {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "分类1banner图 2金刚区分类")
    private Integer classify;

    @ApiModelProperty(value = "状态1正常 2隐藏")
    private Integer state;
}
