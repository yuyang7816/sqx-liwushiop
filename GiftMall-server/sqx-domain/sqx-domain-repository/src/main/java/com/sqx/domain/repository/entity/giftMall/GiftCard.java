package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 卡片表
 * @TableName gift_card
 */
@TableName(value ="gift_card")
@Data
public class GiftCard implements Serializable {
    /**
     * 卡片id
     */
    @TableId(value = "card_id")
    private Long cardId;

    /**
     * 卡片图案
     */
    @TableField(value = "card_img")
    private String cardImg;

    /**
     * 卡片分类id
     */
    @TableField(value = "card_category_id")
    private Long cardCategoryId;

    /**
     * 卡片状态 0启用 1禁用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序号
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}