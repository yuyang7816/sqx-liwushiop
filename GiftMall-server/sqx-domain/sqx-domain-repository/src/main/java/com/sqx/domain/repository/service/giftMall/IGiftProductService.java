package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【gift_product(商品基本信息)】的数据库操作Service
* @createDate 2024-07-15 16:45:14
*/
public interface IGiftProductService extends IService<GiftProduct> {

    /**
     * 增加商品销量
     * @param orderId 订单id
     * @param sales 销量
     */
    void incrSales(Long orderId, int sales);
}
