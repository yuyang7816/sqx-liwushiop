package com.sqx.invoice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.giftMall.Invoice;
import com.sqx.invoice.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "发票管理", tags = {"管理端-发票管理"})
@RequestMapping(value = "/admin/invoice/")
public class AdminInvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation("获取所有发票列表")
    @GetMapping("adminGetIndentList")
    public Result adminGetIndentList(Integer page, Integer limit, Long userId, String phone, String indentNumber, Integer status, String startTime, String endTime) {
        IPage<Invoice> invoiceIPage = invoiceService.adminGetIndentList(page, limit, userId, phone, indentNumber, status, startTime, endTime);
        return Result.success(invoiceIPage);
    }

    @ApiOperation("用户获取发票信息")
    @GetMapping("getInvoiceInfo")
    public Result getInvoiceInfo(Long invoiceId, String indentNumber) {
        Invoice invoiceInfo = invoiceService.getInvoiceInfo(invoiceId, indentNumber);
        return Result.success(invoiceInfo);
    }

    @ApiOperation("管理员修改发票信息")
    @PostMapping("updateInvoiceInfo")
    public Result updateInvoiceInfo(Invoice invoice) {
        return invoiceService.updateById(invoice)?Result.success():Result.failed();
    }
    @ApiOperation("管理员修改发票信息")
    @PostMapping("updateInvoice")
    public Result updateInvoice(String ids,Integer status) {
        return invoiceService.updateInvoice(ids,status);
    }
    /**
     * 导出
     */
//    @ApiOperation("发票导出")
//    @ResponseBody
//    @GetMapping("/export")
//    public void export(HttpServletResponse response,
//                       Integer userType,
//                       Integer invoiceType,
//                       String invoiceTitle,
//                       String taxpayerNumber,
//                       String bankDeposit,
//                       String userPhone,
//                       String indentId,
//                       Integer status,
//                       String startTime, String endTime
//    ) throws Exception {
//        ExcelData data = invoiceService.listExcel(userType, invoiceType, invoiceTitle, taxpayerNumber, bankDeposit, userPhone, indentId, status,startTime,endTime);
//        ExportExcelUtils.exportExcel(response, "发票列表.xlsx", data);
//    }
}