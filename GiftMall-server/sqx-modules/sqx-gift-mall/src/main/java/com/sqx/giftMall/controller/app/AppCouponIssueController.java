package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.service.CouponIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Api(value = "用户端-优惠券发布", tags = {"用户端-优惠券发布"})
@RestController
@RequestMapping("app/couponIssue")
public class AppCouponIssueController {
    @Resource
    private CouponIssueService couponIssueService;

    @GetMapping("/useList")
    @ApiOperation("用户端领券列表")
    public Result useList(@RequestParam(required = false) Long productId, @RequestParam(required = false) Long userId) {
        return couponIssueService.useList(productId, userId);
    }
}
