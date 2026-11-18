package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.domain.repository.mapper.giftMall.GiftShareMapper;
import com.sqx.domain.repository.service.giftMall.IGiftShareService;
import org.springframework.stereotype.Service;

/**
* @author ming
* @description 针对表【gift_share(礼物分享表)】的数据库操作Service实现
* @createDate 2025-07-31 14:00:01
*/
@Service
public class IGiftShareServiceImpl extends ServiceImpl<GiftShareMapper, GiftShare>
    implements IGiftShareService {

    @Override
    public GiftShare getByOrderNo(String orderNo) {
        LambdaQueryWrapper<GiftShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GiftShare::getOrderNo, orderNo);
        return this.getOne(queryWrapper);
    }
}




