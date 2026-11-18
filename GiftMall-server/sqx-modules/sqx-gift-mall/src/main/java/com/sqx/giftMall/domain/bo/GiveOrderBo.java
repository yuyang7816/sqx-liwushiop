package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/7/31
 */
@Data
public class GiveOrderBo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("接受用户id")
    private Long receiverUserId;
}
