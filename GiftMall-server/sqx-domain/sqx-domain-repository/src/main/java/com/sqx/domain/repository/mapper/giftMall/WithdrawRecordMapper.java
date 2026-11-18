package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.bo.WithdrawRecordPageBo;
import com.sqx.domain.repository.domain.giftMall.dto.WithdrawRecordVo;
import com.sqx.domain.repository.entity.giftMall.WithdrawRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 提现记录 Mapper 接口
 * </p>
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {

    IPage<WithdrawRecordVo> pageInfo(Page<WithdrawRecord> initPage, @Param("param") WithdrawRecordPageBo withdrawRecordPageBo);

    BigDecimal sumAmount(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
