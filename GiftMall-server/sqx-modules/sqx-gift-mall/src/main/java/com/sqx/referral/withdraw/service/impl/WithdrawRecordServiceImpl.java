package com.sqx.referral.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.ReferralUserCommission;
import com.sqx.domain.repository.entity.giftMall.WithdrawRecord;
import com.sqx.domain.repository.service.giftMall.IWithdrawalAccountService;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.referral.user.db.service.IReferralUserCommissionService;
import com.sqx.referral.withdraw.db.service.IWithdrawRecordService;
import com.sqx.referral.withdraw.domain.bo.*;
import com.sqx.referral.withdraw.domain.vo.WithdrawStatisticsVo;
import com.sqx.referral.withdraw.service.WithdrawRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提现记录 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@Service
public class WithdrawRecordServiceImpl implements WithdrawRecordService {

    @Resource
    private IWithdrawRecordService iWithdrawRecordService;
    @Resource
    private IReferralUserCommissionService iReferralUserCommissionService;
    @Resource
    private CommonInfoService commonInfoService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IWithdrawalAccountService iWithdrawalAccountService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveInfo(WithdrawRecordSaveBo withdrawRecordSaveBo) {
        // 用户收益余额
        ReferralUserCommission referralUserCommission = iReferralUserCommissionService.getByUserId(withdrawRecordSaveBo.getUserId());
        if (Objects.isNull(referralUserCommission)) {
            throw new BizException("用户收益不存在");
        }

        if (withdrawRecordSaveBo.getAmount().compareTo(referralUserCommission.getCommission()) > 0) {
            throw new BizException("余额不足");
        }

        String maxAmount = commonInfoService.getCommonValue(153);
        if (withdrawRecordSaveBo.getAmount().compareTo(new BigDecimal(maxAmount)) > 0) {
            throw new BizException("最大提现金额为" + maxAmount);
        }

        String minAmount = commonInfoService.getCommonValue(112);
        if (withdrawRecordSaveBo.getAmount().compareTo(new BigDecimal(minAmount)) < 0) {
            throw new BizException("最小提现金额为" + maxAmount);
        }

        // 计算到账金额
        String serviceFeeRate = commonInfoService.getCommonValue(152);
        BigDecimal serviceFee = withdrawRecordSaveBo.getAmount().multiply(new BigDecimal(serviceFeeRate));
        BigDecimal actualAmount = withdrawRecordSaveBo.getAmount().subtract(serviceFee);

        // 保存提现记录
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        BeanUtils.copyProperties(withdrawRecordSaveBo, withdrawRecord);
        withdrawRecord.setActualAmount(actualAmount);
        withdrawRecord.setServiceFee(serviceFee);
        withdrawRecord.setStatus(1);

        iWithdrawRecordService.save(withdrawRecord);

        // 扣除用户收益余额
        iReferralUserCommissionService.decr(withdrawRecordSaveBo.getUserId(), withdrawRecordSaveBo.getAmount());
    }

    @Override
    public IPage<WithdrawRecordVo> pageInfo(WithdrawRecordPageBo withdrawRecordPageBo) {
        Page<WithdrawRecord> initPage = PageUtil.initPage(withdrawRecordPageBo.getPage(), withdrawRecordPageBo.getLimit());

        IPage<WithdrawRecordVo> voIPage = iWithdrawRecordService.pageInfo(initPage, withdrawRecordPageBo);

        return voIPage;
    }

    @Override
    public WithdrawRecordVo info(WithdrawRecordInfoBo withdrawRecordInfoBo) {
        WithdrawRecord withdrawRecord = iWithdrawRecordService.getById(withdrawRecordInfoBo.getId());
        if (Objects.isNull(withdrawRecord)) {
            throw new BizException("信息不存在");
        }

        WithdrawRecordVo vo = new WithdrawRecordVo();
        BeanUtils.copyProperties(withdrawRecord, vo);

        UserEntity userEntity = iUserService.getById(withdrawRecord.getUserId());
        if (Objects.nonNull(userEntity)) {
            vo.setUserEntity(userEntity);
        }

        return vo;
    }

    @Override
    public void updateInfo(WithdrawRecordUpdateBo withdrawRecordUpdateBo) {
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        BeanUtils.copyProperties(withdrawRecordUpdateBo, withdrawRecord);

        iWithdrawRecordService.updateById(withdrawRecord);
    }

    @Override
    public void delete(WithdrawRecordInfoBo withdrawRecordInfoBo) {
        iWithdrawRecordService.removeById(withdrawRecordInfoBo.getId());
    }

    @Override
    public void confirm(WithdrawRecordConfirmBo withdrawRecordConfirmBo) {
        WithdrawRecord withdrawRecord = iWithdrawRecordService.getById(withdrawRecordConfirmBo.getId());
        if (Objects.isNull(withdrawRecord)) {
            throw new BizException("提现信息不存在");
        }

        if (2 == withdrawRecordConfirmBo.getStatus()) {
            // 提现成功
            withdrawRecord.setStatus(2);
            withdrawRecord.setWithdrawTime(LocalDateTime.now());

            iWithdrawRecordService.updateById(withdrawRecord);
        } else if (3 == withdrawRecordConfirmBo.getStatus()) {
            // 拒绝提现
            withdrawRecord.setStatus(3);
            withdrawRecord.setReason(withdrawRecordConfirmBo.getReason());

            iWithdrawRecordService.updateById(withdrawRecord);

            // 回退提现金额
            iReferralUserCommissionService.incr(withdrawRecord.getUserId(), withdrawRecord.getAmount());
        } else {
            throw new BizException("非法操作");
        }
    }

    @Override
    public WithdrawStatisticsVo statistics(WithdrawStatisticsBo withdrawStatisticsBo) {
        List<LocalDateTime> formattedDateRange = getFormattedDateRange(withdrawStatisticsBo.getFindDate(), withdrawStatisticsBo.getFindType());
        LocalDateTime startTime = formattedDateRange.get(0);
        LocalDateTime endTime = formattedDateRange.get(1);

        BigDecimal totalAmount = iWithdrawRecordService.sumAmount(startTime, endTime);

        QueryWrapper<WithdrawRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(WithdrawRecord::getCreateTime, startTime, endTime);
        Long totalCount = iWithdrawRecordService.count(queryWrapper);

        queryWrapper.lambda().eq(WithdrawRecord::getStatus, 1);
        Long waitCount = iWithdrawRecordService.count(queryWrapper);

        WithdrawStatisticsVo vo = new WithdrawStatisticsVo();
        vo.setTotalAmount(totalAmount);
        vo.setTotalCount(totalCount);
        vo.setWaitCount(waitCount);

        return vo;
    }

    public List<LocalDateTime> getFormattedDateRange(LocalDate findDate, String findType) {
        LocalDateTime start;
        LocalDateTime end;

        switch (findType.toLowerCase()) {
            case "day":
                start = findDate.atStartOfDay();
                end = findDate.atTime(23, 59, 59);
                break;

            case "month":
                YearMonth yearMonth = YearMonth.from(findDate);
                start = yearMonth.atDay(1).atStartOfDay();
                end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
                break;

            case "year":
                Year year = Year.from(findDate);
                start = year.atDay(1).atStartOfDay(); // 1月1日
                LocalDate lastDay = LocalDate.of(year.getValue(), 12, 31);
                end = lastDay.atTime(23, 59, 59);
                break;

            default:
                throw new IllegalArgumentException("不支持的 findType: " + findType);
        }

        List<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(start);
        localDateTimes.add(end);
        return localDateTimes;
    }
}
