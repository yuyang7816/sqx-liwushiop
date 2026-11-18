package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.domain.repository.domain.giftMall.bo.RechargeRecordBo;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;
import com.sqx.domain.repository.domain.giftMall.dto.RechargeRecordVo;
import com.sqx.domain.repository.entity.giftMall.PayDetails;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【pay_details(支付详情)】的数据库操作Mapper
* @createDate 2024-07-19 15:49:43
* @Entity com.sqx.domain.repository.entity.giftMall.PayDetails
*/
public interface PayDetailsMapper extends BaseMapper<PayDetails> {

    IPage<RechargeRecordVo> rechargeRecord(Page<PayDetails> initPage, @Param("param") RechargeRecordBo rechargeRecordBo);

    IPage<PayDetailsVo> payDetails(Page<PayDetails> initPage, @Param("param") PayDetailsBO payDetailsBO);
}




