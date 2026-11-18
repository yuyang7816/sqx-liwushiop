package com.sqx.giftMall.controller.app;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.UserCollectSaveBo;
import com.sqx.giftMall.domain.bo.UserCollectUpdateBo;
import com.sqx.giftMall.service.UserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Api(value = "用户端-用户收藏", tags = {"用户端-用户收藏"})
@RestController
@RequestMapping("app/userCollect")
public class AppUserCollectController {
    @Resource
    private UserCollectService userCollectService;

    @ApiOperation("用户收藏列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, Long userId) {
        return userCollectService.page(page, limit, userId);
    }

    @ApiOperation("用户收藏信息")
    @GetMapping("info")
    public Result info(Long id) {
        return userCollectService.info(id);
    }

    @ApiOperation("用户收藏保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated UserCollectSaveBo userCollectSaveBo) {
        return userCollectService.save(userCollectSaveBo);
    }

    @ApiOperation("用户收藏修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated UserCollectUpdateBo userCollectUpdateBo) {
        return userCollectService.update(userCollectUpdateBo);
    }

    @Login
    @ApiOperation("用户收藏删除")
    @GetMapping("delete")
    public Result delete(@RequestAttribute("userId") Long userId, Long productId) {
        return userCollectService.delete(userId, productId);
    }

    @ApiOperation("是否收藏")
    @GetMapping("check")
    public Result check(Long productId, Long userId) {
        return userCollectService.check(productId, userId);
    }
}
