package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.dto.GiftCardInfoDto;
import com.sqx.domain.repository.entity.giftMall.GiftCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【gift_card(卡片表)】的数据库操作Mapper
* @createDate 2024-07-15 10:33:33
* @Entity com.sqx.domain.repository.entity.giftMall.GiftCard
*/
public interface GiftCardMapper extends BaseMapper<GiftCard> {

    IPage<GiftCardInfoDto> listCard(Page<GiftCard> pages, @Param("cardCategoryId") Long cardCategoryId);

    IPage<GiftCardInfoDto> pageInfo(Page<GiftCard> pages, @Param("cardCategoryId") Long cardCategoryId, @Param("status") Integer status);
}




