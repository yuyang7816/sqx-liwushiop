package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserMoneyDetailsBo;

/**
 * @author chengming
 * @since 2024/7/23
 */
public interface WalletService {
    Result info(Long userId);

    Result detail(Integer page, Integer limit, UserMoneyDetailsBo userMoneyDetailsBo);
}
