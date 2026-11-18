package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 优惠券信息
 * @TableName gift_coupon
 */
@TableName(value ="gift_coupon")
@Data
public class GiftCoupon implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券类型（1通用券 2商品券）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 优惠券名称
     */
    @TableField(value = "coupon_name")
    private String couponName;

    /**
     * 优惠券面值
     */
    @TableField(value = "less_money")
    private BigDecimal lessMoney;

    /**
     * 优惠券最低消费
     */
    @TableField(value = "min_money")
    private BigDecimal minMoney;

    /**
     * 优惠券有效期限(天)
     */
    @TableField(value = "valid_day")
    private Integer validDay;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 状态（0开启 1关闭）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 商品ids(多个) 逗号分隔
     */
    @TableField(value = "product_ids")
    private String productIds;

    /**
     * 商品图片(多个)
     */
    @TableField(value = "product_images")
    private String productImages;

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