package com.sqx.app.controller;

import com.sqx.app.service.UserService;
import com.sqx.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengming
 * @since 2024/7/12
 */
@RestController
@RequestMapping("/app/Login")
@Api("APP登录接口")
@Slf4j
public class AppLoginController {
    @Autowired
    private UserService userService;

    @ApiOperation("微信小程序登陆")
    @RequestMapping(value = "/wxLogin", method = RequestMethod.GET)
    public Result wxLogin(@ApiParam("小程序code码") String code) {
        return userService.wxLogin(code);
    }

    @ApiOperation("快捷登录")
    @RequestMapping(value = "/quickLogin", method = RequestMethod.POST)
    public Result quickLogin(@RequestParam String phone, @RequestParam String verificationCode, String platform, Integer sysPhone, String openId, String inviterCode) {
        return userService.quickLogin(phone, verificationCode, platform, sysPhone, openId, inviterCode);
    }
}
