package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CardListBo;
import com.sqx.giftMall.domain.bo.CardSaveBo;
import com.sqx.giftMall.domain.bo.CardUpdateBo;
import com.sqx.giftMall.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Api(value = "管理端-卡片", tags = {"管理端-卡片"})
@RestController
@RequestMapping("admin/card")
public class AdminCardController {
    @Resource
    private CardService cardService;

    @ApiOperation("卡片列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, CardListBo cardListBo) {
        return cardService.list(page, limit, cardListBo);
    }

    @ApiOperation("卡片信息")
    @GetMapping("info")
    public Result info(Long cardId) {
        return cardService.info(cardId);
    }

    @ApiOperation("卡片保存")
    @PostMapping("save")
    public Result save(@RequestBody CardSaveBo cardSaveBo) {
        return cardService.save(cardSaveBo);
    }

    @ApiOperation("卡片修改")
    @PostMapping("update")
    public Result update(@RequestBody CardUpdateBo cardUpdateBo) {
        return cardService.update(cardUpdateBo);
    }

    @ApiOperation("卡片删除")
    @GetMapping("delete")
    public Result delete(Long cardId) {
        return cardService.delete(cardId);
    }

    @ApiOperation("启用/禁用 卡片")
    @GetMapping("enable")
    public Result enable(Long cardId, Integer status) {
        return cardService.enable(cardId, status);
    }
}
