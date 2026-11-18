package com.sqx.referral.commission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.referral.commission.db.service.IReferralCommissionService;
import com.sqx.referral.commission.domain.bo.ReferralCommissionInfoBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionPageBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionSaveBo;
import com.sqx.referral.commission.domain.bo.ReferralCommissionUpdateBo;
import com.sqx.referral.commission.domain.vo.ReferralCommissionVo;
import com.sqx.referral.commission.service.ReferralCommissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 佣金信息 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@Service
public class ReferralCommissionServiceImpl implements ReferralCommissionService {

    @Resource
    private IReferralCommissionService iReferralCommissionService;
    @Resource
    private IGiftOrderService iGiftOrderService;
    @Resource
    private IUserService iUserService;

    @Override
    public void saveInfo(ReferralCommissionSaveBo referralCommissionSaveBo) {
        ReferralCommission referralCommission = new ReferralCommission();
        BeanUtils.copyProperties(referralCommissionSaveBo, referralCommission);

        iReferralCommissionService.save(referralCommission);
    }

    @Override
    public IPage<ReferralCommissionVo> pageInfo(ReferralCommissionPageBo referralCommissionPageBo) {
        Page<ReferralCommission> initPage = PageUtil.initPage(referralCommissionPageBo.getPage(), referralCommissionPageBo.getLimit());

        QueryWrapper<ReferralCommission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(referralCommissionPageBo.getUserId()), ReferralCommission::getUserId, referralCommissionPageBo.getUserId())
                .orderByDesc(ReferralCommission::getCreateTime);

        IPage<ReferralCommission> iPage = iReferralCommissionService.page(initPage, queryWrapper);
        List<ReferralCommissionVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (ReferralCommission record : iPage.getRecords()) {
                ReferralCommissionVo vo = new ReferralCommissionVo();
                BeanUtils.copyProperties(record, vo);

                GiftOrder giftOrder = iGiftOrderService.getById(record.getOrderId());
                if (Objects.nonNull(giftOrder)) {
                    vo.setGiftOrder(giftOrder);
                }

                UserEntity userEntity = iUserService.getById(giftOrder.getUserId());
                if (Objects.nonNull(userEntity)) {
                    vo.setUserEntity(userEntity);
                }

                voList.add(vo);
            }
        }

        IPage<ReferralCommissionVo> voIPage = PageUtil.copyPage(iPage, voList);
        return voIPage;
    }

    @Override
    public ReferralCommissionVo info(ReferralCommissionInfoBo referralCommissionInfoBo) {
        ReferralCommission referralCommission = iReferralCommissionService.getById(referralCommissionInfoBo.getId());
        if (Objects.isNull(referralCommission)) {
            throw new BizException("信息不存在");
        }

        ReferralCommissionVo vo = new ReferralCommissionVo();
        BeanUtils.copyProperties(referralCommission, vo);

        GiftOrder giftOrder = iGiftOrderService.getById(referralCommission.getOrderId());
        if (Objects.nonNull(giftOrder)) {
            vo.setGiftOrder(giftOrder);
        }

        UserEntity userEntity = iUserService.getById(giftOrder.getUserId());
        if (Objects.nonNull(userEntity)) {
            vo.setUserEntity(userEntity);
        }

        return vo;
    }

    @Override
    public void updateInfo(ReferralCommissionUpdateBo referralCommissionUpdateBo) {
        ReferralCommission referralCommission = new ReferralCommission();
        BeanUtils.copyProperties(referralCommissionUpdateBo, referralCommission);

        iReferralCommissionService.updateById(referralCommission);
    }

    @Override
    public void delete(ReferralCommissionInfoBo referralCommissionInfoBo) {
        iReferralCommissionService.removeById(referralCommissionInfoBo.getId());
    }
}
