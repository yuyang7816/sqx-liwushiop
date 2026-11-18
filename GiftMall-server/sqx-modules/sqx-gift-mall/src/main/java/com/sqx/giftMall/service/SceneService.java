package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.SceneSaveBo;
import com.sqx.giftMall.domain.bo.SceneUpdateBo;

/**
 * @author chengming
 * @since 2024/7/15
 */
public interface SceneService {
    Result list(Integer page, Integer limit, String name);

    Result save(SceneSaveBo sceneSaveBo);

    Result update(SceneUpdateBo sceneUpdateBo);

    Result delete(Long id);
}
