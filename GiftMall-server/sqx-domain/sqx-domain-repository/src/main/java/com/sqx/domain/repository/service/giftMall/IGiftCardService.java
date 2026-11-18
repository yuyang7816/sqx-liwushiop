package com.sqx.domain.repository.service.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.dto.GiftCardInfoDto;
import com.sqx.domain.repository.entity.giftMall.GiftCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【gift_card(卡片表)】的数据库操作Service
* @createDate 2024-07-15 10:33:33
*/
public interface IGiftCardService extends IService<GiftCard> {

    IPage<GiftCardInfoDto> listCard(Page<GiftCard> pages, Long cardCategoryId);

    IPage<GiftCardInfoDto> pageInfo(Page<GiftCard> pages, Long cardCategoryId, Integer status);
}
