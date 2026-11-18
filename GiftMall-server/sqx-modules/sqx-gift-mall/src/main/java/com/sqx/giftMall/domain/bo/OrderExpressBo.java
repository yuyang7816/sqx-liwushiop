package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class OrderExpressBo implements Serializable {
    private static final long serialVersionUID = 8598056840254444324L;

    @ApiModelProperty("订单id")
    private Long id;

    @ApiModelProperty("快递名称")
    private String expressName;

    @ApiModelProperty("快递号")
    private String expressNumber;
}
