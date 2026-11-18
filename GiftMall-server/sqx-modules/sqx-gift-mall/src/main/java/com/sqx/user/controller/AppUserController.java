package com.sqx.user.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.user.domain.bo.ForgetPasswordBO;
import com.sqx.user.domain.bo.UpdateUserBO;
import com.sqx.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Api(value = "用户端-用户")
@RestController
@RequestMapping("app/user")
public class AppUserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取当前登录用户信息")
    @Login
    @GetMapping("info")
    public Result<UserEntity> info(@RequestAttribute("userId") Long userId) {
        return userService.info(userId);
    }

    @ApiOperation(value = "根据id获取用户信息")
    @Login
    @GetMapping("getUserInfoById")
    public Result<UserEntity> getUserInfoById(Long userId) {
        return userService.info(userId);
    }

    @ApiOperation(value = "忘记密码")
    @Login
    @PostMapping("forgetPassword")
    public Result<Void> forgetPassword(@RequestAttribute("userId") Long userId, @RequestBody ForgetPasswordBO forgetPasswordBO) {
        forgetPasswordBO.setUserId(userId);
        return userService.forgetPassword(forgetPasswordBO);
    }

    @ApiOperation(value = "修改用户信息")
    @Login
    @PostMapping("update")
    public Result<UserEntity> update(@RequestBody @Validated UpdateUserBO updateUserBO) {
        userService.update(updateUserBO);
        return Result.success();
    }
}
