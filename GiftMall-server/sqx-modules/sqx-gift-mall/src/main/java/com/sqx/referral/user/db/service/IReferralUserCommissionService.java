package com.sqx.referral.user.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.ReferralUserCommission;

import java.math.BigDecimal;

/**
 * <p>
 * 用户佣金 服务类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
public interface IReferralUserCommissionService extends IService<ReferralUserCommission> {

    void incr(Long userId, BigDecimal commission);

    ReferralUserCommission getByUserId(Long userId);

    void decr(Long userId, BigDecimal amount);
}
