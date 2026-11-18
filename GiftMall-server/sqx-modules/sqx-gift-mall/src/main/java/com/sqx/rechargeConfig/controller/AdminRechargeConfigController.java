package com.sqx.rechargeConfig.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigInfoBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigPageBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigSaveBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigUpdateBo;
import com.sqx.rechargeConfig.domain.vo.RechargeConfigVo;
import com.sqx.rechargeConfig.service.RechargeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 充值金额配置 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 14:48:43
 */
@Api(value = "管理端-充值金额配置", tags = "管理端-充值金额配置")
@RestController
@RequestMapping("/admin/rechargeConfig")
public class AdminRechargeConfigController {

    @Resource
    private RechargeConfigService rechargeConfigService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated RechargeConfigSaveBo rechargeConfigSaveBo) {
        rechargeConfigService.saveInfo(rechargeConfigSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<RechargeConfigVo>> page(@RequestBody @Validated RechargeConfigPageBo rechargeConfigPageBo) {
        IPage<RechargeConfigVo> voIPage = rechargeConfigService.pageInfo(rechargeConfigPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<RechargeConfigVo> info(@RequestBody @Validated RechargeConfigInfoBo rechargeConfigInfoBo) {
        RechargeConfigVo vo = rechargeConfigService.info(rechargeConfigInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated RechargeConfigUpdateBo rechargeConfigUpdateBo) {
        rechargeConfigService.updateInfo(rechargeConfigUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated RechargeConfigInfoBo rechargeConfigInfoBo) {
        rechargeConfigService.delete(rechargeConfigInfoBo);
        return Result.success();
    }
}
