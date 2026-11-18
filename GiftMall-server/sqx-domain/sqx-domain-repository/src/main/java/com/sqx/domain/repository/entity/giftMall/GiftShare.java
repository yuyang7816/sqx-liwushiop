package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 礼物分享表
 * @TableName gift_share
 */
@TableName(value ="gift_share")
@Data
public class GiftShare implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderNo;

    /**
     * 分享用户id
     */
    private Long userId;

    /**
     * 领取用户id
     */
    private Long receiveUserId;

    /**
     * 状态 1待领取 2已领取 3已使用 4已拒绝 5已超时
     */
    private Integer status;

    /**
     * 领取时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}