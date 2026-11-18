package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/7/31
 */
@Data
public class ObtainBo {

    @ApiModelProperty("分享id")
    private Long shareId;

    @ApiModelProperty("接收人id")
    private Long userId;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String district;

    @ApiModelProperty("详细地址")
    private String addressDetail;

    @ApiModelProperty("祝福语")
    private String blessing;
}
