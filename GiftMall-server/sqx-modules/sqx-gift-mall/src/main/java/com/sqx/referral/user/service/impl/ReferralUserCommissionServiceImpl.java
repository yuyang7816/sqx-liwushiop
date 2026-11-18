package com.sqx.referral.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import com.sqx.domain.repository.entity.giftMall.ReferralUserCommission;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.referral.commission.db.service.IReferralCommissionService;
import com.sqx.referral.user.db.service.IReferralUserCommissionService;
import com.sqx.referral.user.domain.bo.ReferralUserCommissionInfoBo;
import com.sqx.referral.user.domain.bo.ReferralUserCommissionUpdateBo;
import com.sqx.referral.user.domain.vo.ReferralDataVo;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import com.sqx.referral.user.domain.vo.ReferralUserCommissionVo;
import com.sqx.referral.user.service.ReferralUserCommissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 用户佣金 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Slf4j
@Service
public class ReferralUserCommissionServiceImpl implements ReferralUserCommissionService {

    @Resource
    private IReferralUserCommissionService iReferralUserCommissionService;
    @Resource
    private IGiftOrderService iGiftOrderService;
    @Resource
    private CommonInfoService commonInfoService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IReferralCommissionService iReferralCommissionService;

    @Override
    public ReferralUserCommissionVo info(Long userId) {
        ReferralUserCommission referralUserCommission = iReferralUserCommissionService.getOne(new LambdaQueryWrapper<ReferralUserCommission>().eq(ReferralUserCommission::getUserId, userId));
        if (Objects.isNull(referralUserCommission)) {
            throw new BizException("信息不存在");
        }

        ReferralUserCommissionVo vo = new ReferralUserCommissionVo();
        BeanUtils.copyProperties(referralUserCommission, vo);
        return vo;
    }

    @Override
    public void updateInfo(ReferralUserCommissionUpdateBo referralUserCommissionUpdateBo) {
        ReferralUserCommission referralUserCommission = new ReferralUserCommission();
        BeanUtils.copyProperties(referralUserCommissionUpdateBo, referralUserCommission);

        iReferralUserCommissionService.updateById(referralUserCommission);
    }

    @Override
    public void delete(ReferralUserCommissionInfoBo referralUserCommissionInfoBo) {
        iReferralUserCommissionService.removeById(referralUserCommissionInfoBo.getId());
    }

    @Override
    public void saveUserCommission(Long orderId) {
        GiftOrder giftOrder = iGiftOrderService.getById(orderId);
        if (Objects.isNull(giftOrder)) {
            log.info("订单信息不存在, 不计算佣金");
            return;
        }

        UserEntity userInfo = iUserService.getById(giftOrder.getUserId());
        if (Objects.isNull(userInfo)) {
            log.error("用户信息不存在，取消计算佣金");
            return;
        }

        UserEntity inviteUserInfo = iUserService.getById(userInfo.getInviterId());
        if (Objects.isNull(inviteUserInfo)) {
            log.info("邀请用户不存在，不计算佣金");
            return;
        }

        if (Objects.equals(2, inviteUserInfo.getStatus())) {
            log.info("邀请用户被禁用，取消计算佣金");
            return;
        }

        // 佣金比例
        BigDecimal commissionRate = BigDecimal.valueOf(inviteUserInfo.getCommissionRate());

        // 佣金
        BigDecimal payMoney = giftOrder.getPayMoney();
        BigDecimal commission = payMoney.multiply(commissionRate);

        // 增加用户佣金
        iReferralUserCommissionService.incr(inviteUserInfo.getUserId(), commission);

        // 保存用户佣金信息
        ReferralCommission referralCommission = new ReferralCommission();
        referralCommission.setUserId(inviteUserInfo.getUserId());
        referralCommission.setOrderId(orderId);
        referralCommission.setRate(commissionRate);
        referralCommission.setAmount(commission);
        referralCommission.setType(1);
        iReferralCommissionService.save(referralCommission);
    }

    @Override
    public ReferralDataVo referralData(Long userId) {
        // 邀请人数
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getInviterId, userId);
        long count = iUserService.count(queryWrapper);

        // 佣金
        BigDecimal commission = iReferralCommissionService.sumUserCommission(userId);

        ReferralDataVo vo = new ReferralDataVo();
        vo.setNum(count);
        vo.setCommission(commission);
        return vo;
    }

    @Override
    public IPage<UserEntity> referralUserList(Long userId) {
        Page<UserEntity> page = new Page<>(1, 10);

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getInviterId, userId);
        IPage<UserEntity> userEntityPage = iUserService.page(page, queryWrapper);

        return userEntityPage;
    }

    @Override
    public IPage<ReferralListVo> referralList(Long userId) {
        Page<ReferralCommission> page = new Page<>(1, 10);

        IPage<ReferralListVo> voIPage = iReferralCommissionService.referralList(page, userId);

        return voIPage;
    }
}
