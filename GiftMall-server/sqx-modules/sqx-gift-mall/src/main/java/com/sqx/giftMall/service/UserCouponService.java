package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserCouponSaveBo;

/**
 * @author chengming
 * @since 2024/7/18
 */
public interface UserCouponService {
    Result list(Integer page, Integer limit, String couponName);

    Result receivedCouponList(Integer page, Integer limit, Long userId);

    Result save(UserCouponSaveBo userCouponSaveBo);
}
