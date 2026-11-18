package com.sqx.referral.withdraw.db.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.WithdrawRecord;
import com.sqx.domain.repository.mapper.giftMall.WithdrawRecordMapper;
import com.sqx.referral.withdraw.db.service.IWithdrawRecordService;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 提现记录 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Service
public class IWithdrawRecordServiceImpl extends ServiceImpl<WithdrawRecordMapper, WithdrawRecord> implements IWithdrawRecordService {

    @Resource
    private WithdrawRecordMapper withdrawRecordMapper;

    @Override
    public IPage<WithdrawRecordVo> pageInfo(Page<WithdrawRecord> initPage, WithdrawRecordPageBo withdrawRecordPageBo) {
        return withdrawRecordMapper.pageInfo(initPage, withdrawRecordPageBo);
    }

    @Override
    public BigDecimal sumAmount(LocalDateTime startTime, LocalDateTime endTime) {
        return withdrawRecordMapper.sumAmount(startTime, endTime);
    }
}
