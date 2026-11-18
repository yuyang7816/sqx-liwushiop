package com.sqx.domain.repository.service.giftMall;

import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import com.sqx.domain.repository.entity.giftMall.GiftProductAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_product_attr(商品属性)】的数据库操作Service
* @createDate 2024-07-16 17:22:00
*/
public interface IGiftProductAttrService extends IService<GiftProductAttr> {

    List<GiftProductAttrDto> findByProductId(Long id);

    Result saveBody(GiftProductAttrDto giftProductAttr);

    Result updateBody(Long productId, List<GiftProductAttrDto> giftProductAttrList);

    void deleteAttrAndValue(Long productId);
}
