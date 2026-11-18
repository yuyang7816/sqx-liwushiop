package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.RuleListBo;
import com.sqx.giftMall.domain.bo.RuleSaveBo;
import com.sqx.giftMall.domain.bo.RuleUpdateBo;

/**
 * @author chengming
 * @since 2024/7/17
 */
public interface RuleService {
    Result page(Integer page, Integer limit, RuleListBo ruleListBo);

    Result info(Long id);

    Result save(RuleSaveBo ruleSaveBo);

    Result update(RuleUpdateBo ruleUpdateBo);

    Result delete(Long id);

    Result list(RuleListBo ruleListBo);
}
