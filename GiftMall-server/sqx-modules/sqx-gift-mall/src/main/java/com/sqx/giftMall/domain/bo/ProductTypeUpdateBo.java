package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class ProductTypeUpdateBo implements Serializable {
    private static final long serialVersionUID = -3185218396343150655L;

    @ApiModelProperty("id")
    private Long id;

    /**
     * 父级id，父级为0时为一级
     */
    @ApiModelProperty("父级id，父级为0时为一级")
    private Long parentId;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String img;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}
