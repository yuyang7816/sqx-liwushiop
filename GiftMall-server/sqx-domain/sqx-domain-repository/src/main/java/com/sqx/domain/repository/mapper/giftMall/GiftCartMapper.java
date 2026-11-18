package com.sqx.domain.repository.mapper.giftMall;

import com.sqx.domain.repository.entity.giftMall.GiftCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【gift_cart(购物车)】的数据库操作Mapper
* @createDate 2024-07-17 15:02:14
* @Entity com.sqx.domain.repository.entity.giftMall.GiftCart
*/
public interface GiftCartMapper extends BaseMapper<GiftCart> {

    Long sum(@Param("userId") Long userId);
}




