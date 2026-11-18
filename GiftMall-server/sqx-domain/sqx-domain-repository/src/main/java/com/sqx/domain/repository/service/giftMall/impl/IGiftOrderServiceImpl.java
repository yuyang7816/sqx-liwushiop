package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.mapper.giftMall.GiftOrderMapper;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_order(订单)】的数据库操作Service实现
* @createDate 2024-07-19 09:40:05
*/
@Service
public class IGiftOrderServiceImpl extends ServiceImpl<GiftOrderMapper, GiftOrder>
    implements IGiftOrderService {

    @Resource
    private GiftOrderMapper giftOrderMapper;

    @Override
    public void orderNotify(Long orderId, BigDecimal money, String way) {

    }

    @Override
    public GiftOrder getByOrderNo(String orderNo) {
        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftOrder::getOrderNo, orderNo);

        return this.getOne(queryWrapper);
    }

    @Override
    public BigDecimal sum(List<Integer> statusList, LocalDateTime startTime, LocalDateTime endTime) {
        return giftOrderMapper.sum(statusList, startTime, endTime);
    }
}




