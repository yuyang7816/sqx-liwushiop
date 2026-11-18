package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.service.CardCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "用户端-卡片分类", tags = {"用户端-卡片分类"})
@RestController
@RequestMapping("app/cardCategory")
public class AppCardCategoryController {
    @Resource
    private CardCategoryService cardCategoryService;

    @ApiOperation("卡片分类列表")
    @GetMapping("listCardCategory")
    public Result listCardCategory(Integer page, Integer limit) {
        return cardCategoryService.listCardCategory(page, limit);
    }
}
