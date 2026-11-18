package com.sqx.pay.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.pay.platform.wechat.WechatPay;
import com.sqx.common.pay.platform.wechat.domain.WxPayConfigParam;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.giftMall.utils.GenUtil;
import com.sqx.pay.business.service.BusinessService;
import com.sqx.pay.business.service.CallbackService;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.domain.WxPayParam;
import com.sqx.pay.enums.BusinessType;
import com.sqx.pay.enums.PlatformEnum;
import com.sqx.pay.enums.WxTradeType;
import com.sqx.pay.service.WxPayService;
import com.sqx.pay.utils.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    /**
     * 微信支付成功回调地址
     */
    public static final String NOTIFY_URL = "/sqx_fast/app/wxpay/notifyUrl";

    @Autowired
    private BusinessService businessService;
    @Autowired
    private CommonInfoService commonInfoService;
    @Autowired
    private CallbackService callbackService;

    @Override
    public <T> Result<T> pay(BusinessParam param) {
        String orderNo;
        if (Objects.equals(BusinessType.ORDER.getCode(), param.getBusinessType())) {
            Map<String, Object> extensions = param.getExtensions();
            orderNo = (String) extensions.getOrDefault("orderNo", GenUtil.genOrderNo());
        } else {
            orderNo = GenUtil.genOrderNo();
        }

        String subject = "订单标题";

        // 处理业务
        businessService.process(orderNo, PlatformEnum.WX.getCode(), param);

        WxPayParam wxPayParam = param.getWxPayParam();
        WxPayConfigParam wxPayConfigParam = buildWxPayConfigParam(wxPayParam);
        T res;
        try {
            res = WechatPay.pay(orderNo, String.valueOf(param.getAmount()), subject, wxPayConfigParam);
        } catch (WxPayException e) {
            e.printStackTrace();
            throw new BizException("交易失败");
        }

        return Result.success(res);
    }

    @Override
    public String notifyUrl(HttpServletRequest request) {
        String xml = PayUtil.requestToXml(request);
        String tradeType = getTradeTypeByXml(xml);

        try {
            com.github.binarywang.wxpay.service.WxPayService wxPayService = getWxPayService(tradeType);
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xml);

            callbackService.successProcess(result.getOutTradeNo(), result.getTransactionId());
        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因: [{}]", e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }

        return WxPayNotifyResponse.success("处理成功");
    }

    private String getTradeTypeByXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("trade_type");
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent().trim();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("解析 XML 失败", e);
        }
    }

    private com.github.binarywang.wxpay.service.WxPayService getWxPayService(String tradeType) throws WxPayException {
        String appId = getWxPayAppId(tradeType);
        String mchId = commonInfoService.getCommonValue(425);
        String mchKey = commonInfoService.getCommonValue(426);

        return WechatPay.getWxPayService(appId, mchId, mchKey);
    }

    private WxPayConfigParam buildWxPayConfigParam(WxPayParam wxPayParam) {
        WxPayConfigParam wxPayConfigParam = new WxPayConfigParam();

        String appId = getWxPayAppId(wxPayParam.getTradeType());
        wxPayConfigParam.setAppId(appId);

        String mchId = commonInfoService.getCommonValue(425);
        wxPayConfigParam.setMchId(mchId);

        String mchKey = commonInfoService.getCommonValue(426);
        wxPayConfigParam.setMchKey(mchKey);

        wxPayConfigParam.setTradeType(wxPayParam.getTradeType());

        String domain = commonInfoService.getCommonValue(Integer.valueOf(wxPayParam.getClientId()));
        wxPayConfigParam.setNotifyUrl(domain + NOTIFY_URL);

        wxPayConfigParam.setOpenId(wxPayParam.getOpenId());

        return wxPayConfigParam;
    }

    /**
     * 获取微信支付appid
     * @param tradeType 交易类型 JSAPI-公众号支付 NATIVE-原生扫码支付 APP-app支付
     */
    private String getWxPayAppId(String tradeType) {
        String appId;
        if (Objects.equals(tradeType, WxTradeType.JSAPI.getCode())) {
            appId = commonInfoService.getCommonValue(430);
        } else if (Objects.equals(tradeType, WxTradeType.NATIVE.getCode())) {
            appId = commonInfoService.getCommonValue(429);
        } else if (Objects.equals(tradeType, WxTradeType.APP.getCode())) {
            appId = commonInfoService.getCommonValue(428);
        } else {
            log.error("不支持的交易类型: {}", tradeType);
            throw new BizException("不支持的交易类型");
        }

        return appId;
    }
}
