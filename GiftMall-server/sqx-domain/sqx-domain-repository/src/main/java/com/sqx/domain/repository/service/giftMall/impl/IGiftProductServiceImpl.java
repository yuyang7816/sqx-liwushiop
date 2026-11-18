package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.service.giftMall.IGiftProductService;
import com.sqx.domain.repository.mapper.giftMall.GiftProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【gift_product(商品基本信息)】的数据库操作Service实现
* @createDate 2024-07-15 16:45:14
*/
@Service
public class IGiftProductServiceImpl extends ServiceImpl<GiftProductMapper, GiftProduct>
    implements IGiftProductService {

    @Resource
    private GiftProductMapper giftProductMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void incrSales(Long orderId, int sales) {
        giftProductMapper.incrSales(orderId, sales);
    }
}




