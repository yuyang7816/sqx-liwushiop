package com.sqx.giftMall.controller.app;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserMoneyDetailsBo;
import com.sqx.giftMall.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/23
 */
@Api(value = "用户端-我的钱包", tags = {"用户端-我的钱包"})
@RestController
@RequestMapping("app/wallet")
public class AppWalletController {
    @Resource
    private WalletService walletService;

    @Login
    @ApiOperation("获取钱包详情")
    @GetMapping("info")
    public Result info(@RequestAttribute("userId") Long userId) {
        return walletService.info(userId);
    }

    @ApiOperation("资金明细")
    @GetMapping("detail")
    public Result detail(Integer page, Integer limit, UserMoneyDetailsBo userMoneyDetailsBo) {
        return walletService.detail(page, limit, userMoneyDetailsBo);
    }
}
