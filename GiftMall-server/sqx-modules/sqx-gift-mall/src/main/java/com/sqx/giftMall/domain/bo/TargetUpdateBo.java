package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class TargetUpdateBo implements Serializable {
    private static final long serialVersionUID = -4376360819896392744L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @NotNull(message = "请选择送礼对象")
    private Long id;

    /**
     * 送礼场景名称
     */
    @ApiModelProperty("送礼对象名称")
    private String name;
}
