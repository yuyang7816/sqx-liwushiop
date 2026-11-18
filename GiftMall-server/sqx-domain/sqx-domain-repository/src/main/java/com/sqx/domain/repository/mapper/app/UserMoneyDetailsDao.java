package com.sqx.domain.repository.mapper.app;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.domain.repository.entity.app.UserMoneyDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper
public interface UserMoneyDetailsDao extends BaseMapper<UserMoneyDetails> {
    Double monthIncome(@Param("date") String date,@Param("userId") Long userId);
    Double selectMyProfit(Long userId);

    BigDecimal sumRechargeAmount(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
