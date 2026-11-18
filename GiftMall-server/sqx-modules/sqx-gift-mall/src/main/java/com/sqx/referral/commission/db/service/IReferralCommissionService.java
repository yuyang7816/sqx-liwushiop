package com.sqx.referral.commission.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;

import java.math.BigDecimal;

/**
 * <p>
 * 佣金信息 服务类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
public interface IReferralCommissionService extends IService<ReferralCommission> {

    BigDecimal sumUserCommission(Long userId);

    IPage<ReferralListVo> referralList(Page<ReferralCommission> page, Long userId);
}
