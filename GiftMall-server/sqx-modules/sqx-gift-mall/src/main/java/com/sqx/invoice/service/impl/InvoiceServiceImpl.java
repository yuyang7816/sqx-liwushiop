package com.sqx.invoice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.giftMall.*;
import com.sqx.domain.repository.entity.sys.CommonInfo;
import com.sqx.domain.repository.mapper.giftMall.InvoiceDao;
import com.sqx.domain.repository.service.giftMall.*;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.domain.repository.domain.giftMall.dto.GiftOrderProductVo;
import com.sqx.giftMall.domain.vo.GiftOrderVo;
import com.sqx.giftMall.enums.OrderStatus;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.invoice.service.InvoiceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private CommonInfoService commonInfoService;
    @Autowired
    private IGiftOrderService iGiftOrderService;
    @Autowired
    private IGiftOrderProductService iGiftOrderProductService;
    @Autowired
    private IGiftProductService iGiftProductService;
    @Autowired
    private IGiftProductTypeService iGiftProductTypeService;
    @Autowired
    private IGiftProductSkuService iGiftProductSkuService;

    @Override
    public Result applyInvoice(Long userId, Invoice invoice) {
        UserEntity userEntity = iUserService.getOne(new QueryWrapper<UserEntity>().eq("user_id", userId));
        if (userEntity == null) {
            return Result.failed("当前用户不存在!");
        }
        String[] indentNumberList = invoice.getIndentNumber().split(",");
        BigDecimal allMoney = BigDecimal.ZERO;
        for (String number : indentNumberList) {
            GiftOrder giftOrder = iGiftOrderService.getOne(new LambdaQueryWrapper<GiftOrder>()
                    .eq(GiftOrder::getUserId, userEntity.getUserId())
                    .eq(GiftOrder::getOrderNo, number));
            if (giftOrder == null) {
                return Result.failed("当前订单不存在!");
            }
            Invoice invoiceServiceOne = invoiceDao.selectOne(new QueryWrapper<Invoice>().like("indent_number", number));
            if (invoiceServiceOne != null) {
                return Result.failed("请勿重复提交申请");
            }
            allMoney = allMoney.add(giftOrder.getPayMoney());
        }

        CommonInfo commonInfo = commonInfoService.findOne(318);
        if (allMoney.compareTo(new BigDecimal(commonInfo.getValue())) < 0) {
            return Result.failed("每次开票金额不得小于" + commonInfo.getValue() + "元");
        }
        invoice.setUserId(userId);
        invoice.setInvoiceMoney(allMoney);
        invoice.setStatus(1);
        return Result.success((invoiceDao.insert(invoice)));
    }

    @Override
    public IPage<Invoice> adminGetIndentList(Integer page, Integer limit, Long userId, String phone, String indentNumber, Integer status, String startTime, String endTime) {
        Page<Invoice> pages;
        if (page != null && limit != null) {
            pages = new Page<>(page, limit);
        } else {
            pages = new Page<>();
            pages.setSize(-1);
        }
        return invoiceDao.adminGetIndentList(pages, userId, phone, indentNumber, status, startTime, endTime);
    }

    @Override
    public Invoice getInvoiceInfo(Long invoiceId, String indentNumber) {

        QueryWrapper<Invoice> wrapper = new QueryWrapper<>();
        if (invoiceId != null) {
            wrapper.eq("invoice_id", invoiceId);
        }
        if (StringUtils.isNotBlank(indentNumber)) {
            wrapper.eq("indent_number", indentNumber);
        }
        return invoiceDao.selectOne(wrapper);
    }

    @Override
    public Result updateInvoice(String ids, Integer status) {
        String[] idList = ids.split(",");
        for (String id : idList) {
            Invoice invoice = invoiceDao.selectOne(new QueryWrapper<Invoice>().lambda().eq(Invoice::getInvoiceId, id));
            invoice.setStatus(status);
            if (status == 2) {
                invoice.setInvoicingTime(LocalDateTime.now());

                // 修改订单状态为已开票
                iGiftOrderService.update(new LambdaUpdateWrapper<GiftOrder>()
                        .eq(GiftOrder::getUserId, invoice.getUserId())
                        .eq(GiftOrder::getOrderNo, invoice.getIndentNumber())
                        .set(GiftOrder::getStatus, OrderStatus.INVOICE.code()));
            }
            invoiceDao.update(invoice, new QueryWrapper<Invoice>().eq("invoice_id", id));
        }
        return Result.success();
    }

