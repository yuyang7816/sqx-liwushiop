package com.sqx.giftMall.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Data
public class GiftCartVo implements Serializable {
    private static final long serialVersionUID = 1701349989777927323L;

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
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long productId;

    /**
     * 商品skuId
     */
    @ApiModelProperty("商品skuId")
    private Long skuId;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer number;

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

    @ApiModelProperty("商品基本信息")
    private GiftProduct giftProduct;

    @ApiModelProperty("商品sku信息")
    private GiftProductSku giftProductSku;
}
