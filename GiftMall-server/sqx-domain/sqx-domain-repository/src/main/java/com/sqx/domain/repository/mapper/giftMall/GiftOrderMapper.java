package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.bo.OrderListBo;
import com.sqx.domain.repository.domain.giftMall.dto.OrderDto;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_order(订单)】的数据库操作Mapper
* @createDate 2024-07-19 09:40:05
* @Entity com.sqx.domain.repository.entity.giftMall.GiftOrder
*/
public interface GiftOrderMapper extends BaseMapper<GiftOrder> {

    IPage<OrderDto> page(Page<GiftOrder> initPage, @Param("param") OrderListBo orderListBo);

    IPage<OrderDto> selfRefundList(Page<GiftOrder> initPage, @Param("userId") Long userId);

    BigDecimal sum(@Param("statusList") List<Integer> statusList, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}




