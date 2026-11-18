package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardUpdateBo implements Serializable {
    private static final long serialVersionUID = -3611153821109380057L;

    /**
     * 卡片id
     */
    @ApiModelProperty("卡片id")
    @NotNull(message = "卡片id不能为空")
    private Long cardId;

    /**
     * 卡片图案
     */
    @ApiModelProperty("卡片图案")
    @NotBlank(message = "卡片图案不能为空")
    private String cardImg;

    /**
     * 卡片分类id
     */
    @ApiModelProperty("卡片分类id")
    @NotNull(message = "请选择卡片分类")
    private Long cardCategoryId;

    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    private Integer sort;
}
