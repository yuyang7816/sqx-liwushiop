package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.service.BlessingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "用户端-祝福语", tags = {"用户端-祝福语"})
@RestController
@RequestMapping("/app/blessing")
public class AppBlessingController {
    @Resource
    private BlessingService blessingService;

    @ApiOperation("随机获取一句祝福语")
    @GetMapping("random")
    public Result random() {
        return blessingService.random();
    }
}
