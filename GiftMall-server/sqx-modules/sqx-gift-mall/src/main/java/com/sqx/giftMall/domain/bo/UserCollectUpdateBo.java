package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class UserCollectUpdateBo implements Serializable {
    private static final long serialVersionUID = -8388127915591448495L;

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long productId;
}
