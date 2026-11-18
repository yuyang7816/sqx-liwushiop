package com.sqx.giftMall.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class CartSaveBo implements Serializable {
    private static final long serialVersionUID = -4075421143839949381L;

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

    @ApiModelProperty("数量")
    private Integer num;
}
