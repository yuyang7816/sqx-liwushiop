package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CouponIssueListBo;
import com.sqx.giftMall.domain.bo.CouponIssueSaveBo;
import com.sqx.giftMall.domain.bo.CouponIssueUpdateBo;

/**
 * @author chengming
 * @since 2024/7/18
 */
public interface CouponIssueService {
    Result page(Integer page, Integer limit, CouponIssueListBo couponIssueListBo);

    Result info(Long id);

    Result save(CouponIssueSaveBo couponIssueSaveBo);

    Result update(CouponIssueUpdateBo couponIssueUpdateBo);

    Result delete(Long id);

    Result useList(Long productId, Long userId);
}
