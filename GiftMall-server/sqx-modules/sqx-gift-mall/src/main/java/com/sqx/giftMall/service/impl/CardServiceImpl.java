package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.domain.giftMall.dto.GiftCardInfoDto;
import com.sqx.domain.repository.entity.giftMall.GiftCard;
import com.sqx.domain.repository.service.giftMall.IGiftCardService;
import com.sqx.giftMall.domain.bo.CardGroupByCategoryListBo;
import com.sqx.giftMall.domain.bo.CardListBo;
import com.sqx.giftMall.domain.bo.CardSaveBo;
import com.sqx.giftMall.domain.bo.CardUpdateBo;
import com.sqx.giftMall.enums.CardStatus;
import com.sqx.giftMall.service.CardService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Service
public class CardServiceImpl implements CardService {
    @Resource
    private IGiftCardService giftCardService;

    @Override
    public Result list(Integer page, Integer limit, CardListBo cardListBo) {
        Page<GiftCard> pages = PageUtil.initPage(page, limit);

        IPage<GiftCardInfoDto> giftCardIPage = giftCardService.pageInfo(pages, cardListBo.getCardCategoryId(), cardListBo.getStatus());

        return Result.success(giftCardIPage);
    }

    @Override
    public Result info(Long cardId) {
        GiftCard giftCard = giftCardService.getById(cardId);
        if (Objects.isNull(giftCard)) {
            throw new SqxException("卡片不存在");
        }

        return Result.success(giftCard);
    }

    @Override
    public Result save(CardSaveBo cardSaveBo) {
        GiftCard giftCard = new GiftCard();
        BeanUtils.copyProperties(cardSaveBo, giftCard);
        giftCard.setStatus(CardStatus.ENABLE.code());

        // 默认排序号99
        if (Objects.isNull(cardSaveBo.getSort())) {
            giftCard.setSort(99);
        }

        if (!giftCardService.save(giftCard)) {
            throw new SqxException("卡片保存失败");
        }

        return Result.success();
    }

    @Override
    public Result update(CardUpdateBo cardUpdateBo) {
        if (!checkCardExistById(cardUpdateBo.getCardId())) {
            throw new SqxException("卡片信息不存在");
        }

        UpdateWrapper<GiftCard> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftCard::getCardId, cardUpdateBo.getCardId())
                .set(StringUtils.isNotBlank(cardUpdateBo.getCardImg()), GiftCard::getCardImg, cardUpdateBo.getCardImg())
                .set(Objects.nonNull(cardUpdateBo.getCardCategoryId()), GiftCard::getCardCategoryId, cardUpdateBo.getCardCategoryId())
                .set(Objects.nonNull(cardUpdateBo.getSort()), GiftCard::getSort, cardUpdateBo.getSort());

        if (!giftCardService.update(new GiftCard(), updateWrapper)){
            throw new SqxException("修改卡片信息失败");
        }

        return Result.success();
    }

    @Override
    public Result enable(Long cardId, Integer status) {
        if (!checkCardExistById(cardId)) {
            throw new SqxException("卡片信息不存在");
        }

        UpdateWrapper<GiftCard> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftCard::getCardId, cardId)
                .set(GiftCard::getStatus, status);

        if (!giftCardService.update(new GiftCard(), updateWrapper)){
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    @Override
    public Result listCard(Integer page, Integer limit, CardGroupByCategoryListBo cardGroupByCategoryListBo) {
        Page<GiftCard> pages = PageUtil.initPage(page, limit);

        return Result.success(giftCardService.listCard(pages, cardGroupByCategoryListBo.getCardCategoryId()));
    }

    @Override
    public Result delete(Long cardId) {
        return Result.success(giftCardService.removeById(cardId));
    }

    /**
     * 校验卡片信息是否存在
     * @param cardId 卡片id
     * @return
     */
    private boolean checkCardExistById(Long cardId) {
        QueryWrapper<GiftCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCard::getCardId, cardId);

        return giftCardService.exists(queryWrapper);
    }
}
