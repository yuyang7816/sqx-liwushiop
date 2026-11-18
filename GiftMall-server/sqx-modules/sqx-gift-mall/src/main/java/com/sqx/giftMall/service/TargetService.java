package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.SceneSaveBo;
import com.sqx.giftMall.domain.bo.SceneUpdateBo;
import com.sqx.giftMall.domain.bo.TargetSaveBo;
import com.sqx.giftMall.domain.bo.TargetUpdateBo;

/**
 * @author chengming
 * @since 2024/7/15
 */
public interface TargetService {
    Result list(Integer page, Integer limit, String name);

    Result save(TargetSaveBo targetSaveBo);

    Result update(TargetUpdateBo targetUpdateBo);

    Result delete(Long id);
}
