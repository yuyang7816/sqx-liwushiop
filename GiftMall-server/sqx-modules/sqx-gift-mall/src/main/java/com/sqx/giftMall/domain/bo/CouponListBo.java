package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class CouponListBo implements Serializable {
    private static final long serialVersionUID = -7547606788963930073L;

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
     * 状态（1开启 2关闭）
     */
    @ApiModelProperty("状态（1开启 2关闭）")
    private Integer status;
}
