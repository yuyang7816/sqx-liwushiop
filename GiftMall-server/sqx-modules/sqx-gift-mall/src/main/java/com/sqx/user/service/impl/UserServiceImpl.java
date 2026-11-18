package com.sqx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.*;
import com.sqx.domain.repository.service.giftMall.IPayDetailsService;
import com.sqx.domain.repository.service.giftMall.IUserMoneyDetailsService;
import com.sqx.domain.repository.service.giftMall.IUserMoneyService;
import com.sqx.domain.repository.service.giftMall.IWithdrawalAccountService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.enums.AppError;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.referral.user.db.service.IReferralUserCommissionService;
import com.sqx.user.domain.bo.ForgetPasswordBO;
import com.sqx.user.domain.bo.ListUserBO;
import com.sqx.user.domain.bo.UpdateUserBO;
import com.sqx.user.domain.bo.UpdateUserMoneyBO;
import com.sqx.user.enums.OperationType;
import com.sqx.user.service.UserService;
import com.sqx.verifyCode.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private IUserMoneyService iUserMoneyService;
    @Autowired
    private IUserMoneyDetailsService iUserMoneyDetailsService;
    @Autowired
    private IPayDetailsService iPayDetailsService;
    @Autowired
    private IReferralUserCommissionService iReferralUserCommissionService;
    @Autowired
    private IWithdrawalAccountService iWithdrawalAccountService;

    @Override
    public Result<UserEntity> info(Long userId) {
        UserEntity userEntity = iUserService.getById(userId);
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.USER_NOT_FOUND);
        }

        // 钱包余额
        UserMoney userMoney = iUserMoneyService.getByUserId(userId);
        if (Objects.isNull(userMoney)) {
            throw new BizException("用户钱包不存在");
        }

        userEntity.setUserMoney(userMoney.getMoney());

        ReferralUserCommission referralUserCommission = iReferralUserCommissionService.getByUserId(userId);
        if (Objects.isNull(referralUserCommission)) {
            throw new BizException("用户佣金收益不存在");
        }

        userEntity.setCommission(referralUserCommission.getCommission());

        // 邀请人信息
        if (null != userEntity.getInviterId()) {
            UserEntity inviterUser = iUserService.getById(userEntity.getInviterId());
            if (Objects.nonNull(inviterUser)) {
                userEntity.setInviterUserName(inviterUser.getUserName());
                userEntity.setInviterUserPhone(inviterUser.getPhone());
                userEntity.setInviterUserAvatar(inviterUser.getAvatar());
                userEntity.setInviterUserCode(inviterUser.getInvitationCode());
            }
        }

        // 提现账号信息
        List<WithdrawalAccount> withdrawalAccounts = iWithdrawalAccountService.getByUserId(userEntity.getUserId());
        userEntity.setWithdrawalAccounts(withdrawalAccounts);

        return Result.success(userEntity);
    }

    @Override
    public Result<Void> forgetPassword(ForgetPasswordBO forgetPasswordBO) {
        UserEntity userEntity = iUserService.getById(forgetPasswordBO.getUserId());
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.USER_NOT_FOUND);
        }

        if (!verifyCodeService.verify(userEntity.getPhone(), forgetPasswordBO.getCode())) {
            throw new BizException(AppError.VERIFY_CODE_ERROR);
        }

        iUserService.update(new UserEntity(), new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserId, forgetPasswordBO.getUserId())
                .set(UserEntity::getPassword, DigestUtils.md5Hex(forgetPasswordBO.getPassword()))
        );

        return Result.success();
    }

    @Override
    public Page<UserEntity> list(ListUserBO listUserBO) {
        Page<UserEntity> initPage = PageUtil.initPage(listUserBO.getPage(), listUserBO.getLimit());

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(listUserBO.getPhone()), UserEntity::getPhone, listUserBO.getPhone())
                .eq(StringUtils.isNotBlank(listUserBO.getUsername()), UserEntity::getUserName, listUserBO.getUsername())
                .eq(Objects.nonNull(listUserBO.getStatus()), UserEntity::getStatus, listUserBO.getStatus())
                .orderByDesc(UserEntity::getCreateTime);

        Page<UserEntity> userEntityPage = iUserService.page(initPage, queryWrapper);
        if (CollectionUtils.isNotEmpty(userEntityPage.getRecords())) {
            for (UserEntity userEntity : userEntityPage.getRecords()) {
                UserMoney userMoney = iUserMoneyService.getByUserId(userEntity.getUserId());
                if (Objects.nonNull(userMoney)) {
                    userEntity.setUserMoney(userMoney.getMoney());
                }

                // 提现账号信息
                List<WithdrawalAccount> withdrawalAccounts = iWithdrawalAccountService.getByUserId(userEntity.getUserId());
                for (WithdrawalAccount withdrawalAccount : withdrawalAccounts) {
                    if (1 == withdrawalAccount.getType()) {
                        userEntity.setAliWithdrawalAccount(withdrawalAccount.getAccountNumber());
                    }

                    if (2 == withdrawalAccount.getType()) {
                        userEntity.setWxWithdrawalAccount(withdrawalAccount.getAccountNumber());
                    }
                }
            }
        }

        return userEntityPage;
    }

    @Override
    public void update(UpdateUserBO updateUserBO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(updateUserBO, userEntity);

        iUserService.updateById(userEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMoney(UpdateUserMoneyBO updateUserMoneyBO) {
        Integer type;
        String content;
        if (Objects.equals(OperationType.INCR.code(), updateUserMoneyBO.getOperation())) {
            // 增加金额
            iUserMoneyService.incr(updateUserMoneyBO.getUserId(), updateUserMoneyBO.getMoney());

            type = 1;
            content = "系统增加金额: " + updateUserMoneyBO.getMoney();
        } else {
            // 减少金额
            iUserMoneyService.decr(updateUserMoneyBO.getUserId(), updateUserMoneyBO.getMoney());

            type = 2;
            content = "系统减少金额: " + updateUserMoneyBO.getMoney();
        }

        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(updateUserMoneyBO.getUserId());
        userMoneyDetails.setMoney(updateUserMoneyBO.getMoney());
        userMoneyDetails.setType(type);
        userMoneyDetails.setClassify(4);
        userMoneyDetails.setTitle("系统操作");
        userMoneyDetails.setContent(content);
        userMoneyDetails.setState(2);
        userMoneyDetails.setCreateTime(LocalDateTime.now());

        iUserMoneyDetailsService.save(userMoneyDetails);
    }

    @Override
    public IPage<PayDetailsVo> payDetails(PayDetailsBO payDetailsBO) {
        Page<PayDetails> initPage = PageUtil.initPage(payDetailsBO.getPage(), payDetailsBO.getLimit());
        IPage<PayDetailsVo> voIPage = iPayDetailsService.payDetails(initPage, payDetailsBO);

        return voIPage;
    }
}
