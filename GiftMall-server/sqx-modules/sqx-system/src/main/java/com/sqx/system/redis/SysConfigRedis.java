package com.sqx.system.redis;



import com.sqx.common.redis.utils.RedisKeys;
import com.sqx.common.redis.utils.RedisUtil;
import com.sqx.domain.repository.entity.sys.SysConfigEntity;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 *
 */
@Component
public class SysConfigRedis {
    //@Autowired
    //private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        RedisUtil.setCacheObject(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        RedisUtil.deleteObject(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return RedisUtil.getCacheObject(key);
    }
}
