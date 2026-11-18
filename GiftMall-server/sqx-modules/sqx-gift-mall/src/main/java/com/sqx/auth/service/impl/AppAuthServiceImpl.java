package com.sqx.auth.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sqx.auth.domain.bo.*;
import com.sqx.auth.domain.vo.LoginVO;
import com.sqx.auth.domain.vo.WxLoginVo;
import com.sqx.auth.service.AppAuthService;
import com.sqx.auth.utils.JwtUtils;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.utils.Md5Utils;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.ReferralUserCommission;
import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.sqx.domain.repository.service.giftMall.IUserMoneyService;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.enums.AppError;
import com.sqx.giftMall.utils.GenUtil;
import com.sqx.referral.user.db.service.IReferralUserCommissionService;
import com.sqx.verifyCode.service.VerifyCodeService;
import com.sqx.wx.WxJsLoginRes;
import com.sqx.wx.WxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Service
public class AppAuthServiceImpl implements AppAuthService {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private IUserMoneyService iUserMoneyService;
    @Autowired
    private IReferralUserCommissionService iReferralUserCommissionService;
    @Autowired
    private CommonInfoService commonInfoService;

    @Override
    public Result<LoginVO> phonePasswordLogin(PhonePasswordLoginBO phonePasswordLoginBO) {
        UserEntity userEntity = getUserByPhone(phonePasswordLoginBO.getPhoneNo());
        if (Objects.isNull(userEntity)) {
            throw new BizException(AppError.PHONE_NOT_REGISTER);
        }

        if (1 != userEntity.getStatus()) {
            throw new BizException(AppError.ACCOUNT_ABNORMAL);
        }

        String encryptPassword = Md5Utils.md5s(phonePasswordLoginBO.getPassword());

        if (!Objects.equals(encryptPassword, userEntity.getPassword())) {
            throw new BizException(AppError.PHONE_OR_PASSWORD_ERROR);
        }

        LoginVO vo = buildLoginResult(userEntity);
        return Result.success(vo);
    }

    @Override
    public Result<LoginVO> phoneCodeLogin(PhoneCodeLoginBO phoneCodeLoginBO) {
        // 校验验证码
        if (!verifyCodeService.verify(phoneCodeLoginBO.getPhoneNo(), phoneCodeLoginBO.getVerifyCode())) {
            throw new BizException(AppError.VERIFY_CODE_ERROR);
        }

        UserEntity userEntity = getUserByPhone(phoneCodeLoginBO.getPhoneNo());
        if (Objects.isNull(userEntity)) {
            // 注册流程
            UserEntity userInfo = registerPhone(phoneCodeLoginBO.getPhoneNo(), null ,null,null, null, null);
            LoginVO vo = buildLoginResult(userInfo);
            return Result.success(vo);
        }

        if (1 != userEntity.getStatus()) {
            throw new BizException(AppError.ACCOUNT_ABNORMAL);
        }

        LoginVO vo = buildLoginResult(userEntity);
        return Result.success(vo);
    }

    @Override
    public Result<LoginVO> phoneRegister(PhoneRegisterBO phoneRegisterBO) {
        if (!verifyCodeService.verify(phoneRegisterBO.getPhoneNo(), phoneRegisterBO.getCode())) {
            throw new BizException(AppError.VERIFY_CODE_ERROR);
        }

        UserEntity userInfo = registerPhone(phoneRegisterBO.getPhoneNo(), phoneRegisterBO.getPassword(), phoneRegisterBO.getUsername(), phoneRegisterBO.getSysPhone(), phoneRegisterBO.getPlatform(), phoneRegisterBO.getSuperiorInvitationCode());

        LoginVO vo = buildLoginResult(userInfo);
        return Result.success(vo);
    }

