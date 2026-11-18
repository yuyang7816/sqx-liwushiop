package com.sqx.giftMall.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class CouponIssueUpdateBo implements Serializable {
    private static final long serialVersionUID = 5964713031881611132L;

    @ApiModelProperty("优惠券发布id")
    @NotNull(message = "请选择优惠券发布")
    private Long id;

    /**
     * 领券开启时间
     */
    @ApiModelProperty("领券开启时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请输入领券开启时间")
    @Future(message = "领券开启时间不能早于当前日期")
    private LocalDateTime startTime;

    /**
     * 领券结束时间
     */
    @ApiModelProperty("领券结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请输入领券结束时间")
    @Future(message = "领券结束时间不能早于当前日期")
    private LocalDateTime endTime;

    /**
     * 发布数量
     */
    @ApiModelProperty("发布数量")
    @NotNull(message = "请输入发布数量")
    @Min(value = 0, message = "发布数量不能小于0")
    @Max(value = Integer.MAX_VALUE - 1, message = "发布数量不能多于" + (Integer.MAX_VALUE - 1))
    private Integer number;

    /**
     * 是否限量（1限量 2不限量）
     */
    @ApiModelProperty("是否限量（1限量 2不限量）")
    @NotNull(message = "请输入是否限量")
    @Min(value = 0, message = "限制数量不能小于0")
    @Max(value = Integer.MAX_VALUE - 1, message = "限制数量不能多于" + (Integer.MAX_VALUE - 1))
    private Integer limited;

    /**
     * 优惠券id
     */
    @ApiModelProperty("优惠券id")
    @NotNull(message = "请选择优惠券")
    private Long couponId;
}
