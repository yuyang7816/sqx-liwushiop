package com.sqx.referral.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.referral.user.domain.bo.ReferralUserCommissionInfoBo;
import com.sqx.referral.user.domain.bo.ReferralUserCommissionUpdateBo;
import com.sqx.referral.user.domain.vo.ReferralDataVo;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import com.sqx.referral.user.domain.vo.ReferralUserCommissionVo;


/**
 * 用户佣金 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
public interface ReferralUserCommissionService {

    ReferralUserCommissionVo info(Long userId);

    void updateInfo(ReferralUserCommissionUpdateBo referralUserCommissionUpdateBo);

    void delete(ReferralUserCommissionInfoBo referralUserCommissionInfoBo);

    /**
     * 订单完成后，保存佣金
     * @param orderId 订单id
     */
    void saveUserCommission(Long orderId);

    ReferralDataVo referralData(Long userId);

    IPage<UserEntity> referralUserList(Long userId);

    IPage<ReferralListVo> referralList(Long userId);
}
