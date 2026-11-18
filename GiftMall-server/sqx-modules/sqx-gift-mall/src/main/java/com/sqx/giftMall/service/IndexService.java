package com.sqx.giftMall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.domain.repository.domain.giftMall.bo.RechargeRecordBo;
import com.sqx.giftMall.domain.bo.StatisticsBo;
import com.sqx.giftMall.domain.vo.*;
import com.sqx.domain.repository.domain.giftMall.dto.RechargeRecordVo;

/**
 * @author chengming
 * @since 2025/6/27
 */
public interface IndexService {
    MyDataVo myData(Long userId);

    RechargeStatisticsVo rechargeStatistics(StatisticsBo statisticsBo);

    IPage<RechargeRecordVo> rechargeRecord(RechargeRecordBo rechargeRecordBo);

    IndexStatisticsVo indexStatistics();

    UserStatisticsVo userStatistics(StatisticsBo statisticsBo);

    OrderStatisticsVo orderStatistics(StatisticsBo statisticsBo);
}
