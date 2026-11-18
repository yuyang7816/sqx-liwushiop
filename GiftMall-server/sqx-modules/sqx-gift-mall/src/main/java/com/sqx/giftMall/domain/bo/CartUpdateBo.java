package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class CartUpdateBo implements Serializable {
    private static final long serialVersionUID = -356355580893594780L;

    /**
     * 用户id
     */
    @ApiModelProperty("id")
    @NotNull(message = "请选择购物车商品")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @NotNull(message = "请选择商品")
    private Long productId;

    /**
     * 商品skuId
     */
    @ApiModelProperty("商品skuId")
    @NotNull(message = "请选择商品规格")
    private Long skuId;
}
