package com.sqx.referral.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawRecordSaveBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import com.sqx.referral.withdraw.service.WithdrawRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 提现记录 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Api(value = "用户端-提现记录", tags = "用户端-提现记录")
@RestController
@RequestMapping("/app/withdrawRecord")
public class AppWithdrawRecordController {

    @Resource
    private WithdrawRecordService withdrawRecordService;

    /**
     * 保存
     */
    @Login
    @ApiOperation(value = "发起提现")
    @PostMapping("save")
    public Result<Void> save(@RequestAttribute Long userId, @RequestBody @Validated WithdrawRecordSaveBo withdrawRecordSaveBo) {
        withdrawRecordSaveBo.setUserId(userId);
        withdrawRecordService.saveInfo(withdrawRecordSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @Login
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<WithdrawRecordVo>> page(@RequestAttribute Long userId, @RequestBody @Validated WithdrawRecordPageBo withdrawRecordPageBo) {
        withdrawRecordPageBo.setUserId(userId);
        IPage<WithdrawRecordVo> voIPage = withdrawRecordService.pageInfo(withdrawRecordPageBo);
        return Result.success(voIPage);
    }
}
