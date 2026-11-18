package com.sqx.giftMall.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProductRuleValue;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class GiftProductRuleVo implements Serializable {
    private static final long serialVersionUID = -7502087301682926455L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 规格名称
     */
    @ApiModelProperty("规格名称")
    private String ruleName;

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

    @ApiModelProperty("规格值")
    private List<GiftProductRuleValue> giftProductRuleValueList;
}
