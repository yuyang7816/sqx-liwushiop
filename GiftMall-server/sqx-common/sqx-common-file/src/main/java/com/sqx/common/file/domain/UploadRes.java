package com.sqx.common.file.domain;

import lombok.Data;

/**
 * @author chengming
 * @since 2025/4/29
 */
@Data
public class UploadRes {

    /**
     * 文件路径
     */
    private String path;

    /**
     * 相对路径
     * */
    private String relativePath;

    /**
     * 存储类型
     * */
    private String extension;

    /**
     * 文件大小
     * */
    private Long size;
}
