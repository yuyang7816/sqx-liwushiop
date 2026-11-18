package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sqx.domain.repository.entity.giftMall.GiftProductRuleValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class RuleSaveBo implements Serializable {
    private static final long serialVersionUID = -8587786099309037214L;

    /**
     * 规格名称
     */
    @ApiModelProperty("规格名称")
    @NotBlank(message = "请输入规格名称")
    private String ruleName;

    @ApiModelProperty("规格值列表")
    private List<GiftProductRuleValue> giftProductRuleValueList;
}
