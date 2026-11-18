package com.sqx.domain.repository.service.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【gift_user_coupon(优惠券用户领取记录)】的数据库操作Service
* @createDate 2024-07-18 10:15:30
*/
public interface IGiftUserCouponService extends IService<GiftUserCoupon> {

    /**
     * 核销优惠券
     * @param userCouponsId
     */
    void usedCoupon(Long userCouponsId, Integer status);
}
