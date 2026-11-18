package com.sqx.pay.service;

import com.sqx.common.core.domain.Result;
import com.sqx.pay.domain.BusinessParam;

/**
 * @author chengming
 * @since 2025/8/5
 */
public interface LocalpayService {
    Result<String> pay(BusinessParam param);

    String refund(String orderNo);
}
