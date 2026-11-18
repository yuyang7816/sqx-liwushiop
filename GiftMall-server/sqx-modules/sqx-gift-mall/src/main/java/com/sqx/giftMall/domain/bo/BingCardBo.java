package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/8/4
 */
@Data
public class BingCardBo {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("卡片id")
    private Long cardId;

    @ApiModelProperty("卡片图片地址")
    private String cardImg;
}