    @Override
    public Result<Void> resetPassword(ResetPasswordBO resetPasswordBO) {
        if (!verifyCodeService.verify(resetPasswordBO.getPhone(), resetPasswordBO.getCode())) {
            throw new BizException(AppError.VERIFY_CODE_ERROR);
        }

        if (!checkPhoneExist(resetPasswordBO.getPhone())) {
            throw new BizException(AppError.PHONE_NOT_REGISTER);
        }

        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(UserEntity::getPhone, resetPasswordBO.getPhone())
                .set(UserEntity::getPassword, Md5Utils.md5s(resetPasswordBO.getPassword()));

        iUserService.update(new UserEntity(), updateWrapper);

        return Result.success();
    }

    @Override
    public Result<WxLoginVo> wxLogin(String code) {
        String appId = commonInfoService.getCommonValue(45);
        String secret = commonInfoService.getCommonValue(46);

        WxJsLoginRes wxJsLoginRes = WxUtil.wxJsLogin(appId, secret, code);

        WxLoginVo vo = new WxLoginVo();
        vo.setSessionKey(wxJsLoginRes.getSessionKey());
        vo.setUnionid(wxJsLoginRes.getUnionid());
        vo.setErrmsg(wxJsLoginRes.getErrmsg());
        vo.setOpenid(wxJsLoginRes.getOpenid());
        vo.setErrmsg(wxJsLoginRes.getErrmsg());

        // 是否绑定手机号
        UserEntity userEntity = iUserService.getByOpenId(wxJsLoginRes.getOpenid());
        if (Objects.isNull(userEntity) || StringUtils.isBlank(userEntity.getPhone())) {
            vo.setBindPhone(false);
        } else {
            LoginVO loginVO = buildLoginResult(userEntity);
            vo.setLoginVO(loginVO);
            vo.setUserEntity(userEntity);
            vo.setBindPhone(true);
        }

        return Result.success(vo);
    }

    @Override
    public Result<LoginVO> wxJsRegister(WxJsRegisterBo wxJsRegisterBo) {
        UserEntity userByPhone = getUserByPhone(wxJsRegisterBo.getPhone());
        if (Objects.nonNull(userByPhone)) {
            // 绑定openId
            userByPhone.setOpenId(wxJsRegisterBo.getOpenId());
            iUserService.updateById(userByPhone);

            LoginVO vo = buildLoginResult(userByPhone);
            return Result.success(vo);
        }

        // 新注册用户
        UserEntity userInfo = new UserEntity();
        userInfo.setUserName("微信用户");
        userInfo.setAvatar(wxJsRegisterBo.getAvatar());
        userInfo.setPhone(wxJsRegisterBo.getPhone());
        userInfo.setStatus(1);
        userInfo.setPlatform("小程序");
        userInfo.setInvitationCode(GenUtil.genInviteCode(wxJsRegisterBo.getPhone()));
        userInfo.setOpenId(wxJsRegisterBo.getOpenId());
        userInfo.setCreateTime(LocalDateTime.now());

        // 默认佣金比例
        String commissionRate = commonInfoService.getCommonValue(189);
        userInfo.setCommissionRate(Double.parseDouble(commissionRate));

        // 邀请人id
        if (StringUtils.isNotBlank(wxJsRegisterBo.getSuperiorInvitationCode())) {
            UserEntity invitationUser = iUserService.getUserByInvitationCode(wxJsRegisterBo.getSuperiorInvitationCode());
            if (Objects.nonNull(invitationUser)) {
                userInfo.setInviterId(invitationUser.getUserId());
            }
        }

        iUserService.save(userInfo);

        /* 其他业务 */
        // 用户钱包
        UserMoney userMoney = new UserMoney();
        userMoney.setUserId(userInfo.getUserId());
        userMoney.setMoney(BigDecimal.ZERO);
        iUserMoneyService.save(userMoney);

        // 用户佣金钱包
        ReferralUserCommission referralUserCommission = new ReferralUserCommission();
        referralUserCommission.setUserId(userInfo.getUserId());
        referralUserCommission.setCommission(BigDecimal.ZERO);
        iReferralUserCommissionService.save(referralUserCommission);

        LoginVO vo = buildLoginResult(userInfo);
        return Result.success(vo);
    }

