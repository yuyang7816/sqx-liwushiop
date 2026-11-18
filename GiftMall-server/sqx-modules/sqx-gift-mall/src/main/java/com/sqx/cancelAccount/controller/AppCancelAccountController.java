package com.sqx.cancelAccount.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.cancelAccount.domain.bo.CancelAccountSaveBo;
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
@Api(value = "用户端-用户注销申请", tags = "用户端-用户注销申请")
@RestController
@RequestMapping("/app/cancelAccount")
public class AppCancelAccountController {

    @Resource
    private CancelAccountService cancelAccountService;

    /**
     * 提交申请
     */
    @Login
    @ApiOperation(value = "提交注销申请")
    @PostMapping("commit")
    public Result<Void> save(@RequestAttribute Long userId, @RequestBody @Validated CancelAccountSaveBo cancelAccountSaveBo) {
        cancelAccountSaveBo.setUserId(userId);
        cancelAccountService.saveInfo(cancelAccountSaveBo);
        return Result.success();
    }

    @Login
    @ApiOperation(value = "用户撤销申请")
    @GetMapping("revoke")
    public Result<Void> revoke(@RequestAttribute Long userId) {
        cancelAccountService.revoke(userId);
        return Result.success();
    }
}
