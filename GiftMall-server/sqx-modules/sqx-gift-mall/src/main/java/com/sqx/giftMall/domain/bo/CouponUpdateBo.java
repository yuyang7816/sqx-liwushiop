package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class CouponUpdateBo implements Serializable {
    private static final long serialVersionUID = -4164796445662385400L;


    @ApiModelProperty("id")
    private Long id;

    /**
     * 优惠券类型（1通用券 2商品券）
     */
    @ApiModelProperty("优惠券类型（1通用券 2商品券）")
    @NotNull(message = "请选择优惠券类型")
    private Integer type;

    /**
     * 优惠券名称
     */
    @ApiModelProperty("优惠券名称")
    @NotBlank(message = "请输入优惠券名称")
    private String couponName;

    /**
     * 优惠券面值
     */
    @ApiModelProperty("优惠券面值")
    @NotNull(message = "请输入优惠券面值")
    private BigDecimal lessMoney;

    /**
     * 优惠券最低消费
     */
    @ApiModelProperty("优惠券最低消费")
    @NotNull(message = "请输入优惠券最低消费")
    private BigDecimal minMoney;

    /**
     * 优惠券有效期限(天)
     */
    @ApiModelProperty("优惠券有效期限(天)")
    @NotNull(message = "请输入优惠券有效期限")
    private Integer validDay;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

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
}
