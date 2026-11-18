package com.sqx.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.user.domain.bo.ListUserBO;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.user.domain.bo.UpdateUserBO;
import com.sqx.user.domain.bo.UpdateUserMoneyBO;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;
import com.sqx.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Api(value = "管理端-用户", tags = "管理端-用户")
@RestController
@RequestMapping("admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表")
    @PostMapping("list")
    public Result<Page<UserEntity>> list(@RequestBody @Validated ListUserBO listUserBO) {
        Page<UserEntity> userEntityList = userService.list(listUserBO);
        return Result.success(userEntityList);
    }

    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public Result<UserEntity> info(@NotNull(message = "用户id不能为空") Long userId) {
        return userService.info(userId);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("update")
    public Result<Page<UserEntity>> update(@RequestBody @Validated UpdateUserBO updateUserBO) {
        userService.update(updateUserBO);
        return Result.success();
    }

    @ApiOperation(value = "修改用户钱包")
    @PostMapping("updateMoney")
    public Result<Void> updateMoney(@RequestBody @Validated UpdateUserMoneyBO updateUserMoneyBO) {
        userService.updateMoney(updateUserMoneyBO);
        return Result.success();
    }

    @ApiOperation(value = "支付明细")
    @PostMapping("payDetails")
    public Result<IPage<PayDetailsVo>> payDetails(@RequestBody @Validated PayDetailsBO payDetailsBO) {
        IPage<PayDetailsVo> voIPage = userService.payDetails(payDetailsBO);
        return Result.success(voIPage);
    }
}
