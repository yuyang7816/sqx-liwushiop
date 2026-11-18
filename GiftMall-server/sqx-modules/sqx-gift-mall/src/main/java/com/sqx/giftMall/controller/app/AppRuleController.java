package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.RuleListBo;
import com.sqx.giftMall.domain.bo.RuleSaveBo;
import com.sqx.giftMall.domain.bo.RuleUpdateBo;
import com.sqx.giftMall.service.RuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Api(value = "用户端-商品规格", tags = {"用户端-商品规格"})
@RestController
@RequestMapping("app/rule")
public class AppRuleController {
    @Resource
    private RuleService ruleService;

    @ApiOperation("商品规格列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, RuleListBo ruleListBo) {
        return ruleService.page(page, limit, ruleListBo);
    }

    @ApiOperation("商品规格详情")
    @GetMapping("info")
    public Result info(Long id) {
        return ruleService.info(id);
    }
}
