package com.sqx.domain.repository.domain.giftMall.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2025/9/4
 */
@Data
public class RechargeRecordBo extends PageParam {

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("支付平台 1微信 2支付宝 3paypal 4stripe 5余额")
    private Integer platform;

    @ApiModelProperty("支付类型 应用：APP 网页：WEB 小程序：JSAPI")
    private String tradeType;

    @ApiModelProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
