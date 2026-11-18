package com.sqx.template.service;

/**
 * 短信模板 服务类
 *
 * @author sqx
 * @since 2025-06-12 10:20:37
 */
public interface SmsTemplateService {

    /**
     * 根据业务代码获取短信模板
     * @param bizCode 业务代码
     * @return 短信模板
     */
    String getContentByBizCode(String bizCode);
}
