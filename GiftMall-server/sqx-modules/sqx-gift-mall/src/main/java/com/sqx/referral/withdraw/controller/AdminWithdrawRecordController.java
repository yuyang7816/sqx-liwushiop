package com.sqx.referral.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import com.sqx.referral.withdraw.domain.bo.*;
import com.sqx.referral.withdraw.domain.vo.WithdrawStatisticsVo;
import com.sqx.referral.withdraw.service.WithdrawRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提现记录 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Api(value = "管理端-提现记录", tags = "管理端-提现记录")
@RestController
@RequestMapping("/admin/withdrawRecord")
public class AdminWithdrawRecordController {

    @Resource
    private WithdrawRecordService withdrawRecordService;

    /**
     * 保存
     */
    @ApiOperation(value = "发起提现")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated WithdrawRecordSaveBo withdrawRecordSaveBo) {
        withdrawRecordService.saveInfo(withdrawRecordSaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<WithdrawRecordVo>> page(@RequestBody @Validated WithdrawRecordPageBo withdrawRecordPageBo) {
        IPage<WithdrawRecordVo> voIPage = withdrawRecordService.pageInfo(withdrawRecordPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<WithdrawRecordVo> info(@RequestBody @Validated WithdrawRecordInfoBo withdrawRecordInfoBo) {
        WithdrawRecordVo vo = withdrawRecordService.info(withdrawRecordInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated WithdrawRecordUpdateBo withdrawRecordUpdateBo) {
        withdrawRecordService.updateInfo(withdrawRecordUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated WithdrawRecordInfoBo withdrawRecordInfoBo) {
        withdrawRecordService.delete(withdrawRecordInfoBo);
        return Result.success();
    }

    @ApiOperation(value = "确认转帐")
    @PostMapping("confirm")
    public Result<Void> confirm(@RequestBody WithdrawRecordConfirmBo withdrawRecordConfirmBo) {
        withdrawRecordService.confirm(withdrawRecordConfirmBo);
        return Result.success();
    }

    @ApiOperation(value = "提现统计")
    @PostMapping("statistics")
    public Result<WithdrawStatisticsVo> statistics(@RequestBody WithdrawStatisticsBo withdrawStatisticsBo) {
        WithdrawStatisticsVo vo = withdrawRecordService.statistics(withdrawStatisticsBo);
        return Result.success(vo);
    }
}
