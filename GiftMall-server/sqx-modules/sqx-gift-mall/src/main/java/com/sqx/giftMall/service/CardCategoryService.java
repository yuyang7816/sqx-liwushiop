package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CardCategoryListBo;
import com.sqx.giftMall.domain.bo.CardCategorySaveBo;
import com.sqx.giftMall.domain.bo.CardCategoryUpdateBo;

/**
 * @author chengming
 * @since 2024/7/15
 */
public interface CardCategoryService {
    Result list(Integer page, Integer limit, CardCategoryListBo cardCategoryListBo);

    Result info(Long cardCategoryId);

    Result save(CardCategorySaveBo cardCategorySaveBo);

    Result update(CardCategoryUpdateBo cardCategoryUpdateBo);

    Result delete(Long cardCategoryId);

    Result listCardCategory(Integer page, Integer limit);
}
