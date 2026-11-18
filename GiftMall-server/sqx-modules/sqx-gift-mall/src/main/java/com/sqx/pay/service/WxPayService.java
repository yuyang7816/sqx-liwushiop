package com.sqx.pay.service;

import com.sqx.common.core.domain.Result;
import com.sqx.pay.domain.BusinessParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chengming
 * @since 2025/4/22
 */
public interface WxPayService {
    <T> Result<T> pay(BusinessParam payParam);

    String notifyUrl(HttpServletRequest request);
}
