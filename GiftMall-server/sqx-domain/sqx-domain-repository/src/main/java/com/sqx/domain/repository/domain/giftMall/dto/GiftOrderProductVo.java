package com.sqx.domain.repository.domain.giftMall.dto;

import com.sqx.domain.repository.entity.giftMall.GiftOrderProduct;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.sqx.domain.repository.entity.giftMall.GiftProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/1
 */
@Data
public class GiftOrderProductVo extends GiftOrderProduct {

    @ApiModelProperty("商品信息")
    private GiftProduct giftProduct;

    @ApiModelProperty("商品类型信息")
    private GiftProductType giftProductType;

    @ApiModelProperty("商品sku")
    private GiftProductSku giftProductSku;
}
