package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftProductAttrValue;
import com.sqx.domain.repository.service.giftMall.IGiftProductAttrValueService;
import com.sqx.domain.repository.mapper.giftMall.GiftProductAttrValueMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_product_attr_value(商品属性值)】的数据库操作Service实现
* @createDate 2024-07-16 17:23:28
*/
@Service
public class IGiftProductAttrValueServiceImpl extends ServiceImpl<GiftProductAttrValueMapper, GiftProductAttrValue>
    implements IGiftProductAttrValueService {

    @Override
    public List<GiftProductAttrValue> findByProductId(Long id) {
        QueryWrapper<GiftProductAttrValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttrValue::getProductId, id);

        return this.list(queryWrapper);
    }
}