    @Override
    public Result<Object> decryptPhone(DecryptPhoneBo decryptPhoneBo) {
        String decryptS5 = WxUtil.decryptS5(decryptPhoneBo.getDecryptData(), decryptPhoneBo.getKey(), decryptPhoneBo.getIv());
        return Result.success(JSONObject.parseObject(decryptS5));
    }

    @Override
    public Result<Void> delete(Long userId) {
        iUserService.removeById(userId);

        iUserMoneyService.remove(new LambdaQueryWrapper<UserMoney>().eq(UserMoney::getUserId, userId));

        iReferralUserCommissionService.remove(new LambdaQueryWrapper<ReferralUserCommission>().eq(ReferralUserCommission::getUserId, userId));

        return Result.success();
    }

    private boolean checkPhoneExist(String phone) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getPhone, phone);
        return iUserService.exists(queryWrapper);
    }

    /**
     * 手机号注册
     * @param phoneNo
     * @param sysPhone
     * @param platform
     * @param superiorInvitationCode
     * @return
     */
    private UserEntity registerPhone(String phoneNo, String password, String username, Integer sysPhone, String platform, String superiorInvitationCode) {
        UserEntity userEntity = getUserByPhone(phoneNo);
        if (Objects.nonNull(userEntity)) {
            throw new BizException(AppError.PHONE_REGISTERED);
        }

        UserEntity userInfo = new UserEntity();
        userInfo.setUserName(StringUtils.isNotBlank(username) ? username : phoneNo);
        userInfo.setPhone(phoneNo);
        userInfo.setSysPhone(sysPhone);
        userInfo.setStatus(1);
        userInfo.setPlatform(platform);
        userInfo.setInvitationCode(GenUtil.genInviteCode(phoneNo));
        userInfo.setCreateTime(LocalDateTime.now());

        // 默认佣金比例
        String commissionRate = commonInfoService.getCommonValue(189);
        userInfo.setCommissionRate(Double.parseDouble(commissionRate));

        // 邀请人id
        if (StringUtils.isNotBlank(superiorInvitationCode)) {
            UserEntity invitationUser = iUserService.getUserByInvitationCode(superiorInvitationCode);
            if (Objects.nonNull(invitationUser)) {
                userInfo.setInviterId(invitationUser.getUserId());
            }
        }

        // 密码
        if (StringUtils.isNotBlank(password)) {
            String encryptPassword = Md5Utils.md5s(password);
            userInfo.setPassword(encryptPassword);
        }

        iUserService.save(userInfo);

        /* 其他业务 */
        // 用户钱包
        UserMoney userMoney = new UserMoney();
        userMoney.setUserId(userInfo.getUserId());
        userMoney.setMoney(BigDecimal.ZERO);
        iUserMoneyService.save(userMoney);

        // 用户佣金钱包
        ReferralUserCommission referralUserCommission = new ReferralUserCommission();
        referralUserCommission.setUserId(userInfo.getUserId());
        referralUserCommission.setCommission(BigDecimal.ZERO);
        iReferralUserCommissionService.save(referralUserCommission);

        return userInfo;
    }

    /**
     * 构建登录返回值
     * @param userEntity 用户信息
     * @return
     */
    private LoginVO buildLoginResult(UserEntity userEntity) {
        LoginVO vo = new LoginVO();

        //生成token
        String token = jwtUtil.generateToken(userEntity.getUserId());
        vo.setToken(token);
        vo.setExpire(jwtUtil.getExpire());
        vo.setUser(userEntity);
        return vo;

    }

    /**
     * 手机号获取用户信息
     * @param phoneNo 手机号
     * @return
     */
    private UserEntity getUserByPhone(String phoneNo) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getPhone, phoneNo);

        return iUserService.getOne(queryWrapper);
    }
}
