package com.sqx.referral.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.referral.user.domain.vo.ReferralDataVo;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import com.sqx.referral.user.domain.vo.ReferralUserCommissionVo;
import com.sqx.referral.user.service.ReferralUserCommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户佣金 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Api(value = "用户端-用户佣金", tags = "用户端-用户佣金")
@RestController
@RequestMapping("/app/referral/userCommission")
public class AppReferralUserCommissionController {

    @Resource
    private ReferralUserCommissionService referralUserCommissionService;

    /**
     * 信息
     */
    @Login
    @ApiOperation(value = "信息")
    @GetMapping("info")
    public Result<ReferralUserCommissionVo> info(@RequestAttribute Long userId) {
        ReferralUserCommissionVo vo = referralUserCommissionService.info(userId);
        return Result.success(vo);
    }

    @Login
    @ApiOperation(value = "分销数据")
    @GetMapping("referralData")
    public Result<ReferralDataVo> referralData(@RequestAttribute Long userId) {
        ReferralDataVo vo = referralUserCommissionService.referralData(userId);
        return Result.success(vo);
    }

    @Login
    @ApiOperation(value = "邀请用户列表")
    @GetMapping("referralUserList")
    public Result<IPage<UserEntity>> referralUserList(@RequestAttribute Long userId) {
        IPage<UserEntity> userList = referralUserCommissionService.referralUserList(userId);
        return Result.success(userList);
    }

    @Login
    @ApiOperation(value = "收益奖励列表")
    @GetMapping("referralList")
    public Result<IPage<ReferralListVo>> referralList(@RequestAttribute Long userId) {
        IPage<ReferralListVo> voIPage = referralUserCommissionService.referralList(userId);
        return Result.success(voIPage);
    }
}
