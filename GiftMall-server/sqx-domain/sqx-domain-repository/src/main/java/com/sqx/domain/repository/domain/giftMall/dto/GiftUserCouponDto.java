package com.sqx.domain.repository.domain.giftMall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class GiftUserCouponDto implements Serializable {
    private static final long serialVersionUID = -5102074090241384680L;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failureTime;

    @ApiModelProperty("用户优惠券状态（1未使用 2已使用 3已过期）")
    private Integer status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("优惠券金额")
    private BigDecimal lessMoney;

    @ApiModelProperty("优惠券最小金额")
    private BigDecimal minMoney;
}
