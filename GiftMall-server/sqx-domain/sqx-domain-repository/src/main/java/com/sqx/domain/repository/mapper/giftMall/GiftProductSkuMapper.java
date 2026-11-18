package com.sqx.domain.repository.mapper.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【gift_product_sku(商品SKU)】的数据库操作Mapper
* @createDate 2024-07-16 17:46:55
* @Entity com.sqx.domain.repository.entity.giftMall.GiftProductSku
*/
public interface GiftProductSkuMapper extends BaseMapper<GiftProductSku> {

    void lessStock(@Param("skuId") Long skuId, @Param("number") Integer number);

    void rollbackStock(@Param("skuId") Long skuId, @Param("number") Integer number);

    void incrSales(@Param("skuId") Long skuId, @Param("num") Integer num);
}




