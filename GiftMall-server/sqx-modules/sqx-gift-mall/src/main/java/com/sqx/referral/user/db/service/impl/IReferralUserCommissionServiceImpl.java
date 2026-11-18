package com.sqx.referral.user.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.ReferralUserCommission;
import com.sqx.domain.repository.mapper.giftMall.ReferralUserCommissionMapper;
import com.sqx.referral.user.db.service.IReferralUserCommissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 用户佣金 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Service
public class IReferralUserCommissionServiceImpl extends ServiceImpl<ReferralUserCommissionMapper, ReferralUserCommission> implements IReferralUserCommissionService {

    @Resource
    private ReferralUserCommissionMapper referralUserCommissionMapper;

    @Override
    public void incr(Long userId, BigDecimal commission) {
        referralUserCommissionMapper.incr(userId, commission);
    }

    @Override
    public ReferralUserCommission getByUserId(Long userId) {
        QueryWrapper<ReferralUserCommission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ReferralUserCommission::getUserId, userId);

        return this.getOne(queryWrapper);
    }

    @Override
    public void decr(Long userId, BigDecimal amount) {
        referralUserCommissionMapper.decr(userId, amount);
    }
}
