package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.service.UserCouponService;
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
@Api(value = "管理端-用户优惠券", tags = {"管理端-用户优惠券"})
@RestController
@RequestMapping("admin/userCoupon")
public class AdminUserCouponController {
    @Resource
    private UserCouponService userCouponService;

    @ApiOperation("优惠券领取记录")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, @RequestParam(required = false) String couponName) {
        return userCouponService.list(page, limit, couponName);
    }
}
