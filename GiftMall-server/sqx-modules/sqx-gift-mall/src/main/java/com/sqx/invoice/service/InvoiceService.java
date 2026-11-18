package com.sqx.invoice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.giftMall.Invoice;
import com.sqx.giftMall.domain.vo.GiftOrderVo;

public interface InvoiceService extends IService<Invoice> {


    Result applyInvoice(Long userId, Invoice invoice);


    IPage<Invoice> adminGetIndentList(Integer page, Integer limit, Long userId, String phone, String indentNumber, Integer status, String startTime, String endTime);

    Invoice getInvoiceInfo(Long invoiceId,String indentNumber);

    Result updateInvoice(String ids,Integer status);

//    ExcelData listExcel(Integer userType,Integer invoiceType, String invoiceTitle, String taxpayerNumber, String bankDeposit, String userPhone, String indentId, Integer status,String startTime, String endTime);

    IPage<Invoice> getMyInvoiceList(Integer page, Integer limit, Long userId);

    IPage<GiftOrderVo> getOrderList(Integer page, Integer limit, Long userId, Integer indentType, String indentNumber, String startTime, String endTime);
}
