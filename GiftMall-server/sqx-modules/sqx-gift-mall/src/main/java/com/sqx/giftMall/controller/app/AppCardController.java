package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CardGroupByCategoryListBo;
import com.sqx.giftMall.service.CardService;
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
@Api(value = "用户端-卡片", tags = {"用户端-卡片"})
@RestController
@RequestMapping("/app/card")
public class AppCardController {
    @Resource
    private CardService cardService;

    @ApiOperation("卡片列表")
    @GetMapping("listCard")
    public Result listCard(Integer page, Integer limit, CardGroupByCategoryListBo cardGroupByCategoryListBo) {
        return cardService.listCard(page, limit, cardGroupByCategoryListBo);
    }
}
