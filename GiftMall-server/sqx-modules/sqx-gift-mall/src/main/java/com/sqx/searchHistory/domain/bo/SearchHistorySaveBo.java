package com.sqx.searchHistory.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@Data
public class SearchHistorySaveBo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "词")
    private String value;
}
