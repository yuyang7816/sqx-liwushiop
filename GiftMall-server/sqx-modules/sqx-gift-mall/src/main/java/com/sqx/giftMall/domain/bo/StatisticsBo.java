package com.sqx.giftMall.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class StatisticsBo {

    @ApiModelProperty("year=按年查询 month=按月查询 day=按天查询")
    private String findType;

    @ApiModelProperty("查询日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate findDate;

    @ApiModelProperty(hidden = true)
    private LocalDateTime startTime;
    @ApiModelProperty(hidden = true)
    private LocalDateTime endTime;

    @ApiModelProperty(hidden = true)
    public void initFormattedDateRange(LocalDate findDate, String findType) {
        LocalDateTime start;
        LocalDateTime end;

        switch (findType.toLowerCase()) {
            case "day":
                start = findDate.atStartOfDay();
                end = findDate.atTime(23, 59, 59);
                break;

            case "month":
                YearMonth yearMonth = YearMonth.from(findDate);
                start = yearMonth.atDay(1).atStartOfDay();
                end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
                break;

            case "year":
                Year year = Year.from(findDate);
                start = year.atDay(1).atStartOfDay(); // 1月1日
                LocalDate lastDay = LocalDate.of(year.getValue(), 12, 31);
                end = lastDay.atTime(23, 59, 59);
                break;

            default:
                throw new IllegalArgumentException("不支持的 findType: " + findType);
        }

        this.startTime = start;
        this.endTime = end;
    }
}
