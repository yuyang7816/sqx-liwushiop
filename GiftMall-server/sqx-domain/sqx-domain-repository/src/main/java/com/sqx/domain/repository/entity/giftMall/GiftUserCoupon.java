package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 优惠券用户领取记录
 * @TableName gift_user_coupon
 */
@TableName(value ="gift_user_coupon")
@Data
public class GiftUserCoupon implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 失效时间
     */
    @TableField(value = "failure_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failureTime;

    /**
     * 状态（1未使用 2已使用 3已过期）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 优惠券id
     */
    @TableField(value = "coupon_id")
    private Long couponId;

    /**
     * 优惠券类型（1通用券 2商品券）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 优惠券发布id
     */
    @TableField(value = "coupon_issue_id")
    private Long couponIssueId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}