package com.sqx.giftMall.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class CardGroupByCategoryListBo implements Serializable {
    private static final long serialVersionUID = 2850145272750168283L;

    @ApiModelProperty("卡片分类id")
    private Long cardCategoryId;
}
