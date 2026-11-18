package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class ProductTypeSaveBo implements Serializable {
    private static final long serialVersionUID = 6457756670329580029L;

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
