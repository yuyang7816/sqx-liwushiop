package com.sqx.common.core.domain;

import lombok.Data;

/**
 * @author chengming
 * @since 2024/9/11
 */
@Data
public class PageParam {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 页大小
     */
    private Integer limit;
}
