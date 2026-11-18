package com.sqx.invoice.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.giftMall.Invoice;
import com.sqx.giftMall.domain.vo.GiftOrderVo;
import com.sqx.invoice.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "发票管理", tags = {"用户端-发票管理"})
@RequestMapping(value = "/app/invoice/")
public class AppInvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Login
    @ApiOperation("用户获取可申请发票的订单列表")
    @GetMapping("getIndentList")
    public Result getIndentList(Integer page, Integer limit, @RequestAttribute("userId") Long userId, Integer indentType, String indentNumber, String startTime, String endTime) {
        IPage<GiftOrderVo> orderIPage = invoiceService.getOrderList(page, limit, userId, indentType, indentNumber, startTime, endTime);
        return Result.success(orderIPage);
    }

    @Login
    @ApiOperation("用户申请发票")
    @PostMapping("applyInvoice")
    public Result applyInvoice(@RequestAttribute("userId") Long userId, @RequestBody Invoice invoice) {
        return invoiceService.applyInvoice(userId, invoice);
    }

    @Login
    @ApiOperation("用户获取发票信息")
    @GetMapping("getInvoiceInfo")
    public Result getInvoiceInfo(Long invoiceId, String indentNumber) {
        Invoice invoiceInfo = invoiceService.getInvoiceInfo(invoiceId, indentNumber);
        return Result.success(invoiceInfo);
    }

    @Login
    @ApiOperation("获取开票记录")
    @GetMapping("getMyInvoiceList")
    public Result getMyInvoiceList(Integer page,Integer limit ,@RequestAttribute("userId")Long userId){
        IPage<Invoice> myInvoiceList = invoiceService.getMyInvoiceList(page, limit, userId);
        return Result.success(myInvoiceList);
    }
}
