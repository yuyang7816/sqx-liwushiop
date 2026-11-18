package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.WithdrawalAccount;
import com.sqx.domain.repository.mapper.giftMall.WithdrawalAccountMapper;
import com.sqx.domain.repository.service.giftMall.IWithdrawalAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 提现账号 服务实现类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
@Service
public class IWithdrawalAccountServiceImpl extends ServiceImpl<WithdrawalAccountMapper, WithdrawalAccount> implements IWithdrawalAccountService {

    @Override
    public List<WithdrawalAccount> getByUserId(Long userId) {
        LambdaQueryWrapper<WithdrawalAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WithdrawalAccount::getUserId, userId);

        return this.list(queryWrapper);
    }
}
