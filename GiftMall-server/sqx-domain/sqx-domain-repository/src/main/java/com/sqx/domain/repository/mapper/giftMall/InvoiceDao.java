package com.sqx.domain.repository.mapper.giftMall;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.entity.giftMall.Invoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fang
 * @date 2022/8/3
 */
public interface InvoiceDao extends BaseMapper<Invoice> {


    IPage<Invoice> adminGetIndentList(@Param("pages") Page<Invoice> pages, @Param("userId") Long userId, @Param("phone") String phone, @Param("indentNumber") String indentNumber, @Param("status") Integer status, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Invoice> getInvoiceList(@Param("userType") Integer userType, @Param("invoiceType") Integer invoiceType, @Param("invoiceTitle") String invoiceTitle, @Param("taxpayerNumber") String taxpayerNumber, @Param("bankDeposit") String bankDeposit, @Param("userPhone") String userPhone, @Param("indentId") String indentId, @Param("status") Integer status, String startTime, String endTime);

    IPage<Invoice> getMyInvoiceList(@Param("pages") Page<Invoice> pages, @Param("userId") Long userId);
}
