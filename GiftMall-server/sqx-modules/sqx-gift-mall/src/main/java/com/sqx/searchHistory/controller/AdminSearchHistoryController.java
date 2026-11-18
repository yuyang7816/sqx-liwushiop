package com.sqx.searchHistory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.searchHistory.domain.bo.SearchHistoryInfoBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;
import com.sqx.searchHistory.domain.bo.SearchHistorySaveBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryUpdateBo;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.searchHistory.service.SearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 搜索历史 前端控制器
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@Api(value = "管理端-搜索历史", tags = "管理端-搜索历史")
@RestController
@RequestMapping("/admin/searchHistory")
public class AdminSearchHistoryController {

    @Resource
    private SearchHistoryService searchHistoryService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("save")
    public Result<Void> save(@RequestBody @Validated SearchHistorySaveBo searchHistorySaveBo) {
        searchHistoryService.saveInfo(searchHistorySaveBo);
        return Result.success();
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表")
    @PostMapping("page")
    public Result<IPage<SearchHistoryVo>> page(@RequestBody @Validated SearchHistoryPageBo searchHistoryPageBo) {
        IPage<SearchHistoryVo> voIPage = searchHistoryService.pageInfo(searchHistoryPageBo);
        return Result.success(voIPage);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息")
    @PostMapping("info")
    public Result<SearchHistoryVo> info(@RequestBody @Validated SearchHistoryInfoBo searchHistoryInfoBo) {
        SearchHistoryVo vo = searchHistoryService.info(searchHistoryInfoBo);
        return Result.success(vo);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Validated SearchHistoryUpdateBo searchHistoryUpdateBo) {
        searchHistoryService.updateInfo(searchHistoryUpdateBo);
        return Result.success();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public Result<Void> delete(@RequestBody @Validated SearchHistoryInfoBo searchHistoryInfoBo) {
        searchHistoryService.delete(searchHistoryInfoBo);
        return Result.success();
    }
}
