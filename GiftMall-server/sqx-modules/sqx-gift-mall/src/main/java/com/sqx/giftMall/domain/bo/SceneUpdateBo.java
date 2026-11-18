package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class SceneUpdateBo implements Serializable {
    private static final long serialVersionUID = 1497551801697199836L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @NotNull(message = "请选择送礼场景")
    private Long id;

    /**
     * 送礼场景名称
     */
    @ApiModelProperty("送礼场景名称")
    private String name;

}
