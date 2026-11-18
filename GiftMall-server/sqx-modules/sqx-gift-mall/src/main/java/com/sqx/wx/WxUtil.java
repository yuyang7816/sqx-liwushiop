package com.sqx.wx;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import com.sqx.common.core.exception.BizException;
import com.sqx.giftMall.utils.http.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/8/5
 */
@Slf4j
public class WxUtil {

    /**
     * 微信小程序登录
     * @param appId appId
     * @param secret secret
     * @param js_code code
     */
    public static WxJsLoginRes wxJsLogin(String appId, String secret, String js_code) {
        String api = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("js_code", js_code);
        params.put("grant_type", "authorization_code");

        String resp = OkHttpUtil.doGet(api, params, null);
        WxJsLoginRes wxJsLoginRes = JSONObject.parseObject(resp, WxJsLoginRes.class);
        if (Objects.nonNull(wxJsLoginRes.getErrcode()) && 0 != wxJsLoginRes.getErrcode()) {
            log.error("微信小程序登录返回结果: {}", wxJsLoginRes);
            throw new BizException("登录失败");
        }

        return wxJsLoginRes;
    }

    /**
     * 解密手机号
     * @param decryptData 加密手机号（微信返回）
     * @param key session_key
     * @param iv iv（微信返回）
     */
    public static String decryptS5(String decryptData, String key, String iv) {
        try {
            byte[] encData = Base64.decode(decryptData);
            byte[] ivs = Base64.decode(iv);
            byte[] keys = Base64.decode(key);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivs);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keys, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("解密手机号失败");
        }
    }
}
