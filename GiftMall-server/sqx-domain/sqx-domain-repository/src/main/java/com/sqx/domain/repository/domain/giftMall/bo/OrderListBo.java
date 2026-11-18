package com.sqx.domain.repository.domain.giftMall.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Data
public class OrderListBo implements Serializable {
    private static final long serialVersionUID = 4680057806599856271L;

    @ApiModelProperty("订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送）")
    private Integer status;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("订单类型（1普通订单 2拼团 3秒杀）")
    private Integer orderType;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("用户id")
    private Long userId;
}
