package com.sqx.domain.repository.domain.giftMall.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Data
public class GiftProductDto {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品名称
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
     * 商品类型信息
     */
    private GiftProductType giftProductType;

    /**
     * 送礼场景id
     */
    private Long sceneId;

    /**
     * 送礼场景名称
     */
    private String sceneName;

    /**
     * 送礼对象id
     */
    private Long targetId;

    /**
     * 送礼对象名称
     */
    private String targetName;

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
