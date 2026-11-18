package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserCollectSaveBo;
import com.sqx.giftMall.domain.bo.UserCollectUpdateBo;

/**
 * @author chengming
 * @since 2024/7/18
 */
public interface UserCollectService {
    Result page(Integer page, Integer limit, Long userId);

    Result info(Long id);

    Result save(UserCollectSaveBo userCollectSaveBo);

    Result update(UserCollectUpdateBo userCollectUpdateBo);

    Result delete(Long userId, Long productId);

    Result check(Long productId, Long userId);
}
