package com.sqx.domain.repository.service.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;
import com.sqx.domain.repository.entity.giftMall.PayDetails;

/**
* @author Administrator
* @description 针对表【pay_details(支付详情)】的数据库操作Service
* @createDate 2024-07-19 15:49:43
*/
public interface IPayDetailsService extends IService<PayDetails> {

    IPage<PayDetailsVo> payDetails(Page<PayDetails> initPage, PayDetailsBO payDetailsBO);
}
