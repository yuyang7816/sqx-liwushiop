package com.sqx.giftMall.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class GiftCouponInfoVo implements Serializable {
    private static final long serialVersionUID = -911569221610510162L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 优惠券类型（1通用券 2商品券）
     */
    @ApiModelProperty("优惠券类型（1通用券 2商品券）")
    private Integer type;

    /**
     * 优惠券名称
     */
    @ApiModelProperty("优惠券名称")
    private String couponName;

    /**
     * 优惠券面值
     */
    @ApiModelProperty("优惠券面值")
    private BigDecimal lessMoney;

    /**
     * 优惠券最低消费
     */
    @ApiModelProperty("优惠券最低消费")
    private BigDecimal minMoney;

    /**
     * 优惠券有效期限(天)
     */
    @ApiModelProperty("优惠券有效期限(天)")
    private Integer validDay;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态（1开启 2关闭）
     */
    @ApiModelProperty("状态（1开启 2关闭）")
    private Integer status;

    /**
     * 商品ids(多个) 逗号分隔
     */
    @ApiModelProperty("商品ids(多个) 逗号分隔")
    private String productIds;

    /**
     * 商品图片(多个)
     */
    @ApiModelProperty("商品图片(多个)")
    private String productImages;

    @ApiModelProperty("商品列表")
    List<GiftProduct> productList;

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
