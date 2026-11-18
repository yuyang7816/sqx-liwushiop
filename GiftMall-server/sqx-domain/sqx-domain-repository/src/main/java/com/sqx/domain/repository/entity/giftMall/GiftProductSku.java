package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品SKU
 * @TableName gift_product_sku
 */
@TableName(value ="gift_product_sku")
@Data
public class GiftProductSku implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * sku图片
     */
    @TableField(value = "sku_img")
    private String skuImg;

    /**
     * 原价
     */
    private BigDecimal skuOriginPrice;

    /**
     * sku商品售价
     */
    @TableField(value = "sku_price")
    private BigDecimal skuPrice;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 销量
     */
    @TableField(value = "sales")
    private Integer sales;

    /**
     * sku信息，json封装
     */
    @TableField(value = "detail_json")
    private String detailJson;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}