package com.sqx.domain.repository.domain.giftMall.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2025/9/11
 */
@Data
public class PayDetailsBO extends PageParam {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "支付平台 1微信 2支付宝 3paypal 4stripe 5余额")
    private Integer platform;

    @ApiModelProperty(value = "支付类型 应用：APP 网页：WEB 小程序：JSAPI")
    private String tradeType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "第三方平台支付流水号")
    private String tradeNo;

    @ApiModelProperty(value = "业务类型 vip: 购买会员 recharge: 充值余额 order: 订单")
    private String businessType;

    @ApiModelProperty(value = "0待支付 1支付成功 2失败")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
