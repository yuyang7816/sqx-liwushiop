package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CouponIssueListBo;
import com.sqx.giftMall.domain.bo.CouponIssueSaveBo;
import com.sqx.giftMall.domain.bo.CouponIssueUpdateBo;
import com.sqx.giftMall.service.CouponIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Api(value = "管理端-优惠券发布", tags = {"管理端-优惠券发布"})
@RestController
@RequestMapping("admin/couponIssue")
public class AdminCouponIssueController {
    @Resource
    private CouponIssueService couponIssueService;

    @ApiOperation("优惠券发布列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, CouponIssueListBo couponIssueListBo) {
        return couponIssueService.page(page, limit, couponIssueListBo);
    }

    @ApiOperation("优惠券发布信息")
    @GetMapping("info")
    public Result info(Long id) {
        return couponIssueService.info(id);
    }

    @ApiOperation("优惠券发布保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated CouponIssueSaveBo couponIssueSaveBo) {
        return couponIssueService.save(couponIssueSaveBo);
    }

    @ApiOperation("优惠券发布修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated CouponIssueUpdateBo couponIssueUpdateBo) {
        return couponIssueService.update(couponIssueUpdateBo);
    }

    @GetMapping("优惠券发布删除")
    @PostMapping("delete")
    public Result delete(Long id) {
        return couponIssueService.delete(id);
    }
}
