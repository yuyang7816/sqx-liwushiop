package com.sqx.auth.domain.vo;

import com.sqx.domain.repository.entity.app.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/4/22
 */
@Data
public class LoginVO {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "token过期时间")
    private Long expire;

    @ApiModelProperty(value = "用户信息")
    private UserEntity user;
}
