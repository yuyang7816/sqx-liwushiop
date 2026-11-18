package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CouponListBo;
import com.sqx.giftMall.domain.bo.CouponSaveBo;
import com.sqx.giftMall.domain.bo.CouponUpdateBo;

/**
 * @author chengming
 * @since 2024/7/18
 */
public interface CouponService {
    Result page(Integer page, Integer limit, CouponListBo couponListBo);

    Result info(Long id);

    Result save(CouponSaveBo couponSaveBo);

    Result update(CouponUpdateBo couponUpdateBo);

    Result delete(Long id);
}
