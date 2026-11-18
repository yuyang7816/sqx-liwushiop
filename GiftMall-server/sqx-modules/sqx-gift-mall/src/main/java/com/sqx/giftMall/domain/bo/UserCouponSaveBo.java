package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class UserCouponSaveBo implements Serializable {
    private static final long serialVersionUID = 1545794919328916639L;

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("优惠券id")
    @NotNull(message = "请选择优惠券")
    private Long couponId;

    @ApiModelProperty("优惠券发布id")
    @NotNull(message = "优惠券发布id不能为空")
    private Long couponIssueId;
}
