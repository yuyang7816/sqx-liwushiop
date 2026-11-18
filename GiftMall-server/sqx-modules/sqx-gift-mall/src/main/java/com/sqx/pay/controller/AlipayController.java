package com.sqx.pay.controller;


import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.service.AlipayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chengming
 * @since 2025/4/21
 */
@Api(value = "支付宝接口", tags = "支付宝支付接口")
@RestController
@RequestMapping("app/alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Login
    @ApiOperation(value = "支付宝手机网站支付")
    @PostMapping("wapPay")
    public Result<String> wapPay(@RequestAttribute Long userId, @RequestBody @Validated BusinessParam param) {
        param.setUserId(userId);
        return alipayService.wapPay(param);
    }

    @Login
    @ApiOperation(value = "支付宝app支付")
    @PostMapping("appPay")
    public Result<String> appPay(@RequestAttribute Long userId, @RequestBody @Validated BusinessParam param) {
        param.setUserId(userId);
        return alipayService.appPay(param);
    }

    @ApiOperation(value = "支付宝pc支付(电脑网页扫码支付)")
    @PostMapping("pagePay")
    public Result<String> pagePay(@RequestBody @Validated BusinessParam param) {
        return alipayService.pagePay(param);
    }

    @ApiOperation(value = "支付宝普通公钥模式同步通知接口", hidden = true)
    @PostMapping("notifyUrl")
    public String notifyUrl(HttpServletRequest request) {
        return alipayService.notifyUrl(request);
    }

    @ApiOperation(value = "支付宝证书模式同步通知接口", hidden = true)
    @PostMapping("certNotifyUrl")
    public String certNotifyUrl(HttpServletRequest request) {
        return alipayService.certNotifyUrl(request);
    }

    @ApiOperation(value = "支付宝普通公钥模式异步通知接口", hidden = true)
    @GetMapping("returnUrl")
    public String returnUrl(HttpServletRequest request) {
        return alipayService.returnUrl(request);
    }

    @ApiOperation(value = "支付宝证书模式异步通知接口", hidden = true)
    @GetMapping("certReturnUrl")
    public String certReturnUrl(HttpServletRequest request) {
        return alipayService.certReturnUrl(request);
    }
}
