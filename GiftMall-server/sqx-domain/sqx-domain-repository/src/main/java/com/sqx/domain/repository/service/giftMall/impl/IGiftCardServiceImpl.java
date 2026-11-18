package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.domain.giftMall.dto.GiftCardInfoDto;
import com.sqx.domain.repository.entity.giftMall.GiftCard;
import com.sqx.domain.repository.entity.giftMall.GiftCart;
import com.sqx.domain.repository.service.giftMall.IGiftCardService;
import com.sqx.domain.repository.mapper.giftMall.GiftCardMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【gift_card(卡片表)】的数据库操作Service实现
* @createDate 2024-07-15 10:33:33
*/
@Service
public class IGiftCardServiceImpl extends ServiceImpl<GiftCardMapper, GiftCard>
    implements IGiftCardService {

    @Override
    public IPage<GiftCardInfoDto> listCard(Page<GiftCard> pages, Long cardCategoryId) {
        return baseMapper.listCard(pages, cardCategoryId);
    }

    @Override
    public IPage<GiftCardInfoDto> pageInfo(Page<GiftCard> pages, Long cardCategoryId, Integer status) {
        return baseMapper.pageInfo(pages, cardCategoryId, status);
    }
}




