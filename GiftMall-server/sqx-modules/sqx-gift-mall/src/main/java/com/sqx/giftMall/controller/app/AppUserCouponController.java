package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserCouponSaveBo;
import com.sqx.giftMall.service.UserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Api(value = "用户端-用户优惠券", tags = {"用户端-用户优惠券"})
@RestController
@RequestMapping("app/userCoupon")
public class AppUserCouponController {
    @Resource
    private UserCouponService userCouponService;

    @ApiOperation("用户已领取的优惠券列表")
    @GetMapping("receivedCouponList")
    public Result receivedCouponList(Integer page, Integer limit, Long userId) {
        return userCouponService.receivedCouponList(page, limit, userId);
    }

    @ApiOperation("用户领取优惠券")
    @PostMapping("save")
    public Result save(@RequestBody UserCouponSaveBo userCouponSaveBo) {
        return userCouponService.save(userCouponSaveBo);
    }
}
