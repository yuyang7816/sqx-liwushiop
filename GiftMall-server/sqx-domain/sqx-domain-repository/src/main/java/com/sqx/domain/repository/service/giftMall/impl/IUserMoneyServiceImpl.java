package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.sqx.domain.repository.service.giftMall.IUserMoneyService;
import com.sqx.domain.repository.mapper.giftMall.UserMoneyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【user_money】的数据库操作Service实现
* @createDate 2024-07-23 13:07:46
*/
@Service
public class IUserMoneyServiceImpl extends ServiceImpl<UserMoneyMapper, UserMoney>
    implements IUserMoneyService {

    @Resource
    private UserMoneyMapper userMoneyMapper;

    @Override
    public UserMoney getByUserId(Long userId) {
        LambdaQueryWrapper<UserMoney> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserMoney::getUserId, userId);

        return this.getOne(queryWrapper);
    }

    @Override
    public void incr(Long userId, BigDecimal money) {
        userMoneyMapper.incr(userId, money);
    }

    @Override
    public void decr(Long userId, BigDecimal money) {
        userMoneyMapper.decr(userId, money);
    }
}




