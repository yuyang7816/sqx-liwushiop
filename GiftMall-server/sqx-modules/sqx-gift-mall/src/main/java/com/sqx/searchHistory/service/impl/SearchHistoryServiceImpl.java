package com.sqx.searchHistory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.searchHistory.db.service.ISearchHistoryService;
import com.sqx.searchHistory.domain.bo.SearchHistoryInfoBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;
import com.sqx.searchHistory.domain.bo.SearchHistorySaveBo;
import com.sqx.searchHistory.domain.bo.SearchHistoryUpdateBo;
import com.sqx.searchHistory.service.SearchHistoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 搜索历史 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Resource
    private ISearchHistoryService iSearchHistoryService;

    @Override
    public void saveInfo(SearchHistorySaveBo searchHistorySaveBo) {
        SearchHistory searchHistory = new SearchHistory();
        BeanUtils.copyProperties(searchHistorySaveBo, searchHistory);

        iSearchHistoryService.save(searchHistory);
    }

    @Override
    public IPage<SearchHistoryVo> pageInfo(SearchHistoryPageBo searchHistoryPageBo) {
        Page<SearchHistory> initPage = PageUtil.initPage(searchHistoryPageBo.getPage(), searchHistoryPageBo.getLimit());

        IPage<SearchHistoryVo> iPage = iSearchHistoryService.pageInfo(initPage, searchHistoryPageBo);
        return iPage;
    }

    @Override
    public SearchHistoryVo info(SearchHistoryInfoBo searchHistoryInfoBo) {
        SearchHistory searchHistory = iSearchHistoryService.getById(searchHistoryInfoBo.getId());
        if (Objects.isNull(searchHistory)) {
            throw new BizException("信息不存在");
        }

        SearchHistoryVo vo = new SearchHistoryVo();
        BeanUtils.copyProperties(searchHistory, vo);
        return vo;
    }

    @Override
    public void updateInfo(SearchHistoryUpdateBo searchHistoryUpdateBo) {
        SearchHistory searchHistory = new SearchHistory();
        BeanUtils.copyProperties(searchHistoryUpdateBo, searchHistory);

        iSearchHistoryService.updateById(searchHistory);
    }

    @Override
    public void delete(SearchHistoryInfoBo searchHistoryInfoBo) {
        iSearchHistoryService.removeById(searchHistoryInfoBo.getId());
    }

    @Override
    public List<SearchHistory> list(Long userId) {
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SearchHistory::getUserId, userId);

        List<SearchHistory> list = iSearchHistoryService.list(queryWrapper);
        return list;
    }

    @Override
    public List<SearchHistoryVo> hot() {
        return iSearchHistoryService.hotKeyWord();
    }

    @Override
    public void clear(Long userId) {
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SearchHistory::getUserId, userId);

        iSearchHistoryService.remove(queryWrapper);
    }
}
