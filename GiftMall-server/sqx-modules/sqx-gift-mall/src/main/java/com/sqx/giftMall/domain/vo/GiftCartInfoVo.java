package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/8/5
 */
@Data
public class GiftCartInfoVo {

    @ApiModelProperty("商品数量")
    private Integer productNum;

    @ApiModelProperty("总价格")
    private BigDecimal totalPrice;
}
