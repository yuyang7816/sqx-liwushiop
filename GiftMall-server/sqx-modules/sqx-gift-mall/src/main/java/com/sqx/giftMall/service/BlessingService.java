package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.BlessingListBo;
import com.sqx.giftMall.domain.bo.BlessingSaveBo;
import com.sqx.giftMall.domain.bo.BlessingUpdateBo;

/**
 * @author chengming
 * @since 2024/7/15
 */
public interface BlessingService {
    Result save(BlessingSaveBo blessingSaveBo);

    Result list(Integer page, Integer limit, BlessingListBo blessingListBo);

    Result info(Long blessingId);

    Result update(BlessingUpdateBo blessingUpdateBo);

    Result delete(Long blessingId);

    Result random();
}
