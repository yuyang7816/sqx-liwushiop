package com.sqx.giftMall.domain.vo;

import com.sqx.domain.repository.entity.giftMall.GiftOrder;
import com.sqx.domain.repository.entity.giftMall.GiftShare;
import com.sqx.domain.repository.domain.giftMall.dto.GiftOrderProductVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chengming
 * @since 2025/7/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GiftOrderVo extends GiftOrder {

    @ApiModelProperty("商品信息")
    private List<GiftOrderProductVo> productList;

    @ApiModelProperty("分享信息")
    private GiftShare giftShare;

    @ApiModelProperty("分享用户名称")
    private String shareUserName;

    @ApiModelProperty("发票状态 0=未开票 1=待开票 2=已开票 3拒绝开票")
    private Integer invoiceStatus;
}
