package com.sqx.cancelAccount.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.cancelAccount.domain.bo.CancelAccountInfoBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountPageBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountSaveBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountUpdateBo;
import com.sqx.cancelAccount.domain.vo.CancelAccountVo;
import com.sqx.cancelAccount.service.CancelAccountService;
import com.sqx.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户注销申请 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-07 09:31:24
 */
@Api(value = "管理端-用户注销申请", tags = "管理端-用户注销申请")
@RestController
@RequestMapping("/admin/cancelAccount")
public class AdminCancelAccountController {

    @Resource
    private CancelAccountService cancelAccountService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated CancelAccountSaveBo cancelAccountSaveBo) {
        cancelAccountService.saveInfo(cancelAccountSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<CancelAccountVo>> page(@RequestBody @Validated CancelAccountPageBo cancelAccountPageBo) {
        IPage<CancelAccountVo> voIPage = cancelAccountService.pageInfo(cancelAccountPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<CancelAccountVo> info(@RequestBody @Validated CancelAccountInfoBo cancelAccountInfoBo) {
        CancelAccountVo vo = cancelAccountService.info(cancelAccountInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated CancelAccountUpdateBo cancelAccountUpdateBo) {
        cancelAccountService.updateInfo(cancelAccountUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated CancelAccountInfoBo cancelAccountInfoBo) {
        cancelAccountService.delete(cancelAccountInfoBo);
        return Result.success();
    }

    @ApiOperation(value = "注销用户")
    @GetMapping("cancel")
    public Result<Void> cancel(Long id) {
        cancelAccountService.cancel(id);
        return Result.success();
    }

    @ApiOperation(value = "强制注销用户")
    @GetMapping("remove")
    public Result<Void> remove(Long userId) {
        cancelAccountService.remove(userId);
        return Result.success();
    }
}