//    @Override
//    public ExcelData listExcel(Integer userType, Integer invoiceType, String invoiceTitle, String taxpayerNumber, String bankDeposit, String userPhone, String indentId, Integer status, String startTime, String endTime) {
//
//        List<Invoice> invoiceList = invoiceDao.getInvoiceList(userType, invoiceType, invoiceTitle, taxpayerNumber, bankDeposit, userPhone, indentId, status, startTime, endTime);
//        ExcelData data = new ExcelData();
//        data.setName("发票列表");
//        List<String> titles = new ArrayList<>();
//        titles.add("发票Id");
//        titles.add("用户类型");
//        titles.add("发票类型");
//        titles.add("发票金额");
//        titles.add("申请人电子邮件");
//        titles.add("申请人手机号码");
//        titles.add("1已提交 2开票完成 3开票失败");
//        titles.add("开票时间");
//        titles.add("创建时间");
//        titles.add("更新时间");
//        titles.add("申请人id");
//        titles.add("订单号");
//        titles.add("发票标题");
//        titles.add("发票抬头");
//        titles.add("纳税人识别号");
//        titles.add("注册地址");
//        titles.add("注册电话");
//        titles.add("开户行");
//        titles.add("银行卡号");
//        data.setTitles(titles);
//        List<List<Object>> rows = new ArrayList<>();
//        for (Invoice invoice : invoiceList) {
//
//
//            List<Object> row = new ArrayList<>();
//            row.add(invoice.getInvoiceId());
//            if (invoice.getUserType() == 1) {
//                row.add("公司");
//            } else {
//                row.add("个人");
//            }
//            if (invoice.getInvoiceType() == 1) {
//                row.add("电子发票");
//            } else {
//                row.add("纸质发票");
//            }
//
//            row.add(invoice.getInvoiceMoney());
//
//            if (StringUtils.isNotBlank(invoice.getUserEmail())) {
//                row.add(invoice.getUserEmail());
//            } else {
//                row.add("");
//            }
//            if (StringUtils.isNotBlank(invoice.getUserPhone())) {
//                row.add(invoice.getUserPhone());
//            } else {
//                row.add("");
//            }
//            if (invoice.getStatus() == 1) {
//                row.add("待开票");
//            } else if (invoice.getStatus() == 2) {
//                row.add("开票完成");
//            } else if (invoice.getStatus() == 3) {
//                row.add("开票失败");
//            } else {
//                row.add("状态错误");
//            }
//            if (invoice.getInvoicingTime() != null) {
//                row.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getInvoicingTime()));
//            } else {
//                row.add("");
//            }
//
//            row.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getCreateTime()));
//            row.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invoice.getUpdateTime()));
//
//            row.add(invoice.getUserId());
//            row.add(invoice.getIndentNumber());
//            row.add(invoice.getInvoiceTitle());
//            row.add(invoice.getInvoiceHeader());
//            row.add(invoice.getTaxPayerNumber());
//            row.add(invoice.getBankAddress());
//            row.add(invoice.getBankPhone());
//            row.add(invoice.getBankDeposit());
//            row.add(invoice.getBankNumber());
//            rows.add(row);
//        }
//
//        data.setRows(rows);
//        return data;
//    }

    @Override
    public IPage<Invoice> getMyInvoiceList(Integer page, Integer limit, Long userId) {
        Page<Invoice> pages;
        if (page != null && limit != null) {
            pages = new Page<>(page, limit);
        } else {
            pages = new Page<>();
            pages.setSize(-1);
        }
        IPage<Invoice> invoiceList = invoiceDao.getMyInvoiceList(pages, userId);
        for (Invoice record : invoiceList.getRecords()) {
            if (StringUtils.isBlank(record.getBankAddress())) {
                record.setBankAddress("");
            }
            if (StringUtils.isBlank(record.getBankPhone())) {
                record.setBankPhone("");
            }
            if (StringUtils.isBlank(record.getBankDeposit())) {
                record.setBankDeposit("");
            }
            if (StringUtils.isBlank(record.getBankNumber())) {
                record.setBankNumber("");
            }
            if (StringUtils.isBlank(record.getInvoiceContent())) {
                record.setInvoiceContent("");
            }
            if (StringUtils.isBlank(record.getInvoiceTitle())) {
                record.setInvoiceTitle("");
            }
        }
        return invoiceList;
    }

    @Override
    public IPage<GiftOrderVo> getOrderList(Integer page, Integer limit, Long userId, Integer indentType, String indentNumber, String startTime, String endTime) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftOrder::getUserId, userId)
                .eq(StringUtils.isNotBlank(indentNumber), GiftOrder::getOrderNo, indentNumber)
                .eq(GiftOrder::getStatus, OrderStatus.RECEIVED.code())
                .between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime), GiftOrder::getCreateTime, startTime, endTime)
                .orderByDesc(GiftOrder::getCreateTime);

        IPage<GiftOrder> orderPage = iGiftOrderService.page(initPage, queryWrapper);
        List<GiftOrderVo> voList = new ArrayList<>();
        for (GiftOrder giftOrder : orderPage.getRecords()) {
            GiftOrderVo vo = new GiftOrderVo();
            BeanUtils.copyProperties(giftOrder, vo);

            List<GiftOrderProductVo> orderProductList = getOrderProductList(giftOrder.getOrderNo());
            vo.setProductList(orderProductList);

            // 查询发票状态
            LambdaQueryWrapper<Invoice> invoiceLambdaQueryWrapper = new LambdaQueryWrapper<>();
            invoiceLambdaQueryWrapper.eq(Invoice::getIndentNumber, giftOrder.getOrderNo());
            Invoice invoice = this.getOne(invoiceLambdaQueryWrapper);
            if (Objects.nonNull(invoice)) {
                vo.setInvoiceStatus(invoice.getStatus());
            } else {
                vo.setInvoiceStatus(0);
            }

            voList.add(vo);
        }

        IPage<GiftOrderVo> voIPage = PageUtil.copyPage(orderPage, voList);
        return voIPage;
    }

    private List<GiftOrderProductVo> getOrderProductList(String orderNo) {
        List<GiftOrderProduct> giftOrderProductList = iGiftOrderProductService.list(new LambdaQueryWrapper<GiftOrderProduct>().eq(GiftOrderProduct::getOrderNo, orderNo));
        List<GiftOrderProductVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(giftOrderProductList)) {
            for (GiftOrderProduct giftOrderProduct : giftOrderProductList) {
                GiftOrderProductVo giftOrderProductVo = new GiftOrderProductVo();
                GiftProduct giftProduct = iGiftProductService.getById(giftOrderProduct.getProductId());
                if (Objects.nonNull(giftProduct)) {
                    giftOrderProductVo.setGiftProduct(giftProduct);
                }

                GiftProductType giftProductType = iGiftProductTypeService.getById(giftOrderProduct.getTypeId());
                if (Objects.nonNull(giftProductType)) {
                    giftOrderProductVo.setGiftProductType(giftProductType);
                }

                GiftProductSku giftProductSku = iGiftProductSkuService.getById(giftOrderProduct.getSkuId());
                if (Objects.nonNull(giftProductSku)) {
                    giftOrderProductVo.setGiftProductSku(giftProductSku);
                }

                voList.add(giftOrderProductVo);
            }
        }

        return voList;
    }

}
