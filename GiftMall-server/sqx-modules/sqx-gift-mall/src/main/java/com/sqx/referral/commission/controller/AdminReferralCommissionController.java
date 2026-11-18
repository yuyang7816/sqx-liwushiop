package com.sqx.referral.commission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.referral.commission.domain.bo.ReferralCommissionInfoBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionPageBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionSaveBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionUpdateBo;
import com.sqx.referral.commission.domain.vo.ReferralCommissionVo;
import com.sqx.referral.commission.service.ReferralCommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 佣金信息 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@Api(value = "管理端-佣金信息", tags = "管理端-佣金信息")
@RestController
@RequestMapping("/admin/referral/commission")
public class AdminReferralCommissionController {

    @Resource
    private ReferralCommissionService referralCommissionService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated ReferralCommissionSaveBo referralCommissionSaveBo) {
        referralCommissionService.saveInfo(referralCommissionSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<ReferralCommissionVo>> page(@RequestBody @Validated ReferralCommissionPageBo referralCommissionPageBo) {
        IPage<ReferralCommissionVo> voIPage = referralCommissionService.pageInfo(referralCommissionPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<ReferralCommissionVo> info(@RequestBody @Validated ReferralCommissionInfoBo referralCommissionInfoBo) {
        ReferralCommissionVo vo = referralCommissionService.info(referralCommissionInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated ReferralCommissionUpdateBo referralCommissionUpdateBo) {
        referralCommissionService.updateInfo(referralCommissionUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated ReferralCommissionInfoBo referralCommissionInfoBo) {
        referralCommissionService.delete(referralCommissionInfoBo);
        return Result.success();
    }
}
