package com.sqx.giftMall.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.sqx.domain.repository.entity.giftMall.GiftProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Data
public class GiftProductVo implements Serializable {
    private static final long serialVersionUID = 3139769332428621512L;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String name;

    /**
     * 商品描述
     */
    @ApiModelProperty("商品描述")
    private String description;

    /**
     * 商品封面图片
     */
    @ApiModelProperty("商品封面图片")
    private String coverImg;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String img;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal price;

    /**
     * 销量
     */
    @ApiModelProperty("销量")
    private Integer sales;

    /**
     * 商品状态(默认0正常 1下架)
     */
    @ApiModelProperty("商品状态(默认0正常 1下架)")
    private Integer status;

    /**
     * 是否精选好物 0是 1不是
     */
    @ApiModelProperty("是否精选好物 0是 1不是")
    private Integer goodPick;

    /**
     * 是否特价商品 0是 1不是
     */
    @ApiModelProperty("是否特价商品 0是 1不是")
    private Integer specialOffer;

    /**
     * 原价
     */
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /**
     * 折扣比例 0.1表示10%1折
     */
    @ApiModelProperty("折扣比例 0.1表示10%1折")
    private BigDecimal discountRatio;

    /**
     * 商品类型id
     */
    @ApiModelProperty("商品类型id")
    private Long typeId;

    /**
     * 商品类型信息
     */
    @ApiModelProperty("商品类型信息")
    private GiftProductType giftProductType;

    @ApiModelProperty("商品规格列表")
    private List<GiftProductAttrDto> giftProductAttrList;

    @ApiModelProperty("商品sku列表")
    private List<GiftProductSku> giftProductSkuList;

    /**
     * 送礼场景id
     */
    @ApiModelProperty("送礼场景id")
    private Long sceneId;

    /**
     * 送礼场景名称
     */
    @ApiModelProperty("送礼场景名称")
    private String sceneName;

    /**
     * 送礼对象id
     */
    @ApiModelProperty("送礼对象id")
    private Long targetId;

    /**
     * 送礼对象名称
     */
    @ApiModelProperty("送礼对象名称")
    private String targetName;

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
