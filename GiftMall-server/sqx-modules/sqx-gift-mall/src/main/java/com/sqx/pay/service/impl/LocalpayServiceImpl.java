package com.sqx.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.giftMall.UserMoney;
import com.sqx.domain.repository.mapper.app.UserMoneyDao;
import com.sqx.giftMall.utils.GenUtil;
import com.sqx.pay.business.service.BusinessService;
import com.sqx.pay.business.service.CallbackService;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.enums.BusinessType;
import com.sqx.pay.enums.PlatformEnum;
import com.sqx.pay.service.LocalpayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/8/5
 */
@Service
public class LocalpayServiceImpl implements LocalpayService {

    @Resource
    private BusinessService businessService;
    @Resource
    private CallbackService callbackService;
    @Resource
    private UserMoneyDao userMoneyDao;

    @Override
    public Result<String> pay(BusinessParam param) {
        String orderNo;
        if (Objects.equals(BusinessType.ORDER.getCode(), param.getBusinessType())) {
            Map<String, Object> extensions = param.getExtensions();
            orderNo = (String) extensions.getOrDefault("orderNo", GenUtil.genOrderNo());
        } else {
            orderNo = GenUtil.genOrderNo();
        }

        // 余额支付需要记录钱包详情
        // 判断余额是否够
        UserMoney userMoney = userMoneyDao.selectOne(new LambdaQueryWrapper<UserMoney>().eq(UserMoney::getUserId, param.getUserId()));
        if (Objects.isNull(userMoney)) {
            throw new BizException("用户钱包不存在");
        }

        if (userMoney.getMoney().compareTo(param.getAmount()) < 0) {
            throw new BizException("余额不足");
        }

        userMoneyDao.decr(param.getUserId(), param.getAmount());

        // 处理业务
        businessService.process(orderNo, PlatformEnum.LOCAL.getCode(), param);
        callbackService.successProcess(orderNo, orderNo);
        return Result.success();
    }

    @Override
    public String refund(String orderNo) {
        callbackService.refund(orderNo);
        return "";
    }
}
