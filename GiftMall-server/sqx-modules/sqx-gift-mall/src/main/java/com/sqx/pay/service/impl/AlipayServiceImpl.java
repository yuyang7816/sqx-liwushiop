package com.sqx.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.common.pay.platform.ali.AliPay;
import com.sqx.common.pay.platform.ali.domain.AlipayConfigParam;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.giftMall.utils.GenUtil;
import com.sqx.pay.business.service.BusinessService;
import com.sqx.pay.business.service.CallbackService;
import com.sqx.pay.domain.AliPayParam;
import com.sqx.pay.domain.BusinessParam;
import com.sqx.pay.enums.AliMode;
import com.sqx.pay.enums.AliSandbox;
import com.sqx.pay.enums.BusinessType;
import com.sqx.pay.enums.PlatformEnum;
import com.sqx.pay.service.AlipayService;
import com.sqx.pay.utils.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/4/21
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayServiceImpl.class);

    public static final String NOTIFY_URL = "/sqx_fast/app/alipay/notifyUrl";
    public static final String CERT_NOTIFY_URL = "/sqx_fast/app/alipay/certNotifyUrl";

    public static final String RETURN_URL = "/sqx_fast/app/alipay/returnUrl";
    public static final String CERT_RETURN_URL = "/sqx_fast/app/alipay/certReturnUrl";

    @Autowired
    private CallbackService callbackService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private CommonInfoService commonInfoService;

    @Override
    public Result<String> wapPay(BusinessParam param) {
        String orderNo;
        if (Objects.equals(BusinessType.ORDER.getCode(), param.getBusinessType())) {
            Map<String, Object> extensions = param.getExtensions();
            orderNo = (String) extensions.getOrDefault("orderNo", GenUtil.genOrderNo());
        } else {
            orderNo = GenUtil.genOrderNo();
        }

        // 处理业务
        businessService.process(orderNo, PlatformEnum.ALI.getCode(), param);

        // 请求支付
        AlipayConfigParam alipayConfigParam = buildAlipayConfigParam();

        AliPayParam aliPayParam = param.getAliPayParam();
        String domain = commonInfoService.getCommonValue(Integer.valueOf(aliPayParam.getClientId()));

        String notifyUrl = domain + NOTIFY_URL;
        String returnUrl = domain + RETURN_URL;

        String mode = commonInfoService.getCommonValue(422);
        if (Objects.equals(mode, AliMode.CERT.getCode())) {
            notifyUrl = domain + CERT_NOTIFY_URL;
            returnUrl = domain + CERT_RETURN_URL;
        }

        String subject = "订单标题";

        try {
            String res = AliPay.wapPay(orderNo, String.valueOf(param.getAmount()), subject, returnUrl, notifyUrl, alipayConfigParam);

            return Result.success(res);
        } catch (AlipayApiException e) {
            throw new SqxException(e.getMessage());
        }
    }

    @Override
    public Result<String> appPay(BusinessParam param) {
        String orderNo;
        if (Objects.equals(BusinessType.ORDER.getCode(), param.getBusinessType())) {
            Map<String, Object> extensions = param.getExtensions();
            orderNo = (String) extensions.getOrDefault("orderNo", GenUtil.genOrderNo());
        } else {
            orderNo = GenUtil.genOrderNo();
        }

        // 处理业务
        businessService.process(orderNo, PlatformEnum.ALI.getCode(), param);

        // 请求支付
        AlipayConfigParam alipayConfigParam = buildAlipayConfigParam();

        AliPayParam aliPayParam = param.getAliPayParam();
        String domain = commonInfoService.getCommonValue(Integer.valueOf(aliPayParam.getClientId()));
        String notifyUrl = domain + NOTIFY_URL;
        String returnUrl = domain + RETURN_URL;

        String mode = commonInfoService.getCommonValue(423);
        if (Objects.equals(mode, AliMode.CERT.getCode())) {
            notifyUrl = domain + CERT_NOTIFY_URL;
            returnUrl = domain + CERT_RETURN_URL;
        }

        String subject = "订单标题";

        try {
            String res = AliPay.appPay(orderNo, String.valueOf(param.getAmount()), subject, returnUrl, notifyUrl, alipayConfigParam);

            return Result.success(res);
        } catch (AlipayApiException e) {
            throw new SqxException(e.getMessage());
        }
    }

    @Override
    public Result<String> pagePay(BusinessParam param) {
        String orderNo;
        if (Objects.equals(BusinessType.ORDER.getCode(), param.getBusinessType())) {
            Map<String, Object> extensions = param.getExtensions();
            orderNo = (String) extensions.getOrDefault("orderNo", GenUtil.genOrderNo());
        } else {
            orderNo = GenUtil.genOrderNo();
        }

        // 处理业务
        businessService.process(orderNo, PlatformEnum.ALI.getCode(), param);

        // 请求支付
        AlipayConfigParam alipayConfigParam = buildAlipayConfigParam();

        AliPayParam aliPayParam = param.getAliPayParam();
        String domain = commonInfoService.getCommonValue(Integer.valueOf(aliPayParam.getClientId()));
        String notifyUrl = domain + NOTIFY_URL;
        String returnUrl = domain + RETURN_URL;

        String mode = commonInfoService.getCommonValue(423);
        if (Objects.equals(mode, AliMode.CERT.getCode())) {
            notifyUrl = domain + CERT_NOTIFY_URL;
            returnUrl = domain + CERT_RETURN_URL;
        }

        String subject = "订单标题";

        try {
            String res = AliPay.pagePay(orderNo, String.valueOf(param.getAmount()), subject, returnUrl, notifyUrl, aliPayParam.getQrPayMode(), aliPayParam.getQrcodeWidth(), alipayConfigParam);

            return Result.success(res);
        } catch (AlipayApiException e) {
            throw new SqxException(e.getMessage());
        }
    }

    @Override
    public String notifyUrl(HttpServletRequest request) {
        LOGGER.info("支付宝普通公钥模式同步通知");

        Map<String, String> params = PayUtil.requestToMap(request);
        params.forEach((key, value) -> LOGGER.info("{} = {}", key, value));

        String publicKey = commonInfoService.getCommonValue(417);
        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, publicKey, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
            if (!verifyResult) {
                LOGGER.error("支付宝回调验证失败");
                return "failed";
            }

            // 支付成功业务
            callbackService.successProcess(params.get("out_trade_no"), params.get("trade_no"));

            return "success";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    public String certNotifyUrl(HttpServletRequest request) {
        LOGGER.info("支付宝证书模式同步通知");

        Map<String, String> params = PayUtil.requestToMap(request);
        params.forEach((key, value) -> LOGGER.info("{} = {}", key, value));

        String publicCertPath = commonInfoService.getCommonValue(419);
        try {
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(params, publicCertPath, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
            if (!verifyResult) {
                LOGGER.error("支付宝回调验证失败");
                return "failed";
            }

            // 支付成功业务
            callbackService.successProcess(params.get("out_trade_no"), params.get("trade_no"));

            return "success";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    public String returnUrl(HttpServletRequest request) {
        LOGGER.info("支付宝普通公钥模式异步通知");

        Map<String, String> params = PayUtil.requestToMap(request);
        params.forEach((key, value) -> LOGGER.info("{} = {}", key, value));

        String publicKey = commonInfoService.getCommonValue(420);
        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, publicKey, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
            if (!verifyResult) {
                LOGGER.error("支付宝回调验证失败");
                return "failed";
            }

            // 支付成功业务
            callbackService.successProcess(params.get("out_trade_no"), params.get("trade_no"));

            return "success";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    public String certReturnUrl(HttpServletRequest request) {
        LOGGER.info("支付宝证书模式异步通知");

        Map<String, String> params = PayUtil.requestToMap(request);
        params.forEach((key, value) -> LOGGER.info("{} = {}", key, value));

        String publicCertPath = commonInfoService.getCommonValue(419);
        try {
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(params, publicCertPath, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
            if (!verifyResult) {
                LOGGER.error("支付宝回调验证失败");
                return "failed";
            }

            // 支付成功业务
            callbackService.successProcess(params.get("out_trade_no"), params.get("trade_no"));

            return "success";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    /**
     * 支付宝支付配置
     */
    private AlipayConfigParam buildAlipayConfigParam() {
        AlipayConfigParam alipayConfigParam = new AlipayConfigParam();
        String appId = commonInfoService.getCommonValue(416);
        alipayConfigParam.setAppId(appId);
        String privateKey = commonInfoService.getCommonValue(418);
        alipayConfigParam.setPrivateKey(privateKey);

        // 是否为沙箱模式
        String sandbox = commonInfoService.getCommonValue(423);
        if (Objects.equals(sandbox, AliSandbox.YES.getCode())) {
            alipayConfigParam.setSandbox(Boolean.TRUE);
        }

        // 是否开启证书模式
        String aliMode = commonInfoService.getCommonValue(422);
        if (Objects.equals(AliMode.PUBLIC_KEY.getCode(), aliMode)) {
            String publicKey = commonInfoService.getCommonValue(417);
            alipayConfigParam.setAlipayPublicKey(publicKey);

            alipayConfigParam.setCertMode(Boolean.FALSE);
        } else if (Objects.equals(AliMode.CERT.getCode(), aliMode)) {
            String appCertPath = commonInfoService.getCommonValue(419);
            alipayConfigParam.setAppCertPath(appCertPath);

            String alipayPublicCertPath = commonInfoService.getCommonValue(420);
            alipayConfigParam.setAlipayPublicCertPath(alipayPublicCertPath);

            String rootCertPath = commonInfoService.getCommonValue(421);
            alipayConfigParam.setRootCertPath(rootCertPath);

            alipayConfigParam.setCertMode(Boolean.TRUE);
        } else {
            LOGGER.error("支付宝支付模式配置错误, 当前配置: {}", aliMode);
            throw new SqxException("支付失败");
        }

        return alipayConfigParam;
    }
}
