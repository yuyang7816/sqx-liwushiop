package com.sqx.pay.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.service.LocalpayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2025/8/5
 *
 * **未使用**
 */
@Api(value = "余额接口", tags = "余额支付接口")
@RestController
@RequestMapping("app/localpay")
public class LocalpayController {

    @Resource
    private LocalpayService localpayService;

    @Login
    @ApiOperation(value = "余额支付")
    @PostMapping("pay")
    public Result<String> pay(@RequestAttribute Long userId, @RequestBody @Validated BusinessParam param) {
        param.setUserId(userId);
        return localpayService.pay(param);
    }
}
