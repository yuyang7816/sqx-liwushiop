package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftBlessing;
import com.sqx.domain.repository.service.giftMall.IGiftBlessingService;
import com.sqx.giftMall.domain.bo.BlessingListBo;
import com.sqx.giftMall.domain.bo.BlessingSaveBo;
import com.sqx.giftMall.domain.bo.BlessingUpdateBo;
import com.sqx.giftMall.service.BlessingService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Service
public class BlessingServiceImpl implements BlessingService {
    @Resource
    private IGiftBlessingService giftBlessingService;

    @Override
    public Result save(BlessingSaveBo blessingSaveBo) {
        if (checkBlessingExistsByContent(blessingSaveBo.getContent().trim())) {
            throw new SqxException("祝福语已存在");
        }

        GiftBlessing giftBlessing = new GiftBlessing();
        giftBlessing.setContent(blessingSaveBo.getContent().trim());

        if (!giftBlessingService.save(giftBlessing)){
            throw new SqxException("保存祝福语失败");
        }

        return Result.success();
    }

    @Override
    public Result list(Integer page, Integer limit, BlessingListBo blessingListBo) {
        Page<GiftBlessing> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftBlessing> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(blessingListBo.getContent()), GiftBlessing::getContent, blessingListBo.getContent())
                .orderByDesc(GiftBlessing::getCreateTime);

        return Result.success(giftBlessingService.page(pages, queryWrapper));
    }

    @Override
    public Result info(Long blessingId) {
        GiftBlessing giftBlessing = giftBlessingService.getById(blessingId);
        if (Objects.isNull(giftBlessing)) {
            throw new SqxException("祝福语不存在");
        }

        return Result.success(giftBlessing);
    }

    @Override
    public Result update(BlessingUpdateBo blessingUpdateBo) {
        if (!checkBlessingExistsById(blessingUpdateBo.getId())) {
            throw new SqxException("祝福语不存在");
        }

        UpdateWrapper<GiftBlessing> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftBlessing::getId,blessingUpdateBo.getId())
                .set(GiftBlessing::getContent, blessingUpdateBo.getContent());

        if (!giftBlessingService.update(new GiftBlessing(), updateWrapper)) {
            throw new SqxException("修改祝福语失败");
        }

        return Result.success();
    }

    @Override
    public Result delete(Long blessingId) {
        if (!checkBlessingExistsById(blessingId)) {
            throw new SqxException("祝福语不存在");
        }

        if (!giftBlessingService.removeById(blessingId)) {
            throw new SqxException("删除祝福语失败");
        }

        return Result.success();
    }

    @Override
    public Result random() {
        List<GiftBlessing> blessingList = giftBlessingService.list();
        if (CollectionUtils.isEmpty(blessingList)) {
            return Result.failed("当前没有祝福语");
        }
        int randomIndex = (int) (Math.random() * blessingList.size());

        return Result.success(blessingList.get(randomIndex));
    }

    /**
     * 校验祝福语id是否存在
     * @param blessingId 祝福语id
     * @return
     */
    private boolean checkBlessingExistsById(Long blessingId) {
        QueryWrapper<GiftBlessing> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftBlessing::getId, blessingId);

        return giftBlessingService.exists(queryWrapper);
    }

    /**
     * 校验祝福语是否存在
     * @param content 祝福语
     * @return
     */
    private boolean checkBlessingExistsByContent(String content) {
        QueryWrapper<GiftBlessing> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftBlessing::getContent, content);

        return giftBlessingService.exists(queryWrapper);
    }
}
