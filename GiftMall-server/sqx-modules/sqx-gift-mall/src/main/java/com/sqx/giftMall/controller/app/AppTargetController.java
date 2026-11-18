package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
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
@Api(value = "用户端-送礼对象", tags = {"用户端-送礼对象"})
@RestController
@RequestMapping("app/target")
public class AppTargetController {
    @Resource
    private TargetService targetService;

    @ApiOperation("送礼对象列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String name) {
        return targetService.list(page, limit, name);
    }
}
