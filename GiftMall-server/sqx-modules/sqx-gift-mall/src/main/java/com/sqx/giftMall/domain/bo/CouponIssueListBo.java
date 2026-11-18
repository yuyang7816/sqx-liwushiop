package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class CouponIssueListBo implements Serializable {
    private static final long serialVersionUID = -4448015308465168462L;

    /**
     * 领券时间
     */
    @ApiModelProperty("领券时间 是否在领券开始时间~领券结束时间范围内")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime couponTime;

    /**
     * 是否限量（1限量 2不限量）
     */
    @ApiModelProperty("是否限量（1限量 2不限量）")
    private Integer limited;

    /**
     * 状态（0开启 1关闭）
     */
    @ApiModelProperty("状态（0开启 1关闭）")
    private Integer status;
}
