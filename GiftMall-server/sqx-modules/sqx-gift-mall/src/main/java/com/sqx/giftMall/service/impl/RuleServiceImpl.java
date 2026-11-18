package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftProductRule;
import com.sqx.domain.repository.entity.giftMall.GiftProductRuleValue;
import com.sqx.domain.repository.service.giftMall.IGiftProductRuleService;
import com.sqx.domain.repository.service.giftMall.IGiftProductRuleValueService;
import com.sqx.giftMall.domain.bo.RuleListBo;
import com.sqx.giftMall.domain.bo.RuleSaveBo;
import com.sqx.giftMall.domain.bo.RuleUpdateBo;
import com.sqx.giftMall.domain.vo.GiftProductRuleVo;
import com.sqx.giftMall.service.RuleService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Service
public class RuleServiceImpl implements RuleService {
    @Resource
    private IGiftProductRuleService giftProductRuleService;
    @Resource
    private IGiftProductRuleValueService giftProductRuleValueService;

    @Override
    public Result page(Integer page, Integer limit, RuleListBo ruleListBo) {
        Page<GiftProductRule> initPage = PageUtil.initPage(page, limit);
        LambdaQueryWrapper<GiftProductRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Objects.nonNull(ruleListBo.getRuleId()), GiftProductRule::getId, ruleListBo.getRuleId())
                .like(StringUtils.isNotBlank(ruleListBo.getRuleName()), GiftProductRule::getRuleName, ruleListBo.getRuleName())
                .orderByDesc(GiftProductRule::getCreateTime);

        IPage<GiftProductRule> iPage = giftProductRuleService.page(initPage, queryWrapper);

        List<GiftProductRuleVo> voList = CollectionUtils.isNotEmpty(iPage.getRecords()) ? iPage.getRecords().stream().map(item -> {
            GiftProductRuleVo vo = new GiftProductRuleVo();
            BeanUtils.copyProperties(item, vo);

            List<GiftProductRuleValue> giftProductRuleValueList = getGiftProductRuleValueList(item.getId());
            vo.setGiftProductRuleValueList(giftProductRuleValueList);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        IPage<GiftProductRuleVo> voIPage = PageUtil.copyPage(iPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result info(Long id) {
        GiftProductRule giftProductRule = giftProductRuleService.getById(id);
        if (Objects.isNull(giftProductRule)) {
            throw new SqxException("商品规格信息不存在");
        }

        GiftProductRuleVo vo = new GiftProductRuleVo();
        BeanUtils.copyProperties(giftProductRule, vo);
        vo.setGiftProductRuleValueList(getGiftProductRuleValueList(id));

        return Result.success(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result save(RuleSaveBo ruleSaveBo) {
        if (checkRuleNameExist(ruleSaveBo.getRuleName())) {
            throw new SqxException("规格名称已存在");
        }

        GiftProductRule giftProductRule = new GiftProductRule();
        giftProductRule.setRuleName(ruleSaveBo.getRuleName());
        giftProductRuleService.save(giftProductRule);

        if (CollectionUtils.isNotEmpty(ruleSaveBo.getGiftProductRuleValueList())) {
            List<GiftProductRuleValue> giftProductRuleValueList = ruleSaveBo.getGiftProductRuleValueList();
            giftProductRuleValueList.forEach(item -> item.setRuleId(giftProductRule.getId()));

            giftProductRuleValueService.saveBatch(giftProductRuleValueList);
        }

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result update(RuleUpdateBo ruleUpdateBo) {
        if (!checkRuleExistById(ruleUpdateBo.getId())) {
            throw new SqxException("规格信息不存在");
        }

        if (checkRuleNameExistExcludeSelf(ruleUpdateBo.getId(), ruleUpdateBo.getRuleName())) {
            throw new SqxException("规格名称已存在");
        }

        // 修改规格信息
        if (StringUtils.isNotBlank(ruleUpdateBo.getRuleName())) {
            UpdateWrapper<GiftProductRule> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .eq(GiftProductRule::getId, ruleUpdateBo.getId())
                    .set(GiftProductRule::getRuleName, ruleUpdateBo.getRuleName());
            giftProductRuleService.update(new GiftProductRule(), updateWrapper);
        }

        // 修改规格值
        RuleServiceImpl currentProxy = (RuleServiceImpl) AopContext.currentProxy();
        currentProxy.updateRuleValue(ruleUpdateBo.getId(), ruleUpdateBo.getGiftProductRuleValueList());

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Long id) {
        if (!checkRuleExistById(id)) {
            throw new SqxException("规格信息不存在");
        }

        // 删除规格基本信息
        giftProductRuleService.removeById(id);

        // 删除规格下的规格值
        QueryWrapper<GiftProductRuleValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductRuleValue::getRuleId, id);
        giftProductRuleValueService.remove(queryWrapper);

        return Result.success();
    }

    @Override
    public Result list(RuleListBo ruleListBo) {
        List<GiftProductRule> ruleList = giftProductRuleService.list();

        List<GiftProductRuleVo> voList = CollectionUtils.isNotEmpty(ruleList) ? ruleList.stream().map(item -> {
            GiftProductRuleVo vo = new GiftProductRuleVo();
            BeanUtils.copyProperties(item, vo);

            List<GiftProductRuleValue> giftProductRuleValueList = getGiftProductRuleValueList(item.getId());
            vo.setGiftProductRuleValueList(giftProductRuleValueList);

            return vo;
        }).collect(Collectors.toList()) : Collections.emptyList();

        return Result.success(voList);
    }

    /**
     * 修改规格值
     * @param ruleId 规格id
     * @param giftProductRuleValueList 规格值列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRuleValue(Long ruleId, List<GiftProductRuleValue> giftProductRuleValueList) {
        // 删除旧数据
        QueryWrapper<GiftProductRuleValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductRuleValue::getRuleId, ruleId);
        giftProductRuleValueService.remove(queryWrapper);

        // 保存新数据
        giftProductRuleValueList.forEach(item -> item.setRuleId(ruleId));
        giftProductRuleValueService.saveBatch(giftProductRuleValueList);
    }

    /**
     * 校验规格名称是否存在（排除自己）
     * @param selfRuleId 规格id
     * @param ruleName 规格名称
     * @return
     */
    private boolean checkRuleNameExistExcludeSelf(Long selfRuleId, String ruleName) {
        QueryWrapper<GiftProductRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftProductRule::getRuleName, ruleName)
                .ne(GiftProductRule::getId, selfRuleId);

        return giftProductRuleService.exists(queryWrapper);
    }

    /**
     * 校验规格是否存在
     * @param ruleId 规格id
     * @return
     */
    private boolean checkRuleExistById(Long ruleId) {
        QueryWrapper<GiftProductRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductRule::getId, ruleId);

        return giftProductRuleService.exists(queryWrapper);
    }

    /**
     * 校验规格名称是否存在
     * @param ruleName 规格名称
     * @return
     */
    private boolean checkRuleNameExist(String ruleName) {
        QueryWrapper<GiftProductRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductRule::getRuleName, ruleName);

        return giftProductRuleService.exists(queryWrapper);
    }

    /**
     * 根据ruleid获取value列表
     * @param ruleId 规格id
     * @return
     */
    private List<GiftProductRuleValue> getGiftProductRuleValueList(Long ruleId) {
        QueryWrapper<GiftProductRuleValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductRuleValue::getRuleId, ruleId);

        return giftProductRuleValueService.list(queryWrapper);
    }
}
