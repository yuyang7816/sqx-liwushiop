package com.sqx.referral.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountInfoBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountPageBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountSaveBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountUpdateBo;
import com.sqx.referral.withdraw.domain.vo.WithdrawalAccountVo;
import com.sqx.referral.withdraw.service.WithdrawalAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提现账号 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
@Api(value = "管理端-提现账号", tags = "管理端-提现账号")
@RestController
@RequestMapping("/admin/withdrawalAccount")
public class AdminWithdrawalAccountController {

    @Resource
    private WithdrawalAccountService withdrawalAccountService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated WithdrawalAccountSaveBo withdrawalAccountSaveBo) {
        withdrawalAccountService.saveInfo(withdrawalAccountSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<WithdrawalAccountVo>> page(@RequestBody @Validated WithdrawalAccountPageBo withdrawalAccountPageBo) {
        IPage<WithdrawalAccountVo> voIPage = withdrawalAccountService.pageInfo(withdrawalAccountPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<WithdrawalAccountVo> info(@RequestBody @Validated WithdrawalAccountInfoBo withdrawalAccountInfoBo) {
        WithdrawalAccountVo vo = withdrawalAccountService.info(withdrawalAccountInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated WithdrawalAccountUpdateBo withdrawalAccountUpdateBo) {
        withdrawalAccountService.updateInfo(withdrawalAccountUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated WithdrawalAccountInfoBo withdrawalAccountInfoBo) {
        withdrawalAccountService.delete(withdrawalAccountInfoBo);
        return Result.success();
    }
}
