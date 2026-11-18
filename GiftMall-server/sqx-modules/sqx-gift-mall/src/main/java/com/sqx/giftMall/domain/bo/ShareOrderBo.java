package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/7/31
 */
@Data
public class ShareOrderBo {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("祝福语")
    private String blessing;
}
