package com.sqx.referral.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.giftMall.WithdrawalAccount;
import com.sqx.domain.repository.service.giftMall.IWithdrawalAccountService;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountInfoBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountPageBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountSaveBo;
import com.sqx.referral.withdraw.domain.bo.WithdrawalAccountUpdateBo;
import com.sqx.referral.withdraw.domain.vo.WithdrawalAccountVo;
import com.sqx.referral.withdraw.service.WithdrawalAccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提现账号 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
@Service
public class WithdrawalAccountServiceImpl implements WithdrawalAccountService {

    @Resource
    private IWithdrawalAccountService iWithdrawalAccountService;

    @Override
    public void saveInfo(WithdrawalAccountSaveBo withdrawalAccountSaveBo) {
        WithdrawalAccount withdrawalAccount = new WithdrawalAccount();
        BeanUtils.copyProperties(withdrawalAccountSaveBo, withdrawalAccount);

        iWithdrawalAccountService.save(withdrawalAccount);
    }

    @Override
    public IPage<WithdrawalAccountVo> pageInfo(WithdrawalAccountPageBo withdrawalAccountPageBo) {
        Page<WithdrawalAccount> initPage = PageUtil.initPage(withdrawalAccountPageBo.getPage(), withdrawalAccountPageBo.getLimit());

        QueryWrapper<WithdrawalAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(withdrawalAccountPageBo.getAccountName()), WithdrawalAccount::getAccountName, withdrawalAccountPageBo.getAccountName())
                .like(StringUtils.isNotBlank(withdrawalAccountPageBo.getAccountNumber()), WithdrawalAccount::getAccountNumber, withdrawalAccountPageBo.getAccountNumber())
                .eq(Objects.nonNull(withdrawalAccountPageBo.getType()), WithdrawalAccount::getType, withdrawalAccountPageBo.getType())
                .orderByDesc(WithdrawalAccount::getCreateTime);

        if (Objects.nonNull(withdrawalAccountPageBo.getSource()) && 1 == withdrawalAccountPageBo.getSource()) {
            queryWrapper.lambda()
                    .eq(Objects.nonNull(withdrawalAccountPageBo.getUserId()), WithdrawalAccount::getUserId, withdrawalAccountPageBo.getUserId());
        } else {
            queryWrapper.lambda().eq(WithdrawalAccount::getUserId, withdrawalAccountPageBo.getUserId());
        }

        IPage<WithdrawalAccount> iPage = iWithdrawalAccountService.page(initPage, queryWrapper);
        List<WithdrawalAccountVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (WithdrawalAccount record : iPage.getRecords()) {
                WithdrawalAccountVo vo = new WithdrawalAccountVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        IPage<WithdrawalAccountVo> voIPage = PageUtil.copyPage(iPage, voList);
        return voIPage;
    }

    @Override
    public WithdrawalAccountVo info(WithdrawalAccountInfoBo withdrawalAccountInfoBo) {
        WithdrawalAccount withdrawalAccount = iWithdrawalAccountService.getById(withdrawalAccountInfoBo.getId());
        if (Objects.isNull(withdrawalAccount)) {
            throw new BizException("信息不存在");
        }

        WithdrawalAccountVo vo = new WithdrawalAccountVo();
        BeanUtils.copyProperties(withdrawalAccount, vo);
        return vo;
    }

    @Override
    public void updateInfo(WithdrawalAccountUpdateBo withdrawalAccountUpdateBo) {
        WithdrawalAccount withdrawalAccount = new WithdrawalAccount();
        BeanUtils.copyProperties(withdrawalAccountUpdateBo, withdrawalAccount);

        iWithdrawalAccountService.updateById(withdrawalAccount);
    }

    @Override
    public void delete(WithdrawalAccountInfoBo withdrawalAccountInfoBo) {
        iWithdrawalAccountService.removeById(withdrawalAccountInfoBo.getId());
    }
}
