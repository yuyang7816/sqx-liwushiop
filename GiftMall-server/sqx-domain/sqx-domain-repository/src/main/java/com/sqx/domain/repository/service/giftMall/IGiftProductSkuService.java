package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_product_sku(商品SKU)】的数据库操作Service
* @createDate 2024-07-16 17:46:55
*/
public interface IGiftProductSkuService extends IService<GiftProductSku> {

    List<GiftProductSku> findByProductId(Long productId);

    boolean checkStock(Long skuId, Integer number);

    void lessStock(Long skuId, Integer number);

    void rollbackStock(Long skuId, Integer number);

    /**
     * 增加销量
     * @param skuId skuId
     * @param num 数量
     */
    void incrSales(Long skuId, Integer num);
}
