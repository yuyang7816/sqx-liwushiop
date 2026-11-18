package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardListBo implements Serializable {
    private static final long serialVersionUID = 1182480255185880908L;

    /**
     * 卡片分类id
     */
    @ApiModelProperty("卡片分类id")
    private Long cardCategoryId;

    /**
     * 卡片状态 0启用 1禁用
     */
    @ApiModelProperty("卡片状态 0启用 1禁用")
    private Integer status;
}
