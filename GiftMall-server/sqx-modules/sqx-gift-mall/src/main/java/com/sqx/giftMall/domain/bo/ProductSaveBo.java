package com.sqx.giftMall.domain.bo;

import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class ProductSaveBo implements Serializable {
    private static final long serialVersionUID = 7424591998227441024L;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @NotBlank(message = "请输入商品名称")
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
     * 商品价格(售价)
     */
    @ApiModelProperty("商品价格(售价)")
    private BigDecimal price;

    ///**
    // * 销量
    // */
    //private Integer sales;

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
    @DecimalMin(value = "0", inclusive = false, message = "折扣比例必须大于0")
    @DecimalMax(value = "1", inclusive = false, message = "折扣比例必须小于1")
    private BigDecimal discountRatio;

    /**
     * 商品类型id
     */
    @ApiModelProperty("商品类型id")
    private Long typeId;

    /**
     * 送礼场景id
     */
    @ApiModelProperty("送礼场景id")
    private Long sceneId;

    /**
     * 送礼对象id
     */
    @ApiModelProperty("送礼对象id")
    private Long targetId;

    @ApiModelProperty("商品规格")
    private List<GiftProductAttrDto> giftProductAttrList;

    @ApiModelProperty("商品sku")
    private List<GiftProductSku> giftProductSkuList;
}
