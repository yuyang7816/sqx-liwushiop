package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ming
* @description 针对表【gift_share(礼物分享表)】的数据库操作Service
* @createDate 2025-07-31 14:00:01
*/
public interface IGiftShareService extends IService<GiftShare> {

    GiftShare getByOrderNo(String orderNo);
}
