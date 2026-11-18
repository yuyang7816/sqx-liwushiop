package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.UserMoneyDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【user_money_details】的数据库操作Service
* @createDate 2024-07-23 13:14:18
*/
public interface IUserMoneyDetailsService extends IService<UserMoneyDetails> {

    void saveGiftOrder(Long userId, BigDecimal amount);
}
