package com.sqx.searchHistory.domain.bo;

import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchHistoryPageBo extends PageParam {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "搜索内容")
    private String value;
}
