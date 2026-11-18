package com.sqx.giftMall.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author chengming
 * @since 2024/7/15
 */
@Data
public class BlessingSaveBo implements Serializable {
    private static final long serialVersionUID = -4825291683156892976L;

    /**
     * 祝福语
     */
    @NotBlank(message = "请输入祝福语")
    private String content;
}
