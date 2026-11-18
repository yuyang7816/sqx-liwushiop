package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.dto.GiftUserCouponDto;
import com.sqx.domain.repository.entity.giftMall.GiftUserCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【gift_user_coupon(优惠券用户领取记录)】的数据库操作Mapper
* @createDate 2024-07-18 10:15:30
* @Entity com.sqx.domain.repository.entity.giftMall.GiftUserCoupon
*/
public interface GiftUserCouponMapper extends BaseMapper<GiftUserCoupon> {

    IPage<GiftUserCouponDto> list(Page<GiftUserCoupon> initPage, String couponName);
}




