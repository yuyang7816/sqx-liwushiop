package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.core.validator.group.QcloudGroup;
import com.sqx.domain.repository.entity.giftMall.GiftScene;
import com.sqx.domain.repository.service.giftMall.IGiftSceneService;
import com.sqx.giftMall.domain.bo.SceneSaveBo;
import com.sqx.giftMall.domain.bo.SceneUpdateBo;
import com.sqx.giftMall.service.SceneService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Service
public class SceneServiceImpl implements SceneService {
    @Resource
    private IGiftSceneService giftSceneService;

    @Override
    public Result list(Integer page, Integer limit, String name) {
        Page<GiftScene> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftScene> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(name), GiftScene::getName, name);

        return Result.success(giftSceneService.page(pages, queryWrapper));
    }

    @Override
    public Result save(SceneSaveBo sceneSaveBo) {
        if (checkSceneNameExists(sceneSaveBo.getName())) {
            throw new SqxException("送礼场景名称已存在");
        }

        GiftScene giftScene = new GiftScene();
        giftScene.setName(sceneSaveBo.getName());

        if (!giftSceneService.save(giftScene)) {
            throw new SqxException("保存送礼场景名称失败");
        }

        return Result.success();
    }

    @Override
    public Result update(SceneUpdateBo sceneUpdateBo) {
        if (!checkSceneExistsById(sceneUpdateBo.getId())) {
            throw new SqxException("送礼场景不存在");
        }

        UpdateWrapper<GiftScene> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftScene::getId, sceneUpdateBo.getId())
                .set(GiftScene::getName, sceneUpdateBo.getName());

        if (!giftSceneService.update(new GiftScene(), updateWrapper)) {
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if (!checkSceneExistsById(id)) {
            throw new SqxException("送礼场景不存在");
        }

        if (!giftSceneService.removeById(id)) {
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    /**
     * 校验送礼场景是否存在
     * @param id 送礼场景id
     * @return
     */
    private boolean checkSceneExistsById(Long id) {
        QueryWrapper<GiftScene> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftScene::getId, id);

        return giftSceneService.exists(queryWrapper);
    }

    /**
     * 校验送礼场景名称是否存在
     * @param name 送礼场景名称
     * @return
     */
    private boolean checkSceneNameExists(String name) {
        QueryWrapper<GiftScene> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftScene::getName, name);

        return giftSceneService.exists(queryWrapper);
    }
}
