package com.sqx.giftMall.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftCoupon;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class GiftCouponIssueVo implements Serializable {
    private static final long serialVersionUID = 6785342076602708509L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 领券开启时间
     */
    @ApiModelProperty("领券开启时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 领券结束时间
     */
    @ApiModelProperty("领券结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 发布数量
     */
    @ApiModelProperty("发布数量")
    private Integer number;

    /**
     * 剩余数量
     */
    @ApiModelProperty("剩余数量")
    private Integer remainNumber;

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

    /**
     * 优惠券id
     */
    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券信息")
    private GiftCoupon giftCoupon;

    @ApiModelProperty("领取状态 0已领取 1未领取")
    private Integer userGetCouponStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
