package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.CardGroupByCategoryListBo;
import com.sqx.giftMall.domain.bo.CardListBo;
import com.sqx.giftMall.domain.bo.CardSaveBo;
import com.sqx.giftMall.domain.bo.CardUpdateBo;

/**
 * @author chengming
 * @since 2024/7/15
 */
public interface CardService {
    Result list(Integer page, Integer limit, CardListBo cardListBo);

    Result info(Long cardId);

    Result save(CardSaveBo cardSaveBo);

    Result update(CardUpdateBo cardUpdateBo);

    Result enable(Long cardId, Integer status);

    Result listCard(Integer page, Integer limit, CardGroupByCategoryListBo cardGroupByCategoryListBo);

    Result delete(Long cardId);
}
