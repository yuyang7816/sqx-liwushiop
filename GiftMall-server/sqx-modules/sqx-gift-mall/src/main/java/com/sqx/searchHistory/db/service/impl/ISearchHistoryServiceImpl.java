package com.sqx.searchHistory.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.searchHistory.db.mapper.SearchHistoryMapper;
import com.sqx.searchHistory.db.service.ISearchHistoryService;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 搜索历史 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@Service
public class ISearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public List<SearchHistoryVo> hotKeyWord() {
        return searchHistoryMapper.hotKeyWord();
    }

    @Override
    public IPage<SearchHistoryVo> pageInfo(Page<SearchHistory> initPage, SearchHistoryPageBo searchHistoryPageBo) {
        return searchHistoryMapper.pageInfo(initPage, searchHistoryPageBo);
    }
}
