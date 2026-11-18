package com.sqx.pay.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Api(value = "微信支付", tags = "微信支付")
@RestController
@RequestMapping("app/wxpay")
public class WxPayController {

    @Autowired
    private WxPayService wxPayService;

    @Login
    @ApiOperation(value = "拉起微信支付")
    @PostMapping("pay")
    public <T> Result<T> pay(@RequestAttribute Long userId, @RequestBody BusinessParam payParam) {
        payParam.setUserId(userId);
        return wxPayService.pay(payParam);
    }

    @ApiOperation(value = "微信回调", hidden = true)
    @PostMapping("notifyUrl")
    public String notifyUrl(HttpServletRequest request) {
        return wxPayService.notifyUrl(request);
    }
}
