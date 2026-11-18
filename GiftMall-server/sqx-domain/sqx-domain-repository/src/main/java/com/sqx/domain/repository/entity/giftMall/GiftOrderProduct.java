package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品表
 * @TableName gift_order_product
 */
@TableName(value ="gift_order_product")
@Data
public class GiftOrderProduct implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 订单id
     */
    private String orderNo;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品类型id
     */
    private Long typeId;

    /**
     * 商品skuid
     */
    private Long skuId;

    /**
     * 商品sku信息
     */
    private String skuDetailJson;

    /**
     * 商品价格
     */
    private BigDecimal price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}