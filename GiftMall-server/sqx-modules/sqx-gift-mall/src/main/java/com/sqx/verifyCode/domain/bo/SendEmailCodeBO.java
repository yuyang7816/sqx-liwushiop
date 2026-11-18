package com.sqx.verifyCode.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chengming
 * @since 2025/4/24
 */
@Data
public class SendEmailCodeBO {
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "业务码")
    private String bizCode;
}
