package com.sqx.domain.repository.domain.giftMall.dto;

import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchHistoryVo extends SearchHistory {

    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("出现次数")
    private Long num;

    @ApiModelProperty("用户名")
    private String userName;
}
