package com.sqx.banner.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.banner.domain.bo.BannerPageBo;
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
@Api(value = "用户端-banner")
@RestController
@RequestMapping("app/banner")
public class AppBannerController {

    @Resource
    private BannerService BannerService;

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<BannerVo>> page(@RequestBody @Validated BannerPageBo bannerPageBo) {
        IPage<BannerVo> voIPage = BannerService.pageInfo(bannerPageBo);
        return Result.success(voIPage);
    }
}
