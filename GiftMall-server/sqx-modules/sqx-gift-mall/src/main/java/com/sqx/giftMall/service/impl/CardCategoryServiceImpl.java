package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftCard;
import com.sqx.domain.repository.entity.giftMall.GiftCardCategory;
import com.sqx.domain.repository.service.giftMall.IGiftCardCategoryService;
import com.sqx.domain.repository.service.giftMall.IGiftCardService;
import com.sqx.giftMall.domain.bo.CardCategoryListBo;
import com.sqx.giftMall.domain.bo.CardCategorySaveBo;
import com.sqx.giftMall.domain.bo.CardCategoryUpdateBo;
import com.sqx.giftMall.service.CardCategoryService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Service
public class CardCategoryServiceImpl implements CardCategoryService {
    @Resource
    private IGiftCardCategoryService giftCardCategoryService;
    @Resource
    private IGiftCardService giftCardService;

    @Override
    public Result list(Integer page, Integer limit, CardCategoryListBo cardCategoryListBo) {
        Page<GiftCardCategory> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftCardCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(cardCategoryListBo.getCardCategoryName()),GiftCardCategory::getCardCategoryName, cardCategoryListBo.getCardCategoryName())
                .orderByAsc(GiftCardCategory::getSort);

        return Result.success(giftCardCategoryService.page(pages, queryWrapper));
    }

    @Override
    public Result info(Long cardCategoryId) {
        GiftCardCategory cardCategory = giftCardCategoryService.getById(cardCategoryId);
        if (Objects.isNull(cardCategory)) {
            throw new SqxException("卡片分类不存在");
        }

        return Result.success(cardCategory);
    }

    @Override
    public Result save(CardCategorySaveBo cardCategorySaveBo) {
        if (checkCardCategoryNameExists(cardCategorySaveBo.getCardCategoryName())) {
            throw new SqxException("卡片分类已存在");
        }

        GiftCardCategory cardCategory = new GiftCardCategory();
        cardCategory.setCardCategoryName(cardCategorySaveBo.getCardCategoryName());
        cardCategory.setSort(cardCategorySaveBo.getSort());

        if (!giftCardCategoryService.save(cardCategory)) {
            throw new SqxException("保存卡片分类失败");
        }

        return Result.success();
    }

    @Override
    public Result update(CardCategoryUpdateBo cardCategoryUpdateBo) {
        if (!checkCardCategoryExistsById(cardCategoryUpdateBo.getCardCategoryId())) {
            throw new SqxException("卡片分类不存在");
        }

        UpdateWrapper<GiftCardCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftCardCategory::getCardCategoryId, cardCategoryUpdateBo.getCardCategoryId())
                .set(StringUtils.isNotBlank(cardCategoryUpdateBo.getCardCategoryName()), GiftCardCategory::getCardCategoryName, cardCategoryUpdateBo.getCardCategoryName())
                .set(Objects.nonNull(cardCategoryUpdateBo.getSort()), GiftCardCategory::getSort, cardCategoryUpdateBo.getSort());

        if (!giftCardCategoryService.update(new GiftCardCategory(), updateWrapper)) {
            throw new SqxException("修改卡片分类失败");
        }

        return Result.success();
    }

    @Override
    public Result delete(Long cardCategoryId) {
        if (!checkCardCategoryExistsById(cardCategoryId)) {
            throw new SqxException("卡片分类不存在");
        }

        // 查询卡片分类下是否有卡片
        if (checkHasCard(cardCategoryId)) {
            throw new SqxException("该分类下有其他卡片，不能删除");
        }

        if (!giftCardCategoryService.removeById(cardCategoryId)) {
            throw new SqxException("删除卡片分类失败");
        }

        return Result.success();
    }

    @Override
    public Result listCardCategory(Integer page, Integer limit) {
        Page<GiftCardCategory> pages = PageUtil.initPage(page, limit);

        return Result.success(giftCardCategoryService.page(pages));
    }

    /**
     * 查询卡片分类下是否有卡片
     * @param cardCategoryId
     * @return
     */
    private boolean checkHasCard(Long cardCategoryId) {
        QueryWrapper<GiftCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftCard::getCardCategoryId, cardCategoryId);

        long count = giftCardService.count(queryWrapper);

        return 0 < count;
    }

    /**
     * 校验卡片分类是否存在
     * @param cardCategoryId
     * @return
     */
    private boolean checkCardCategoryExistsById(Long cardCategoryId) {
        QueryWrapper<GiftCardCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCardCategory::getCardCategoryId, cardCategoryId);

        return giftCardCategoryService.exists(queryWrapper);
    }

    /**
     * 校验卡片分类名称是否存在
     * @param cardCategoryName 卡片分类名称
     * @return
     */
    private boolean checkCardCategoryNameExists(String cardCategoryName) {
        QueryWrapper<GiftCardCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCardCategory::getCardCategoryName, cardCategoryName);

        return giftCardCategoryService.exists(queryWrapper);
    }
}
