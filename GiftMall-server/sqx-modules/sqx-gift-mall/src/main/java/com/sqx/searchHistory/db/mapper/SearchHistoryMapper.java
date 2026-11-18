package com.sqx.searchHistory.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.entity.giftMall.SearchHistory;
import com.sqx.domain.repository.domain.giftMall.dto.SearchHistoryVo;
import com.sqx.searchHistory.domain.bo.SearchHistoryPageBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 搜索历史 Mapper 接口
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:23:52
 */
public interface SearchHistoryMapper extends BaseMapper<SearchHistory> {

    List<SearchHistoryVo> hotKeyWord();

    IPage<SearchHistoryVo> pageInfo(Page<SearchHistory> initPage, @Param("param") SearchHistoryPageBo searchHistoryPageBo);
}
