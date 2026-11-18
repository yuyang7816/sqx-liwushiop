package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chengming
 * @since 2025/9/28
 */
@Data
public class OrderStatisticsVo {

    @ApiModelProperty("总订单数")
    private Long totalCount;

    @ApiModelProperty("统计列表")
    private List<OrderStatistics> orderStatisticsList;

    @Data
    public static class OrderStatistics {
        private Integer status;
        private String desc;
        private Long count;
    }
}
