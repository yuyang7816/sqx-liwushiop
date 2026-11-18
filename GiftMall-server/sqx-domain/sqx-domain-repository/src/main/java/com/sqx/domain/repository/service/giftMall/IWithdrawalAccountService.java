package com.sqx.domain.repository.service.giftMall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.WithdrawalAccount;

import java.util.List;

/**
 * <p>
 * 提现账号 服务类
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-06-20 14:02:39
 */
public interface IWithdrawalAccountService extends IService<WithdrawalAccount> {

    List<WithdrawalAccount> getByUserId(Long userId);
}
