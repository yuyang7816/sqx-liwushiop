package com.sqx.domain.repository.domain.giftMall.dto;

import com.sqx.domain.repository.entity.giftMall.PayDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class RechargeRecordVo extends PayDetails {

    @ApiModelProperty("用户名称")
    private String userName;
}
