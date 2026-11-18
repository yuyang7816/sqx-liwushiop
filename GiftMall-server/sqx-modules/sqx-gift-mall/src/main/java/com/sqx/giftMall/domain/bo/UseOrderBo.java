package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/8/1
 */
@Data
public class UseOrderBo {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty(name = "省")
    private String province;

    @ApiModelProperty(name = "市")
    private String city;

    @ApiModelProperty(name = "区/县")
    private String district;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("纬度")
    private BigDecimal lat;
}
