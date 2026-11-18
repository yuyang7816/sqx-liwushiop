package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardCategoryUpdateBo implements Serializable {
    private static final long serialVersionUID = 4465666681960557220L;

    /**
     * 卡片分类id
     */
    @NotNull(message = "请选择卡片分类")
    private Long cardCategoryId;

    /**
     * 卡片名称
     */
    private String cardCategoryName;

    /**
     * 排序号
     */
    private Integer sort;
}
