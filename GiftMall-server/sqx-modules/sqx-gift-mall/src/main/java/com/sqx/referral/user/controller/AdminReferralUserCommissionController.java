package com.sqx.referral.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.referral.user.domain.bo.ReferralUserCommissionUpdateBo;
import com.sqx.referral.user.domain.vo.ReferralDataVo;
import com.sqx.referral.user.domain.vo.ReferralUserCommissionVo;
import com.sqx.referral.user.service.ReferralUserCommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户佣金 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:53
 */
@Api(value = "管理端-用户佣金", tags = "管理端-用户佣金")
@RestController
@RequestMapping("/admin/referral/userCommission")
public class AdminReferralUserCommissionController {

    @Resource
    private ReferralUserCommissionService referralUserCommissionService;

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<ReferralUserCommissionVo> info(Long userId) {
        ReferralUserCommissionVo vo = referralUserCommissionService.info(userId);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated ReferralUserCommissionUpdateBo referralUserCommissionUpdateBo) {
        referralUserCommissionService.updateInfo(referralUserCommissionUpdateBo);
        return Result.success();
    }

    @ApiOperation(value = "分销数据")
    @GetMapping("referralData")
    public Result<ReferralDataVo> referralData(Long userId) {
        ReferralDataVo vo = referralUserCommissionService.referralData(userId);
        return Result.success(vo);
    }

    @ApiOperation(value = "邀请用户列表")
    @GetMapping("referralUserList")
    public Result<IPage<UserEntity>> referralUserList(Long userId) {
        IPage<UserEntity> userList = referralUserCommissionService.referralUserList(userId);
        return Result.success(userList);
    }

    @ApiOperation(value = "收益奖励列表")
    @GetMapping("referralList")
    public Result<IPage<ReferralListVo>> referralList(Long userId) {
        IPage<ReferralListVo> voIPage = referralUserCommissionService.referralList(userId);
        return Result.success(voIPage);
    }
}
