package com.sqx.domain.repository.domain.giftMall.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sqx.domain.repository.entity.giftMall.GiftProductAttrValue;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Data
public class GiftProductAttrDto implements Serializable {
    private static final long serialVersionUID = 5522203315451714054L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 规格模板id
     */
    private Long ruleId;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 规格列表
     */
    private List<GiftProductAttrValue> giftProductAttrValueList;

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
