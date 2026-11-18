package com.sqx.rechargeConfig.controller;

import com.sqx.common.core.domain.Result;
import com.sqx.rechargeConfig.domain.entity.RechargeConfig;
import com.sqx.rechargeConfig.service.RechargeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 充值金额配置 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 14:48:43
 */
@Api(value = "用户端-充值金额配置", tags = "用户端-充值金额配置")
@RestController
@RequestMapping("/app/rechargeConfig")
public class AppRechargeConfigController {

    @Resource
    private RechargeConfigService rechargeConfigService;


    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @GetMapping("list")
    public Result<List<RechargeConfig>> list() {
        List<RechargeConfig> voList = rechargeConfigService.list();
        return Result.success(voList);
    }
}
