package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.BlessingListBo;
import com.sqx.giftMall.domain.bo.BlessingSaveBo;
import com.sqx.giftMall.domain.bo.BlessingUpdateBo;
import com.sqx.giftMall.service.BlessingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.executor.BaseExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@RestController
@RequestMapping("admin/blessing")
@Api(value = "管理端-祝福语", tags = {"管理端-祝福语"})
public class AdminBlessingController {
    @Resource
    private BlessingService blessingService;

    @ApiOperation("祝福语保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated BlessingSaveBo blessingSaveBo) {
        return blessingService.save(blessingSaveBo);
    }

    @ApiOperation("祝福语列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, BlessingListBo blessingListBo) {
        return blessingService.list(page, limit, blessingListBo);
    }

    @ApiOperation("祝福语详情")
    @GetMapping("info")
    public Result info(Long blessingId) {
        return blessingService.info(blessingId);
    }

    @ApiOperation("祝福语修改")
    @PostMapping("update")
    public Result update(BlessingUpdateBo blessingUpdateBo) {
        return blessingService.update(blessingUpdateBo);
    }

    @ApiOperation("祝福语删除")
    @GetMapping("delete")
    public Result delete(Long blessingId) {
        return blessingService.delete(blessingId);
    }
}
