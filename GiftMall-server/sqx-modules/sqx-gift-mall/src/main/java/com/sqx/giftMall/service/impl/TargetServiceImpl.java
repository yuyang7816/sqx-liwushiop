package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftTarget;
import com.sqx.domain.repository.service.giftMall.IGiftTargetService;
import com.sqx.giftMall.domain.bo.TargetSaveBo;
import com.sqx.giftMall.domain.bo.TargetUpdateBo;
import com.sqx.giftMall.service.TargetService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Service
public class TargetServiceImpl implements TargetService {
    @Resource
    private IGiftTargetService giftTargetService;

    @Override
    public Result list(Integer page, Integer limit, String name) {
        Page<GiftTarget> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftTarget> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(name), GiftTarget::getName, name);

        return Result.success(giftTargetService.page(pages, queryWrapper));
    }

    @Override
    public Result save(TargetSaveBo targetSaveBo) {
        if (checkTargetNameExists(targetSaveBo.getName())) {
            throw new SqxException("送礼对象已存在");
        }

        GiftTarget giftTarget = new GiftTarget();
        giftTarget.setName(targetSaveBo.getName());

        if (!giftTargetService.save(giftTarget)) {
            throw new SqxException("保存送礼对象名称失败");
        }

        return Result.success();
    }

    @Override
    public Result update(TargetUpdateBo targetUpdateBo) {
        if (!checkTargetExistsById(targetUpdateBo.getId())) {
            throw new SqxException("送礼对象不存在");
        }

        UpdateWrapper<GiftTarget> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftTarget::getId, targetUpdateBo.getId())
                .set(GiftTarget::getName, targetUpdateBo.getName());

        if (!giftTargetService.update(new GiftTarget(), updateWrapper)) {
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if (!checkTargetExistsById(id)) {
            throw new SqxException("送礼对象不存在");
        }

        if (!giftTargetService.removeById(id)) {
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    /**
     * 校验送礼对象是否存在
     * @param id 送礼对象id
     * @return
     */
    private boolean checkTargetExistsById(Long id) {
        QueryWrapper<GiftTarget> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftTarget::getId, id);

        return giftTargetService.exists(queryWrapper);
    }

    /**
     * 校验送礼对象名称是否存在
     * @param name 送礼对象名称
     * @return
     */
    private boolean checkTargetNameExists(String name) {
        QueryWrapper<GiftTarget> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftTarget::getName, name);

        return giftTargetService.exists(queryWrapper);
    }
}
