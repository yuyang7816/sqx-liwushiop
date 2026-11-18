package com.sqx.referral.commission.domain.vo;

import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.ReferralCommission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-04 14:44:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReferralCommissionVo extends ReferralCommission {

    @ApiModelProperty(value = "订单信息")
    private GiftOrder giftOrder;

    @ApiModelProperty(value = "用户信息")
    private UserEntity userEntity;
}
