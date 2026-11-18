package com.sqx.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sqx.common.core.exception.BizException;
import com.sqx.giftMall.enums.AppError;
import com.sqx.template.db.service.ISmsTemplateService;
import com.sqx.template.domain.entity.SmsTemplate;
import com.sqx.template.service.SmsTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 短信模板 服务实现类
 *
 * @author sqx
 * @since 2025-06-12 10:20:37
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Resource
    private ISmsTemplateService iSmsTemplateService;

    @Override
    public String getContentByBizCode(String bizCode) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getBizCode, bizCode);
        SmsTemplate smsTemplate = iSmsTemplateService.getOne(queryWrapper);
        if (Objects.isNull(smsTemplate)) {
            throw new BizException(AppError.SMS_TEMPLATE_NOT_FOUND);
        }

        return smsTemplate.getContent();
    }
}
