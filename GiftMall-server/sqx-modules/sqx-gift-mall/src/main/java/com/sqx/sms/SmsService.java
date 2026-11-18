package com.sqx.sms;

import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.sms.factory.SmsFactory;
import com.sqx.sms.provider.ISmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chengming
 * @since 2025/6/11
 */
@Component
public class SmsService {

    @Autowired
    private CommonInfoService commonInfoService;

    /**
     * 发送短信
     * @param phone 手机号
     * @param content 内容
     */
    public void sendSms(String phone, String content) {
        String smsProvider = "3";

        ISmsProvider smsService = SmsFactory.getSmsService(smsProvider);
        smsService.sendSms(phone, content);
    }
}
