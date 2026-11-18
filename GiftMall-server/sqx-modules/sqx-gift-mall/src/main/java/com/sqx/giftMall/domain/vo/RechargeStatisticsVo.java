package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class RechargeStatisticsVo {

    @ApiModelProperty("充值总金额")
    private BigDecimal totalAmount;

    private List<RechargeStatistics> rechargeRecords;

    @Data
    public static class RechargeStatistics {
        private String code;
        private String desc;
        private BigDecimal amount;
    }
}
