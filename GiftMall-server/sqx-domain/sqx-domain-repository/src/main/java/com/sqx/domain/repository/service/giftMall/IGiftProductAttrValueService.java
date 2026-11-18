package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_product_attr_value(商品属性值)】的数据库操作Service
* @createDate 2024-07-16 17:23:28
*/
public interface IGiftProductAttrValueService extends IService<GiftProductAttrValue> {

    List<GiftProductAttrValue> findByProductId(Long id);
}
