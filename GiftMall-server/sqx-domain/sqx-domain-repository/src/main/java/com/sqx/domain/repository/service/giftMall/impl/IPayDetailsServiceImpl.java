package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;
import com.sqx.domain.repository.entity.giftMall.PayDetails;
import com.sqx.domain.repository.service.giftMall.IPayDetailsService;
import com.sqx.domain.repository.mapper.giftMall.PayDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【pay_details(支付详情)】的数据库操作Service实现
* @createDate 2024-07-19 15:49:43
*/
@Service
public class IPayDetailsServiceImpl extends ServiceImpl<PayDetailsMapper, PayDetails>
    implements IPayDetailsService {

    @Autowired
    private PayDetailsMapper payDetailsMapper;

    @Override
    public IPage<PayDetailsVo> payDetails(Page<PayDetails> initPage, PayDetailsBO payDetailsBO) {
        return payDetailsMapper.payDetails(initPage, payDetailsBO);
    }
}




