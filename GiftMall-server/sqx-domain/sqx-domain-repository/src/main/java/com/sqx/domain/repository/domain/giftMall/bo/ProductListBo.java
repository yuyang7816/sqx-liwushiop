package com.sqx.domain.repository.domain.giftMall.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinitions;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Data
public class ProductListBo implements Serializable {
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String name;

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

    /**
     * 预算起始金额
     */
    @ApiModelProperty("预算起始金额")
    private BigDecimal startBudget;

    /**
     * 预算截止金额
     */
    @ApiModelProperty("预算截止金额")
    private BigDecimal endBudget;
}
