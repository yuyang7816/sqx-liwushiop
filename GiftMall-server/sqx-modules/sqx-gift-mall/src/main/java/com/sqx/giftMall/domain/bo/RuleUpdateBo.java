package com.sqx.giftMall.domain.bo;

import com.sqx.domain.repository.entity.giftMall.GiftProductRuleValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class RuleUpdateBo implements Serializable {
    private static final long serialVersionUID = 1503597152432495158L;

    @ApiModelProperty("规格id")
    @NotNull(message = "请选择规格")
    private Long id;

    /**
     * 规格名称
     */
    @ApiModelProperty("规格名称")
    @NotBlank(message = "请输入规格名称")
    private String ruleName;

    @ApiModelProperty("规格值列表")
    private List<GiftProductRuleValue> giftProductRuleValueList;
}
