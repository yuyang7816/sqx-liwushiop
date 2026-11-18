package com.sqx.giftMall.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.entity.giftMall.GiftProductRule;
import com.sqx.giftMall.domain.vo.GiftProductRuleVo;

import java.util.List;

/**
 * @author chengming
 * @since 2024/7/15
 */
public class PageUtil {

    public static <T> Page<T> initPage(Integer page, Integer limit) {
        Page<T> pages;
        if (page != null && limit != null) {
            pages = new Page<>(page, limit);
        } else {
            pages = new Page<>(1, 10);
        }

        return pages;
    }

    public static <E,T> IPage<T> copyPage(IPage<E> iPage, List<T> voList) {
        IPage<T> page = new Page<>();
        page.setPages(iPage.getPages());
        page.setSize(iPage.getSize());
        page.setCurrent(iPage.getCurrent());
        page.setTotal(iPage.getTotal());
        page.setRecords(voList);

        return page;
    }
}
