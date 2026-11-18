package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chengming
 * @since 2024/7/18
 */
@Data
public class UserCollectSaveBo implements Serializable {
    private static final long serialVersionUID = -2624867121228603923L;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @NotNull(message = "商品id不能为空")
    private Long productId;
}
