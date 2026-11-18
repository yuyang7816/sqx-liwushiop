package com.sqx.giftMall.controller.admin;

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
@Api(value = "管理端-商品规格", tags = {"管理端-商品规格"})
@RestController
@RequestMapping("admin/rule")
public class AdminRuleController {
    @Resource
    private RuleService ruleService;

    @ApiOperation("商品规格列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, RuleListBo ruleListBo) {
        return ruleService.page(page, limit, ruleListBo);
    }

    @ApiOperation("商品规格列表(不分页)")
    @GetMapping("list")
    public Result list(RuleListBo ruleListBo) {
        return ruleService.list(ruleListBo);
    }

    @ApiOperation("商品规格详情")
    @GetMapping("info")
    public Result info(Long id) {
        return ruleService.info(id);
    }

    @ApiOperation("商品规格保存")
    @PostMapping("save")
    public Result save(@RequestBody RuleSaveBo ruleSaveBo) {
        return ruleService.save(ruleSaveBo);
    }

    @ApiOperation("商品规格修改")
    @PostMapping("update")
    public Result update(@RequestBody RuleUpdateBo ruleUpdateBo) {
        return ruleService.update(ruleUpdateBo);
    }

    @ApiOperation("商品规格删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return ruleService.delete(id);
    }
}
