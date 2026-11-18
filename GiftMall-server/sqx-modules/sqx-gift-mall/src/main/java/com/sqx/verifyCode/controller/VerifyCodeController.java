package com.sqx.verifyCode.controller;

import com.sqx.common.core.domain.Result;
import com.sqx.verifyCode.domain.VerifyCode;
import com.sqx.verifyCode.domain.bo.SendEmailCodeBO;
import com.sqx.verifyCode.domain.bo.SendPhoneCodeBO;
import com.sqx.verifyCode.service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码 前端控制器
 *
 * @author sqx
 * @since 2025-04-22 15:31:50
 */
@Api(value= "验证码")
@RestController
@RequestMapping("app/verifyCode")
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "发送手机验证码")
    @PostMapping("sendPhoneCode")
    public Result<VerifyCode> sendPhoneCode(@RequestBody @Validated SendPhoneCodeBO sendPhoneCodeBO) {
        VerifyCode verifyCode = verifyCodeService.sendPhoneCode(sendPhoneCodeBO.getPhone(), sendPhoneCodeBO.getBizCode());
        return Result.success(verifyCode);
    }

    @ApiOperation(value = "发送邮箱验证码")
    @PostMapping("sendEmailCode")
    public Result<VerifyCode> sendEmailCode(@RequestBody @Validated SendEmailCodeBO sendEmailCodeBO) {
        VerifyCode verifyCode = verifyCodeService.sendMailCode(sendEmailCodeBO.getEmail(), sendEmailCodeBO.getBizCode());
        return Result.success(verifyCode);
    }
}
