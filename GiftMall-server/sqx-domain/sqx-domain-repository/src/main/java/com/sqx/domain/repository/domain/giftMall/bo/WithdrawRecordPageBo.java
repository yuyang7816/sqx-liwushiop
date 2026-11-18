package com.sqx.domain.repository.domain.giftMall.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.common.core.domain.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-05 10:10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WithdrawRecordPageBo extends PageParam {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("状态 1发起提现 2提现成功 3拒绝提现")
    private Integer status;

    @ApiModelProperty("提现账户 1支付宝 2微信")
    private Integer accountType;

    @ApiModelProperty("起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
