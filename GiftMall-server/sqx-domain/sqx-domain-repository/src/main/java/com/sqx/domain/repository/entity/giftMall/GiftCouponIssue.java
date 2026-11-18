package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

/**
 * 优惠券发布表
 * @TableName gift_coupon_issue
 */
@TableName(value ="gift_coupon_issue")
@Data
public class GiftCouponIssue implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 领券开启时间
     */
    @TableField(value = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 领券结束时间
     */
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 发布数量
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 剩余数量
     */
    @TableField(value = "remain_number")
    private Integer remainNumber;

    /**
     * 是否限量（1限量 2不限量）
     */
    @TableField(value = "limited")
    private Integer limited;

    /**
     * 状态（0开启 1关闭）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 优惠券id
     */
    @TableField(value = "coupon_id")
    private Long couponId;

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