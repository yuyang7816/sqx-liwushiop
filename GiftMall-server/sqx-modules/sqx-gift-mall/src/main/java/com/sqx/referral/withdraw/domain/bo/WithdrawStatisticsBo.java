package com.sqx.referral.withdraw.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class WithdrawStatisticsBo {

    @ApiModelProperty("year=按年查询 month=按月查询 day=按天查询")
    private String findType;

    @ApiModelProperty("查询日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate findDate;
}
