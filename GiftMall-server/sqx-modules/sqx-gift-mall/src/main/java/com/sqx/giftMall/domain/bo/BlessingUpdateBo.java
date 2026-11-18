package com.sqx.giftMall.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class BlessingUpdateBo implements Serializable {
    private static final long serialVersionUID = -1113003318656272548L;
    /**
     * id
     */
    @ApiModelProperty("祝福语id")
    private Long id;

    /**
     * 祝福语
     */
    @ApiModelProperty("祝福语内容")
    private String content;
}
