package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2024/7/23
 */
@Data
public class UserMoneyDetailsBo {

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("1注册 2购买 3提现")
    private Integer classify;

    @ApiModelProperty("类型（1充值 2.提现）")
    private Integer type;

    @ApiModelProperty("状态 1待支付 2已到账 3取消")
    private Integer state;
}
