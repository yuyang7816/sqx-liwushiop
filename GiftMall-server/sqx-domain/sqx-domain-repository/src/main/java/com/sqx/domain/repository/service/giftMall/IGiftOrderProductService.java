package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftOrderProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ming
* @description 针对表【gift_order_product(订单商品表)】的数据库操作Service
* @createDate 2025-08-01 10:36:18
*/
public interface IGiftOrderProductService extends IService<GiftOrderProduct> {

    /**
     * 根据orderNo获取订单商品列表
     * @param orderNo 订单编号
     * @return
     */
    List<GiftOrderProduct> getOrderProductsByOrderId(String orderNo);
}
