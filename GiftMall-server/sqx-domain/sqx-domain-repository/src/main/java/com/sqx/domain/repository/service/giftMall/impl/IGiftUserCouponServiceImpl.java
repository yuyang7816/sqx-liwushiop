package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.sqx.domain.repository.service.giftMall.IGiftUserCouponService;
import com.sqx.domain.repository.mapper.giftMall.GiftUserCouponMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Administrator
* @description 针对表【gift_user_coupon(优惠券用户领取记录)】的数据库操作Service实现
* @createDate 2024-07-18 10:15:30
*/
@Service
public class IGiftUserCouponServiceImpl extends ServiceImpl<GiftUserCouponMapper, GiftUserCoupon>
    implements IGiftUserCouponService {

    /**
     * 核销优惠券
     * @param userCouponsId 用户优惠券id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void usedCoupon(Long userCouponsId, Integer status) {
        UpdateWrapper<GiftUserCoupon> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftUserCoupon::getId, userCouponsId)
                .set(GiftUserCoupon::getStatus, status);

        this.update(new GiftUserCoupon(), updateWrapper);
    }
}




