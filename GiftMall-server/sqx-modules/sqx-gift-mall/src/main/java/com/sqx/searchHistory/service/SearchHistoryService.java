package com.sqx.searchHistory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.searchHistory.domain.bo.SearchHistoryInfoBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;
import com.sqx.searchHistory.domain.bo.SearchHistorySaveBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryUpdateBo;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;

import java.util.List;

/**
 * 搜索历史 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
public interface SearchHistoryService {

    void saveInfo(SearchHistorySaveBo searchHistorySaveBo);

    IPage<SearchHistoryVo> pageInfo(SearchHistoryPageBo searchHistoryPageBo);

    SearchHistoryVo info(SearchHistoryInfoBo searchHistoryInfoBo);

    void updateInfo(SearchHistoryUpdateBo searchHistoryUpdateBo);

    void delete(SearchHistoryInfoBo searchHistoryInfoBo);

    List<SearchHistory> list(Long userId);

    List<SearchHistoryVo> hot();

    void clear(Long userId);
}
