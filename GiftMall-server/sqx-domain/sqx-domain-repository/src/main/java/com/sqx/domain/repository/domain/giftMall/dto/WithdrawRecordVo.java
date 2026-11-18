package com.sqx.domain.repository.domain.giftMall.dto;

import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.WithdrawRecord;
import com.sqx.domain.repository.entity.giftMall.WithdrawalAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WithdrawRecordVo extends WithdrawRecord {

    @ApiModelProperty("用户信息")
    private UserEntity userEntity;

    @ApiModelProperty("提现账户信息")
    private WithdrawalAccount withdrawalAccount;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("账户名称")
    private String accountName;

    @ApiModelProperty("账户号")
    private String accountNumber;

}
