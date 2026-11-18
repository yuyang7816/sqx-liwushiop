package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品规格属性
 * @TableName gift_product_rule_value
 */
@TableName(value ="gift_product_rule_value")
@Data
public class GiftProductRuleValue implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格id
     */
    @TableField(value = "rule_id")
    private Long ruleId;

    /**
     * 规格属性名称
     */
    @TableField(value = "value")
    private String value;

    /**
     * 规格属性值
     */
    @TableField(value = "detail")
    private String detail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}