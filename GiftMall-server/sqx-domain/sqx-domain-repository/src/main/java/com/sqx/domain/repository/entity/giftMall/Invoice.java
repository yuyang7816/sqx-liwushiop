package com.sqx.domain.repository.entity.giftMall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fang
 * @description invoice
 * @date 2022-08-22
 */
@Data
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票id
     */
    @TableId(type = IdType.AUTO)
    private Long invoiceId;
    /**
     * 申请人id
     */
    private Long userId;

    /**
     * 用户类型 1公司 2个人
     */
    @ApiModelProperty(name = "用户类型 1公司 2个人")
    private Integer userType;
    /**
     * 发票类型
     */
    @ApiModelProperty(name = "发票类型")
    private Integer invoiceType;

    /**
     * 发票信息
     */
    @ApiModelProperty(name = "发票内容")
    private String invoiceContent;


    /**
     * 发票抬头
     */
    @ApiModelProperty(name = "发票抬头")
    private String invoiceHeader;
    /**
     * 纳税人识别号
     */
    @ApiModelProperty(name = "纳税人识别号")
    private String taxPayerNumber;
    /**
     * 用户地址
     */
    @ApiModelProperty(name = "注册地址")
    private String bankAddress;
    /**
     * 注册电话
     */
    @ApiModelProperty(name = "注册电话")
    private String bankPhone;
    /**
     * 申请人开户行
     */
    @ApiModelProperty(name = "开户行")
    private String bankDeposit;
    /**
     * 银行卡号
     */
    @ApiModelProperty(name = "银行卡号")
    private String bankNumber;


    /**
     * 发票金额
     */
    @ApiModelProperty(name = "发票金额")
    private BigDecimal invoiceMoney;

    /**
     * 申请人电子邮件
     */
    @ApiModelProperty(name = "申请人电子邮件")
    private String userEmail;
    /**
     * 发票内容
     */
    @ApiModelProperty(name = "发票标题")
    private String invoiceTitle;

    /**
     * 申请人手机号码
     */
    @ApiModelProperty(name = "申请人手机号码")
    private String userPhone;

    /**
     * 开票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(name = "开票时间")
    private LocalDateTime invoicingTime;


    /**
     * 创建时间(自动填充)
     */

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间(自动填充)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 订单号
     */
    private String indentNumber;
    /**
     * 1待开票 2已开票
     */
    @ApiModelProperty(name = "1待开票 2已开票")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(name = "用户昵称")
    private String nickName;
}
