package com.sqx.referral.withdraw.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.WithdrawRecord;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 提现记录 服务类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
public interface IWithdrawRecordService extends IService<WithdrawRecord> {

    IPage<WithdrawRecordVo> pageInfo(Page<WithdrawRecord> initPage, WithdrawRecordPageBo withdrawRecordPageBo);

    BigDecimal sumAmount(LocalDateTime startTime, LocalDateTime endTime);
}
