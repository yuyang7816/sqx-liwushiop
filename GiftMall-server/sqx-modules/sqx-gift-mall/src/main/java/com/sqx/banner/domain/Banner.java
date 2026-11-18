package com.sqx.banner.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * banner
 *
 * @author sqx
 * @since 2025-05-16 13:49:04
 */
@Getter
@Setter
@TableName("banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "图片地址")
    @TableField("image_url")
    private String imageUrl;

    @ApiModelProperty(value = "分类1banner图 2金刚区分类")
    @TableField("classify")
    private Integer classify;

    @ApiModelProperty(value = "跳转地址 ")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "状态1正常 2隐藏")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
