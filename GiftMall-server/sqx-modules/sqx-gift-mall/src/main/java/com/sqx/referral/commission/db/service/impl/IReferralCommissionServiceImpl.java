package com.sqx.referral.commission.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import com.sqx.domain.repository.mapper.giftMall.ReferralCommissionMapper;
import com.sqx.referral.commission.db.service.IReferralCommissionService;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 佣金信息 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@Service
public class IReferralCommissionServiceImpl extends ServiceImpl<ReferralCommissionMapper, ReferralCommission> implements IReferralCommissionService {

    @Resource
    private ReferralCommissionMapper referralCommissionMapper;

    @Override
    public BigDecimal sumUserCommission(Long userId) {
        return referralCommissionMapper.sumUserCommission(userId);
    }

    @Override
    public IPage<ReferralListVo> referralList(Page<ReferralCommission> page, Long userId) {
        return referralCommissionMapper.referralList(page, userId);
    }
}
