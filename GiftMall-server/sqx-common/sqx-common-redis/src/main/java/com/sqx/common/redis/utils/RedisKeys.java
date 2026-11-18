package com.sqx.common.redis.utils;

/**
 * Redis所有Keys
 *
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    /**
     * common_info 缓存key
     */
    public static final String COMMON_INFO_KEY = "common::info::";

    /**
     * 订单过期key
     */
    public static final String ORDER_EXPIRE_KEY = "order::expire::";
}
