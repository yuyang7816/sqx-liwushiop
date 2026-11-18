package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftCart;
import com.sqx.domain.repository.service.giftMall.IGiftCartService;
import com.sqx.domain.repository.mapper.giftMall.GiftCartMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【gift_cart(购物车)】的数据库操作Service实现
* @createDate 2024-07-17 15:02:14
*/
@Service
public class IGiftCartServiceImpl extends ServiceImpl<GiftCartMapper, GiftCart>
    implements IGiftCartService {

    @Override
    public void clear(Long userId) {
        LambdaQueryWrapper<GiftCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftCart::getUserId, userId);

        this.remove(queryWrapper);
    }

    @Override
    public void removeByProductId(Long userId, Long productId, Long skuId) {
        LambdaQueryWrapper<GiftCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(GiftCart::getUserId, userId)
                .eq(GiftCart::getProductId, productId)
                .eq(GiftCart::getSkuId, skuId);

        this.remove(queryWrapper);
    }
}




