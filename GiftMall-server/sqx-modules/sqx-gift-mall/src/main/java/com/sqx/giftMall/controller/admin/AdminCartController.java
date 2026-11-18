package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CartSaveBo;
import com.sqx.giftMall.domain.bo.CartUpdateBo;
import com.sqx.giftMall.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Api(value = "管理端-购物车", tags = {"管理端-购物车"})
@RestController
@RequestMapping("/admin/cart")
public class AdminCartController {
    @Resource
    private CartService cartService;

    @ApiOperation("购物车列表")
    @GetMapping("page")
    public Result page(Long userId, Integer page, Integer limit) {
        return cartService.page(page, limit, userId);
    }

    @ApiOperation("详情")
    @GetMapping("info")
    public Result info(Long userId) {
        return cartService.info(userId);
    }

    @ApiOperation("加入购物车")
    @PostMapping("save")
    public Result save(@RequestBody CartSaveBo cartSaveBo) {
        return cartService.save(cartSaveBo);
    }

    @ApiOperation("修改购物车商品")
    @PostMapping("update")
    public Result update(@RequestBody CartUpdateBo cartUpdateBo){
        return cartService.update(cartUpdateBo);
    }

    @ApiOperation("删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return cartService.delete(id);
    }

    @ApiOperation("批量删除")
    @GetMapping("deleteByIds")
    public Result deleteByIds(List<Long> ids) {
        return cartService.deleteByIds(ids);
    }

    @ApiOperation("修改数量")
    @GetMapping("changeNumber")
    public Result changeNumber(Long id, Integer number) {
        return cartService.changeNumber(id, number);
    }
}
