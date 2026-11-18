package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.SceneSaveBo;
import com.sqx.giftMall.domain.bo.SceneUpdateBo;
import com.sqx.giftMall.domain.bo.TargetSaveBo;
import com.sqx.giftMall.domain.bo.TargetUpdateBo;
import com.sqx.giftMall.service.TargetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "管理端-送礼对象", tags = {"管理端-送礼对象"})
@RestController
@RequestMapping("admin/target")
public class AdminTargetController {
    @Resource
    private TargetService targetService;

    @ApiOperation("送礼对象列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String name) {
        return targetService.list(page, limit, name);
    }

    @ApiOperation("送礼对象保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated TargetSaveBo targetSaveBo) {
        return targetService.save(targetSaveBo);
    }

    @ApiOperation("送礼对象修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated TargetUpdateBo targetUpdateBo) {
        return targetService.update(targetUpdateBo);
    }

    @ApiOperation("送礼对象删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return targetService.delete(id);
    }
}
