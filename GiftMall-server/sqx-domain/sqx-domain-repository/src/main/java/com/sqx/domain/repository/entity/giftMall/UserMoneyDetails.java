package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @TableName user_money_details
 */
@TableName(value ="user_money_details")
@Data
public class UserMoneyDetails implements Serializable {
    /**
     * 钱包详情id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 邀请用户id
     */
    @TableField(value = "by_user_id")
    private Long byUserId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 1注册   2购买 3提现 4系统赠送
     */
    @TableField(value = "classify")
    private Integer classify;

    /**
     * 类型（1增加 2减少）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 状态 1待支付 2已到账 3取消
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}