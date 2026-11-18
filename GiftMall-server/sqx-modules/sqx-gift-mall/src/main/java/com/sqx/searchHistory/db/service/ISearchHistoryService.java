package com.sqx.searchHistory.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;

import java.util.List;

/**
 * <p>
 * 搜索历史 服务类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
public interface ISearchHistoryService extends IService<SearchHistory> {

    List<SearchHistoryVo> hotKeyWord();

    IPage<SearchHistoryVo> pageInfo(Page<SearchHistory> initPage, SearchHistoryPageBo searchHistoryPageBo);
}
