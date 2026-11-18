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
public class TargetSaveBo implements Serializable {
    private static final long serialVersionUID = 9155069952478502659L;

    /**
     * 送礼对象名称
     */
    @ApiModelProperty("送礼对象名称")
    @NotBlank(message = "请输入送礼对象名称")
    private String name;
}
