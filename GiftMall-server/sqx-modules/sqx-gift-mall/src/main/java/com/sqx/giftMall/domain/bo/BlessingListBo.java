package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class BlessingListBo implements Serializable {
    private static final long serialVersionUID = -1183236129210013736L;

    @ApiModelProperty("祝福语内容")
    private String content;
}
