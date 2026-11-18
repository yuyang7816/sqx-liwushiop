package com.sqx.giftMall.domain.bo;

import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/22
 */
@Data
public class GenSkuBo {

    @ApiModelProperty(value = "封面图")
    private String coverImg;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "原价")
    private String originalPrice;

    @ApiModelProperty(value = "商品规格")
    private GiftProductAttrDto productAttr;
}
