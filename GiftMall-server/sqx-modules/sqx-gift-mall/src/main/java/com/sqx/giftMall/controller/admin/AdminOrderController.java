package com.sqx.giftMall.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.OrderListBo;
import com.sqx.giftMall.domain.bo.OrderExpressBo;
import com.sqx.giftMall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Api(value = "管理端-订单", tags = {"管理端-订单"})
@RestController
@RequestMapping("admin/order")
public class AdminOrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("订单列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, OrderListBo orderListBo) {
        return orderService.page(page, limit, orderListBo);
    }

    @ApiOperation("发货")
    @PostMapping("express")
    public Result express(@RequestBody OrderExpressBo orderExpressBo) {
        return orderService.express(orderExpressBo);
    }

    @ApiOperation("我购买的礼物")
    @GetMapping("purchased")
    public Result purchased(Integer page, Integer limit, Long userId, Integer status) {
        return orderService.purchased(page, limit, userId, status);
    }

    @ApiOperation("我收到的礼物")
    @GetMapping("received")
    public Result received(Integer page, Integer limit, Long userId, Integer status) {
        return orderService.received(page, limit, userId, status);
    }

    @ApiOperation("修改发货信息")
    @PostMapping("updateExpress")
    public Result updateExpress(@RequestBody OrderExpressBo orderExpressBo) {
        return orderService.updateExpress(orderExpressBo);
    }

    @ApiOperation("查询物流信息")
    @GetMapping("getLogistics")
    public Result<JSONObject> getLogistics(String expressNumber) {
        JSONObject json = orderService.getLogistics(expressNumber);
        return Result.success(json);
    }
}
