package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardCategorySaveBo implements Serializable {
    private static final long serialVersionUID = -1130682799669009942L;

    /**
     * 卡片名称
     */
    @ApiModelProperty("卡片名称")
    @NotBlank(message = "请输入卡片名称")
    private String cardCategoryName;

    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    @NotNull(message = "请输入排序号")
    @Min(value = 0, message = "排序号不能小于0")
    @Max(value = Integer.MAX_VALUE, message = "排序号太大")
    private Integer sort;
}
