package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftOrderProduct;
import com.sqx.domain.repository.mapper.giftMall.GiftOrderProductMapper;
import com.sqx.domain.repository.service.giftMall.IGiftOrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ming
* @description 针对表【gift_order_product(订单商品表)】的数据库操作Service实现
* @createDate 2025-08-01 10:36:18
*/
@Service
public class IGiftOrderProductServiceImpl extends ServiceImpl<GiftOrderProductMapper, GiftOrderProduct>
    implements IGiftOrderProductService {

    @Override
    public List<GiftOrderProduct> getOrderProductsByOrderId(String orderNo) {
        LambdaQueryWrapper<GiftOrderProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftOrderProduct::getOrderNo, orderNo);

        return this.list(queryWrapper);
    }
}




