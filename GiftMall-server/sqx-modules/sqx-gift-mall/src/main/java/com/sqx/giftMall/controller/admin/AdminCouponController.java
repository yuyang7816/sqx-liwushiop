package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CouponListBo;
import com.sqx.giftMall.domain.bo.CouponSaveBo;
import com.sqx.giftMall.domain.bo.CouponUpdateBo;
import com.sqx.giftMall.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Api(value = "管理端-优惠券", tags = {"管理端-优惠券"})
@RestController
@RequestMapping("admin/coupon")
public class AdminCouponController {
    @Resource
    private CouponService couponService;

    @ApiOperation("优惠券列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, CouponListBo couponListBo) {
        return couponService.page(page, limit, couponListBo);
    }

    @ApiOperation("优惠券信息")
    @GetMapping("info")
    public Result info(Long id) {
        return couponService.info(id);
    }

    @ApiOperation("优惠券保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated CouponSaveBo couponSaveBo) {
        return couponService.save(couponSaveBo);
    }

    @ApiOperation("优惠券修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated CouponUpdateBo couponUpdateBo) {
        return couponService.update(couponUpdateBo);
    }

    @ApiOperation("优惠券删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return couponService.delete(id);
    }
}
