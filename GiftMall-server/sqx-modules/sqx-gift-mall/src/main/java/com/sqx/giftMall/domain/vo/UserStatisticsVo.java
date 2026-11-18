package com.sqx.giftMall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/28
 */
@Data
public class UserStatisticsVo {

    @ApiModelProperty("总用户数")
    private Long totalCount;
}
