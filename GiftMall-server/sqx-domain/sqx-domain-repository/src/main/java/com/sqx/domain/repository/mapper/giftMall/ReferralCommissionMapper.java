package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.dto.ReferralListVo;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 佣金信息 Mapper 接口
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
public interface ReferralCommissionMapper extends BaseMapper<ReferralCommission> {

    BigDecimal sumUserCommission(@Param("userId") Long userId);

    IPage<ReferralListVo> referralList(Page<ReferralCommission> page, @Param("userId") Long userId);
}
