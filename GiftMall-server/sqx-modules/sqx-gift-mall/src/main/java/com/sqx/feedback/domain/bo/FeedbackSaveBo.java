package com.sqx.feedback.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-06 15:53:00
 */
@Data
public class FeedbackSaveBo {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "意见")
    private String opinion;

    @ApiModelProperty(value = "联系方式")
    private String contact;

}
