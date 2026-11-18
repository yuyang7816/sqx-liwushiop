package com.sqx.domain.repository.domain.giftMall.dto;

import com.sqx.domain.repository.entity.giftMall.PayDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2025/9/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayDetailsVo extends PayDetails {

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
