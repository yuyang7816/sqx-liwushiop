package com.sqx.giftMall.controller.app;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.vo.MyDataVo;
import com.sqx.giftMall.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2025/6/27
 */
@Api(value = "用户端-首页", tags = "用户端-首页")
@RestController
@RequestMapping("app/index")
public class AppIndexController {

    @Resource
    private IndexService indexService;

    @Login
    @ApiOperation("首页统计")
    @GetMapping("myData")
    public Result myData(@RequestAttribute Long userId) {
        MyDataVo vo = indexService.myData(userId);
        return Result.success(vo);
    }
}
