package com.sqx.domain.repository.service.giftMall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.giftMall.GiftCart;

/**
* @author Administrator
* @description 针对表【gift_cart(购物车)】的数据库操作Service
* @createDate 2024-07-17 15:02:14
*/
public interface IGiftCartService extends IService<GiftCart> {

    void clear(Long userId);

    void removeByProductId(Long userId, Long productId, Long skuId);
}
