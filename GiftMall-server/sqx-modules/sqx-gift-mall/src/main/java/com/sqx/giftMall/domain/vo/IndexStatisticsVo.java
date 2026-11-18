package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2025/9/28
 */
@Data
public class IndexStatisticsVo {

    @ApiModelProperty("总用户数")
    private Long totalUserCount;

    @ApiModelProperty("今日新增用户数")
    private Long todayUserCount;

    @ApiModelProperty("本月新增用户数")
    private Long monthUserCount;

    @ApiModelProperty("本年新增用户数")
    private Long yearUserCount;

    @ApiModelProperty("今日订单")
    private Long dayOrderCount;

    @ApiModelProperty("今日收入")
    private BigDecimal dayAmount;

    @ApiModelProperty("本月收入")
    private BigDecimal monthAmount;

    @ApiModelProperty("本年收入")
    private BigDecimal yearAmount;
}
