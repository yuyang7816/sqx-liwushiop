package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CartSaveBo;
import com.sqx.giftMall.domain.bo.CartUpdateBo;

import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
public interface CartService {
    Result page(Integer page, Integer limit, Long userId);

    Result info(Long userId);

    Result save(CartSaveBo cartSaveBo);

    Result update(CartUpdateBo cartUpdateBo);

    Result delete(Long id);

    Result deleteByIds(List<Long> ids);

    Result changeNumber(Long id, Integer number);
}
