package com.sqx.domain.repository.mapper.giftMall;

import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author Administrator
* @description 针对表【user_money】的数据库操作Mapper
* @createDate 2024-07-23 13:07:46
* @Entity com.sqx.domain.repository.entity.giftMall.UserMoney
*/
public interface UserMoneyMapper extends BaseMapper<UserMoney> {

    void decr(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    void incr(@Param("userId") Long userId, @Param("money") BigDecimal money);
}




