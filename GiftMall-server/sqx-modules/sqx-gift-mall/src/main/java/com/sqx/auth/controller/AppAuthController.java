package com.sqx.auth.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.auth.domain.bo.*;
import com.sqx.auth.domain.vo.LoginVO;
import com.sqx.auth.domain.vo.WxLoginVo;
import com.sqx.auth.service.AppAuthService;
import com.sqx.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Api(value = "用户端-登录")
@RestController
@RequestMapping("app/auth")
public class AppAuthController {

    @Autowired
    private AppAuthService appAuthService;

    @ApiOperation(value = "手机号密码登录")
    @PostMapping("phonePasswordLogin")
    public Result<LoginVO> phonePasswordLogin(@RequestBody PhonePasswordLoginBO phonePasswordLoginBO) {
        return appAuthService.phonePasswordLogin(phonePasswordLoginBO);
    }

    @ApiOperation(value = "手机号验证码登录（如果未注册会先注册）")
    @PostMapping("phoneCodeLogin")
    public Result<LoginVO> phoneCodeLogin(@RequestBody PhoneCodeLoginBO phoneCodeLoginBO) {
        return appAuthService.phoneCodeLogin(phoneCodeLoginBO);
    }

    @ApiOperation(value = "手机号注册")
    @PostMapping("phoneRegister")
    public Result<LoginVO> phoneRegister(@RequestBody PhoneRegisterBO phoneRegisterBO) {
        return appAuthService.phoneRegister(phoneRegisterBO);
    }

    @ApiOperation(value = "忘记密码")
    @PostMapping("resetPassword")
    public Result<Void> resetPassword(@RequestBody ResetPasswordBO resetPasswordBO) {
        return appAuthService.resetPassword(resetPasswordBO);
    }

    @ApiOperation("微信小程序登陆")
    @GetMapping("wxLogin")
    public Result<WxLoginVo> wxLogin(String code){
        return appAuthService.wxLogin(code);
    }

    @ApiOperation("微信小程序注册")
    @PostMapping("wxJsRegister")
    public Result<LoginVO> wxJsRegister(@RequestBody WxJsRegisterBo wxJsRegisterBo) {
        return appAuthService.wxJsRegister(wxJsRegisterBo);
    }

    @ApiOperation("解密手机号")
    @PostMapping("decryptPhone")
    public Result<Object> decryptPhone(@RequestBody DecryptPhoneBo decryptPhoneBo) {
        return appAuthService.decryptPhone(decryptPhoneBo);
    }

    @Login
    @ApiOperation("注销账号")
    @GetMapping("delete")
    public Result<Void> delete(@RequestAttribute Long userId) {
        return appAuthService.delete(userId);
    }
}