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
public class UserCouponVo implements Serializable {
    private static final long serialVersionUID = 7743569704460605534L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 失效时间
     */
    @ApiModelProperty("失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failureTime;

    /**
     * 状态（1未使用 2已使用 3已过期）
     */
    @ApiModelProperty("状态（1未使用 2已使用 3已过期）")
    private Integer status;

    /**
     * 优惠券id
     */
    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券信息")
    private GiftCoupon giftCoupon;

    /**
     * 优惠券类型（1通用券 2商品券）
     */
    @ApiModelProperty("优惠券类型（1通用券 2商品券）")
    private Integer type;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long productId;

    /**
     * 优惠券发布id
     */
    @ApiModelProperty("优惠券发布id")
    private Long couponIssueId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
