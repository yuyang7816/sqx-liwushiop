package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【user_money】的数据库操作Service
* @createDate 2024-07-23 13:07:46
*/
public interface IUserMoneyService extends IService<UserMoney> {

    UserMoney getByUserId(Long userId);

    void incr(Long userId, BigDecimal money);

    void decr(Long userId, BigDecimal money);
}
