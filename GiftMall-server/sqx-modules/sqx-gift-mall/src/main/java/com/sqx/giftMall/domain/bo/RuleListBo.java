package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class RuleListBo implements Serializable {
    private static final long serialVersionUID = -4078319295237281170L;

    @ApiModelProperty("规格id")
    private Long ruleId;

    @ApiModelProperty("规格名称")
    private String ruleName;
}
