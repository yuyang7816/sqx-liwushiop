package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 商品基本信息
 * @TableName gift_product
 */
@TableName(value ="gift_product")
@Data
public class GiftProduct implements Serializable {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * GiftProductSku
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品封面图片
     */
    private String coverImg;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 商品状态(默认0正常 1下架)
     */
    private Integer status;

    /**
     * 是否精选好物 0是 1不是
     */
    private Integer goodPick;

    /**
     * 是否特价商品 0是 1不是
     */
    private Integer specialOffer;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 折扣比例 0.1表示10%1折
     */
    private BigDecimal discountRatio;

    /**
     * 商品类型id
     */
    private Long typeId;

    /**
     * 送礼场景id
     */
    private Long sceneId;

    /**
     * 送礼对象id
     */
    private Long targetId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}