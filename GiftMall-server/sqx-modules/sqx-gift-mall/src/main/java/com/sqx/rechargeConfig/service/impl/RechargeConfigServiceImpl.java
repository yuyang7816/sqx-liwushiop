package com.sqx.rechargeConfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.rechargeConfig.db.service.IRechargeConfigService;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigInfoBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigPageBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigSaveBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigUpdateBo;
import com.sqx.rechargeConfig.domain.entity.RechargeConfig;
import com.sqx.rechargeConfig.domain.vo.RechargeConfigVo;
import com.sqx.rechargeConfig.service.RechargeConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 充值金额配置 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 14:48:43
 */
@Service
public class RechargeConfigServiceImpl implements RechargeConfigService {

    @Resource
    private IRechargeConfigService iRechargeConfigService;

    @Override
    public void saveInfo(RechargeConfigSaveBo rechargeConfigSaveBo) {
        RechargeConfig rechargeConfig = new RechargeConfig();
        BeanUtils.copyProperties(rechargeConfigSaveBo, rechargeConfig);

        iRechargeConfigService.save(rechargeConfig);
    }

    @Override
    public IPage<RechargeConfigVo> pageInfo(RechargeConfigPageBo rechargeConfigPageBo) {
        Page<RechargeConfig> initPage = PageUtil.initPage(rechargeConfigPageBo.getPage(), rechargeConfigPageBo.getLimit());

        QueryWrapper<RechargeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(RechargeConfig::getCreateTime);

        IPage<RechargeConfig> iPage = iRechargeConfigService.page(initPage, queryWrapper);
        List<RechargeConfigVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (RechargeConfig record : iPage.getRecords()) {
                RechargeConfigVo vo = new RechargeConfigVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        IPage<RechargeConfigVo> voIPage = PageUtil.copyPage(iPage, voList);
        return voIPage;
    }

    @Override
    public RechargeConfigVo info(RechargeConfigInfoBo rechargeConfigInfoBo) {
        RechargeConfig rechargeConfig = iRechargeConfigService.getById(rechargeConfigInfoBo.getId());
        if (Objects.isNull(rechargeConfig)) {
            throw new BizException("信息不存在");
        }

        RechargeConfigVo vo = new RechargeConfigVo();
        BeanUtils.copyProperties(rechargeConfig, vo);
        return vo;
    }

    @Override
    public void updateInfo(RechargeConfigUpdateBo rechargeConfigUpdateBo) {
        RechargeConfig rechargeConfig = new RechargeConfig();
        BeanUtils.copyProperties(rechargeConfigUpdateBo, rechargeConfig);

        iRechargeConfigService.updateById(rechargeConfig);
    }

    @Override
    public void delete(RechargeConfigInfoBo rechargeConfigInfoBo) {
        iRechargeConfigService.removeById(rechargeConfigInfoBo.getId());
    }

    @Override
    public List<RechargeConfig> list() {
        List<RechargeConfig> list = iRechargeConfigService.list();
        list.sort(Comparator.comparing(RechargeConfig::getAmount));
        return list;
    }
}
