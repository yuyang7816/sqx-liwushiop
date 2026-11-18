package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class SceneSaveBo implements Serializable {
    private static final long serialVersionUID = -3435373435657712782L;


    /**
     * 送礼场景名称
     */
    @ApiModelProperty("送礼场景名称")
    @NotBlank(message = "请输入送礼场景名称")
    private String name;
}
