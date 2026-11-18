package com.sqx.domain.repository.domain.giftMall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class GiftCardInfoDto {


    /**
     * 卡片id
     */
    @ApiModelProperty("卡片id")
    private Long cardId;

    /**
     * 卡片图案
     */
    @ApiModelProperty("卡片图案")
    private String cardImg;

    /**
     * 卡片分类id
     */
    @ApiModelProperty("卡片分类id")
    private Long cardCategoryId;

    @ApiModelProperty("卡片分类名称")
    private String cardCategoryName;

    /**
     * 卡片状态 0启用 1禁用
     */
    @ApiModelProperty("卡片状态 0启用 1禁用")
    private Integer status;

    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
