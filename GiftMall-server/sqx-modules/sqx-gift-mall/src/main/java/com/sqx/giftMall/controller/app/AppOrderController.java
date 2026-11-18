package com.sqx.giftMall.controller.app;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.giftMall.domain.bo.*;
import com.sqx.giftMall.domain.vo.GiftOrderVo;
import com.sqx.giftMall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Api(value = "用户端-订单", tags = {"用户端-订单"})
@RestController
@RequestMapping("/app/order")
public class AppOrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("我的订单列表")
    @GetMapping("selfList")
    public Result selfList(Integer page, Integer limit, Long userId, Integer status) {
        return orderService.selfList(page, limit, userId, status);
    }

    @ApiOperation("我的售后订单列表")
    @GetMapping("selfRefundList")
    public Result selfRefundList(Integer page, Integer limit, Long userId) {
        return orderService.selfRefundList(page, limit, userId);
    }

    @ApiOperation("订单保存")
    @PostMapping("save")
    public Result save(@RequestBody OrderSaveBo orderSaveBo) {
        return orderService.save(orderSaveBo);
    }

    @Login
    @ApiOperation("购物车结算")
    @PostMapping("saveList")
    public Result saveList(@RequestAttribute Long userId, @RequestBody OrderSaveListBo orderSaveListBo) {
        orderSaveListBo.setUserId(userId);
        return orderService.saveList(orderSaveListBo);
    }

    @ApiOperation(value="订单取消，待付款时可取消, 优惠券不会回退")
    @GetMapping("/cancel")
    public Result orderCancel(@RequestParam Long orderId){
        return orderService.orderCancel(orderId);
    }

    @Login
    @ApiOperation(value = "领取礼物")
    @PostMapping("obtain")
    public Result obtain(@RequestAttribute Long userId, @RequestBody ObtainBo obtainBo) {
        obtainBo.setUserId(userId);
        return orderService.obtain(obtainBo);
    }

    @Deprecated
    @ApiOperation("拒绝礼物")
    @GetMapping("refuse")
    public Result refuse(Long id) {
        return orderService.refuse(id);
    }

    /**
     * 不再使用
     * @param userId
     * @param useOrderBo
     * @return
     */
    @Deprecated
    @Login
    @ApiOperation("使用礼物")
    @PostMapping("use")
    public Result use(@RequestAttribute Long userId, @RequestBody UseOrderBo useOrderBo) {
        useOrderBo.setUserId(userId);
        return orderService.use(useOrderBo);
    }

    @ApiOperation("确认收货")
    @GetMapping("confirm")
    public Result confirm(Long id) {
        return orderService.confirm(id);
    }

    @ApiOperation("订单支付")
    @PostMapping("pay")
    public Result payment(@RequestBody PaymentBo paymentBo) {
        return orderService.pay(paymentBo);
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

    @ApiOperation("订单详情")
    @GetMapping("info")
    public Result<GiftOrderVo> info(Long orderId) {
        return orderService.info(orderId);
    }

    @Login
    @ApiOperation("分享赠送")
    @PostMapping("share")
    public Result<GiftShare> share(@RequestAttribute Long userId, @RequestBody ShareOrderBo shareOrderBo) {
        shareOrderBo.setUserId(userId);
        GiftShare giftShare = orderService.share(shareOrderBo);
        return Result.success(giftShare);
    }

    @Login
    @ApiOperation("订单绑定卡片")
    @GetMapping("bingCard")
    public Result<Void> bingCard(@RequestAttribute Long userId, BingCardBo bingCardBo) {
        bingCardBo.setUserId(userId);
        orderService.bindCard(bingCardBo);
        return Result.success();
    }

    @Login
    @ApiOperation("查询物流信息")
    @GetMapping("getLogistics")
    public Result<JSONObject> getLogistics(String expressNumber) {
        JSONObject json = orderService.getLogistics(expressNumber);
        return Result.success(json);
    }
}
