package com.sqx.banner.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.banner.domain.bo.*;
import com.sqx.banner.domain.vo.BannerVo;
import com.sqx.banner.service.BannerService;
import com.sqx.common.core.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * banner 前端控制器
 *
 * @author sqx
 * @since 2025-05-16 13:49:04
 */
@Api(value = "管理端-banner")
@RestController
@RequestMapping("admin/banner")
public class AdminBannerController {

    @Resource
    private BannerService bannerService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated BannerSaveBo bannerSaveBo) {
        bannerService.saveInfo(bannerSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<BannerVo>> page(@RequestBody @Validated BannerPageBo bannerPageBo) {
        IPage<BannerVo> voIPage = bannerService.pageInfo(bannerPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<BannerVo> info(@RequestBody @Validated BannerInfoBo bannerInfoBo) {
        BannerVo vo = bannerService.info(bannerInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated BannerUpdateBo bannerUpdateBo) {
        bannerService.updateInfo(bannerUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated BannerInfoBo bannerInfoBo) {
        bannerService.delete(bannerInfoBo);
        return Result.success();
    }

    @ApiOperation(value = "修改状态")
    @PostMapping("updateState")
    public Result<Void> updateState(@RequestBody @Validated BannerUpdateStateBo bannerUpdateStateBo) {
        bannerService.updateState(bannerUpdateStateBo);
        return Result.success();
    }
}
