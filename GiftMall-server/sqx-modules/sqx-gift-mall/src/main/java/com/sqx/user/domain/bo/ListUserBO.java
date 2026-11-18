package com.sqx.user.domain.bo;


import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chengming
 * @since 2025/4/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListUserBO extends PageParam {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "状态 1正常 2禁用")
    private Integer status;

    @ApiModelProperty(value = "用户类型 0未认证 1普通用户 2企业用户")
    private Integer userType;
}
