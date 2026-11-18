package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardCategoryListBo implements Serializable {
    private static final long serialVersionUID = -7199547838087935979L;

    /**
     * 卡片名称
     */
    @ApiModelProperty("卡片名称")
    private String cardCategoryName;
}
