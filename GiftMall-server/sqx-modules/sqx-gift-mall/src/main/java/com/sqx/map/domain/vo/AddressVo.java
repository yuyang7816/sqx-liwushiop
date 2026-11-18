package com.sqx.map.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/7/22
 */
@Data
public class AddressVo {

    @ApiModelProperty(name = "国家")
    private String country;

    @ApiModelProperty(name = "省")
    private String province;

    @ApiModelProperty(name = "市")
    private String city;

    @ApiModelProperty(name = "区/县")
    private String district;

    @ApiModelProperty(name = "街道/城镇")
    private String town;

    @ApiModelProperty(name = "路/街")
    private String road;

    @ApiModelProperty(name = "地址")
    private String address;
}
