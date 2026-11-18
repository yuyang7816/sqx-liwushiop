package com.sqx.giftMall.enums;

import com.sqx.common.core.exception.SqxException;
import com.sqx.giftMall.constants.PayNotifyUrl;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Slf4j
public enum PayType {
    /**
     * 微信支付类型 类型 1App支付 2Web支付 3jsApi
     */
    APP(1, "App支付", PayNotifyUrl.APP_NOTIFY_URL, 74),
    WEB(2, "Web支付", PayNotifyUrl.WEB_NOTIFY_URL, 5),
    JSAPI(3, "jsApi", PayNotifyUrl.JSAPI_NOTIFY_URL, 45),
    ALI_APP(4, "支付宝App支付", PayNotifyUrl.ALI_APP_NOTIFY_URL, null),
    ALI_WEB(5, "支付宝H5支付", PayNotifyUrl.ALI_WEB_NOTIFY_URL, null)
    ;
    private final Integer code;
    private final String desc;
    private final String url;
    private final Integer cacheKey;

    PayType(Integer code, String desc, String url, Integer cacheKey) {
        this.code = code;
        this.desc = desc;
        this.url = url;
        this.cacheKey = cacheKey;
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String url() {
        return url;
    }

    public Integer cacheKey() {
        return cacheKey;
    }

    /**
     * 获取回调地址
     * @param code
     * @return
     */
    public static String getNotifyUrl(Integer code) {
        PayType[] values = PayType.values();
        for (PayType payType : values) {
            if (Objects.equals(code, payType.code())) {
                return payType.url;
            }
        }

        log.error("获取微信回调地址失败");
        throw new SqxException("不支持的微信支付方式");
    }

    /**
     * 获取appId缓存key
     * @return
     */
    public static Integer getAppIdCacheKey(Integer code) {
        PayType[] values = PayType.values();
        for (PayType payType : values) {
            if (Objects.equals(code, payType.code())) {
                return payType.cacheKey;
            }
        }

        log.error("获取appId缓存key失败");
        throw new SqxException("不支持的微信支付方式");
    }
}
