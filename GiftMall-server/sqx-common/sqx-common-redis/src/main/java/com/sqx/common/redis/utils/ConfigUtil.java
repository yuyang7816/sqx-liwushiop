package com.sqx.common.redis.utils;

import com.alibaba.fastjson2.JSONObject;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.utils.SpringContextHolder;

/**
 * @author chengming
 * @since 2025/6/30
 */
public class ConfigUtil {

    /**
     * 获取缓存中配置
     * @param key 配置key
     * @return
     */
    public static String getConfigValue(Integer key) {
        String cacheKey = RedisKeys.COMMON_INFO_KEY + key;
        if (!RedisUtil.hasKey(cacheKey)) {
            throw new BizException("配置不存在");
        }

        String config = RedisUtil.getCacheObject(cacheKey);
        JSONObject configJson = JSONObject.parseObject(config);
        return configJson.getString("value");
    }
}
