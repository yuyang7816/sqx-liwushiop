package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.service.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "用户端-送礼场景", tags = {"用户端-送礼场景"})
@RestController
@RequestMapping("app/scene")
public class AppSceneController {

    @Resource
    private SceneService sceneService;

    @ApiOperation("送礼场景列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String name) {
        return sceneService.list(page, limit, name);
    }
}
