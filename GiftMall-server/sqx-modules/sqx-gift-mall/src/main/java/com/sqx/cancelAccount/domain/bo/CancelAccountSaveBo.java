package com.sqx.cancelAccount.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-07 09:31:24
 */
@Data
public class CancelAccountSaveBo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

}
