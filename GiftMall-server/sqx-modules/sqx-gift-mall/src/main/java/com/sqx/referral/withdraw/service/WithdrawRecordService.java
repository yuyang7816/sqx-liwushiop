package com.sqx.referral.withdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.referral.withdraw.domain.bo.*;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import com.sqx.referral.withdraw.domain.vo.WithdrawStatisticsVo;

/**
 * 提现记录 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
public interface WithdrawRecordService {

    void saveInfo(WithdrawRecordSaveBo withdrawRecordSaveBo);

    IPage<WithdrawRecordVo> pageInfo(WithdrawRecordPageBo withdrawRecordPageBo);

    WithdrawRecordVo info(WithdrawRecordInfoBo withdrawRecordInfoBo);

    void updateInfo(WithdrawRecordUpdateBo withdrawRecordUpdateBo);

    void delete(WithdrawRecordInfoBo withdrawRecordInfoBo);

    void confirm(WithdrawRecordConfirmBo withdrawRecordConfirmBo);

    WithdrawStatisticsVo statistics(WithdrawStatisticsBo withdrawStatisticsBo);
}
