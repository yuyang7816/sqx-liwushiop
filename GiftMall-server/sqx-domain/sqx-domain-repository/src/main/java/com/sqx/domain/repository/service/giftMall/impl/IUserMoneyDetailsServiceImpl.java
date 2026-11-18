package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.UserMoneyDetails;
import com.sqx.domain.repository.mapper.UserMoneyDetailsMapper;
import com.sqx.domain.repository.service.giftMall.IUserMoneyDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【user_money_details】的数据库操作Service实现
* @createDate 2024-07-23 13:14:18
*/
@Service
public class IUserMoneyDetailsServiceImpl extends ServiceImpl<UserMoneyDetailsMapper, UserMoneyDetails>
    implements IUserMoneyDetailsService {

    @Override
    public void saveGiftOrder(Long userId, BigDecimal amount) {
        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(userId);
        userMoneyDetails.setMoney(amount);
        userMoneyDetails.setTitle("购买商品");
        userMoneyDetails.setClassify(2);
        userMoneyDetails.setType(2);
        userMoneyDetails.setState(2);
        userMoneyDetails.setContent("购买商品花费: " + amount);

        this.save(userMoneyDetails);
    }
}




