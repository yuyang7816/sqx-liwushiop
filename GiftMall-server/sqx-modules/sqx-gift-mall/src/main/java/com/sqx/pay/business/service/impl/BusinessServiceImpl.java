package com.sqx.pay.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.PayDetails;
import com.sqx.domain.repository.mapper.giftMall.PayDetailsMapper;
import com.sqx.domain.repository.service.giftMall.IGiftOrderService;
import com.sqx.giftMall.enums.OrderStatus;
import com.sqx.pay.business.service.BusinessService;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.enums.BusinessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/21
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private PayDetailsMapper payDetailsMapper;
    @Resource
    private IGiftOrderService iGiftOrderService;

    @Override
    public void process(String orderNo, String platform, BusinessParam param) {
        log.info("支付业务处理");

        if (Objects.equals(param.getBusinessType(), BusinessType.RECHARGE.getCode())) {
            handlerRecharge(orderNo, platform, param);
        } else if (Objects.equals(param.getBusinessType(), BusinessType.VIP.getCode())) {

        } else if (Objects.equals(param.getBusinessType(), BusinessType.ORDER.getCode())) {
            handlerOrder(orderNo, platform, param);
        } else {
            throw new BizException("不支持的业务类型");
        }
    }

    /**
     * 订单业务
     *
     * @param orderNo  订单号
     * @param platform 平台
     * @param param    参数
     */
    private void handlerOrder(String orderNo, String platform, BusinessParam param) {
        GiftOrder giftOrder = iGiftOrderService.getByOrderNo(orderNo);
        if (Objects.isNull(giftOrder)) {
            throw new SqxException("订单信息不存在");
        }

        if (!Objects.equals(giftOrder.getStatus(), OrderStatus.WAIT_FOR_PAY.code())) {
            throw new SqxException("订单不能支付");
        }

        // 是否已经保存过了
        PayDetails savePayDetails = payDetailsMapper.selectOne(new LambdaQueryWrapper<PayDetails>()
                .eq(PayDetails::getOrderNo, orderNo)
        );
        if (Objects.nonNull(savePayDetails)) {
            return;
        }

        // 支付详情
        PayDetails payDetails = new PayDetails();
        payDetails.setUserId(param.getUserId());
        payDetails.setPlatform(Integer.valueOf(platform));
        payDetails.setOrderNo(orderNo);
        payDetails.setAmount(param.getAmount());
        payDetails.setTradeType(param.getTradeType());
        payDetails.setAmount(param.getAmount());
        payDetails.setBusinessType(param.getBusinessType());
        payDetails.setState(0);
        payDetailsMapper.insert(payDetails);
    }

    /**
     * @param platform
     * @param param
     */
    private void handlerRecharge(String orderNo, String platform, BusinessParam param) {
        PayDetails payDetails = new PayDetails();
        payDetails.setUserId(param.getUserId());
        payDetails.setPlatform(Integer.valueOf(platform));
        payDetails.setOrderNo(orderNo);
        payDetails.setAmount(param.getAmount());
        payDetails.setTradeType(param.getTradeType());
        payDetails.setAmount(param.getAmount());
        payDetails.setBusinessType(param.getBusinessType());
        payDetails.setState(0);

        payDetailsMapper.insert(payDetails);
    }
}
