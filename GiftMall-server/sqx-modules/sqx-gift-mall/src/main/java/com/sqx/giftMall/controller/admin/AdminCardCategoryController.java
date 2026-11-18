package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CardCategoryListBo;
import com.sqx.giftMall.domain.bo.CardCategorySaveBo;
import com.sqx.giftMall.domain.bo.CardCategoryUpdateBo;
import com.sqx.giftMall.service.CardCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "管理端-卡片分类", tags = {"管理端-卡片分类"})
@RestController
@RequestMapping("admin/cardCategory")
public class AdminCardCategoryController {
    @Resource
    private CardCategoryService cardCategoryService;

    @ApiOperation("卡片分类列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, CardCategoryListBo cardCategoryListBo) {
        return cardCategoryService.list(page, limit, cardCategoryListBo);
    }

    @ApiOperation("卡片分类详情")
    @GetMapping("info")
    public Result info(Long cardCategoryId) {
        return cardCategoryService.info(cardCategoryId);
    }

    @ApiOperation("卡片分类保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated CardCategorySaveBo cardCategorySaveBo) {
        return cardCategoryService.save(cardCategorySaveBo);
    }

    @ApiOperation("卡片分类修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated CardCategoryUpdateBo cardCategoryUpdateBo) {
        return cardCategoryService.update(cardCategoryUpdateBo);
    }

    @ApiOperation("卡片分类删除")
    @GetMapping("delete")
    public Result delete(Long cardCategoryId) {
        return cardCategoryService.delete(cardCategoryId);
    }
}
