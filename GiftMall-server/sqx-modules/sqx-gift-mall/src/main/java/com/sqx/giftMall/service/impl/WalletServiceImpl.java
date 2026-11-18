package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.sqx.domain.repository.entity.giftMall.UserMoneyDetails;
import com.sqx.domain.repository.service.giftMall.IUserMoneyDetailsService;
import com.sqx.domain.repository.service.giftMall.IUserMoneyService;
import com.sqx.giftMall.domain.bo.UserMoneyDetailsBo;
import com.sqx.giftMall.service.WalletService;
import com.sqx.giftMall.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/23
 */
@Service
public class WalletServiceImpl implements WalletService {
    @Resource
    private IUserMoneyService userMoneyService;
    @Resource
    private IUserMoneyDetailsService userMoneyDetailsService;

    @Override
    public Result info(Long userId) {
        UserMoney userMoney = userMoneyService.getOne(new QueryWrapper<UserMoney>()
                .lambda()
                .eq(UserMoney::getUserId, userId)
        );

        if (Objects.isNull(userMoney)) {
            throw new SqxException("获取钱包详情失败");
        }

        return Result.success(userMoney);
    }

    @Override
    public Result detail(Integer page, Integer limit, UserMoneyDetailsBo userMoneyDetailsBo) {
        Page<UserMoneyDetails> initPage = PageUtil.initPage(page, limit);
        QueryWrapper<UserMoneyDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(UserMoneyDetails::getUserId, userMoneyDetailsBo.getUserId())
                .eq(Objects.nonNull(userMoneyDetailsBo.getClassify()), UserMoneyDetails::getClassify, userMoneyDetailsBo.getClassify())
                .eq(Objects.nonNull(userMoneyDetailsBo.getType()), UserMoneyDetails::getType, userMoneyDetailsBo.getType())
                .eq(Objects.nonNull(userMoneyDetailsBo.getState()), UserMoneyDetails::getState, userMoneyDetailsBo.getState())
                .orderByDesc(UserMoneyDetails::getCreateTime);

        Page<UserMoneyDetails> detailsPage = userMoneyDetailsService.page(initPage, queryWrapper);

        return Result.success(detailsPage);
    }
}
