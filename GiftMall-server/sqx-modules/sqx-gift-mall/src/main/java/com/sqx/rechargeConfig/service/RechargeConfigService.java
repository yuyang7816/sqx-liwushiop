package com.sqx.rechargeConfig.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigInfoBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigPageBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigSaveBo;
import com.sqx.rechargeConfig.domain.bo.RechargeConfigUpdateBo;
import com.sqx.rechargeConfig.domain.entity.RechargeConfig;
import com.sqx.rechargeConfig.domain.vo.RechargeConfigVo;

import java.util.List;

/**
 * 充值金额配置 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 14:48:43
 */
public interface RechargeConfigService {

    void saveInfo(RechargeConfigSaveBo rechargeConfigSaveBo);

    IPage<RechargeConfigVo> pageInfo(RechargeConfigPageBo rechargeConfigPageBo);

    RechargeConfigVo info(RechargeConfigInfoBo rechargeConfigInfoBo);

    void updateInfo(RechargeConfigUpdateBo rechargeConfigUpdateBo);

    void delete(RechargeConfigInfoBo rechargeConfigInfoBo);

    List<RechargeConfig> list();

}
