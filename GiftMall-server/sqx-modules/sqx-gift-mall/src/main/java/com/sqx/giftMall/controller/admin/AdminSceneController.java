package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.SceneSaveBo;
import com.sqx.giftMall.domain.bo.SceneUpdateBo;
import com.sqx.giftMall.service.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "管理端-送礼场景", tags = {"管理端-送礼场景"})
@RestController
@RequestMapping("admin/scene")
public class AdminSceneController {

    @Resource
    private SceneService sceneService;

    @ApiOperation("送礼场景列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String name) {
        return sceneService.list(page, limit, name);
    }

    @ApiOperation("送礼场景保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated SceneSaveBo sceneSaveBo) {
        return sceneService.save(sceneSaveBo);
    }

    @ApiOperation("送礼场景修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated SceneUpdateBo sceneUpdateBo) {
        return sceneService.update(sceneUpdateBo);
    }

    @ApiOperation("送礼场景删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return sceneService.delete(id);
    }
}
