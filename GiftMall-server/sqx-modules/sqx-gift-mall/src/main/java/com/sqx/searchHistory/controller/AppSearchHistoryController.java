package com.sqx.searchHistory.controller;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.searchHistory.domain.bo.SearchHistoryInfoBo;
import com.sqx.searchHistory.service.SearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索历史 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@Api(value = "用户端-搜索历史", tags = "用户端-搜索历史")
@RestController
@RequestMapping("/app/searchHistory")
public class AppSearchHistoryController {

    @Resource
    private SearchHistoryService searchHistoryService;

    /**
     * 列表
     */
    @Login
    @ApiOperation(value = "列表")
    @GetMapping("list")
    public Result<List<SearchHistory>> list(@RequestAttribute Long userId) {
        List<SearchHistory> voList = searchHistoryService.list(userId);
        return Result.success(voList);
    }

    @ApiOperation(value = "热门搜索")
    @GetMapping("hot")
    public Result<List<SearchHistoryVo>> hot() {
        List<SearchHistoryVo> voList = searchHistoryService.hot();
        return Result.success(voList);
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated SearchHistoryInfoBo searchHistoryInfoBo) {
        searchHistoryService.delete(searchHistoryInfoBo);
        return Result.success();
    }

    @Login
    @ApiOperation(value = "清空")
    @GetMapping("clear")
    public Result<Void> clear(@RequestAttribute Long userId) {
        searchHistoryService.clear(userId);
        return Result.success();
    }
}
