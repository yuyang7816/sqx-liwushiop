package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName user_money
 */
@TableName(value ="user_money")
@Data
public class UserMoney implements Serializable {
    /**
     * 钱包id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 钱包金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}