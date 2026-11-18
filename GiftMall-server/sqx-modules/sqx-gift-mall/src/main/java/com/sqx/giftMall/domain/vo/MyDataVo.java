package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/4
 */
@Data
public class MyDataVo {

    @ApiModelProperty("收藏数")
    private Long collectNum;

    @ApiModelProperty("接收礼物数")
    private Long receiveGiftNum;

    @ApiModelProperty("购物车商品数")
    private Long cartNum;
}
