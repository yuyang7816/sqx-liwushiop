package com.sqx.giftMall.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.domain.giftMall.bo.OrderListBo;
import com.sqx.domain.repository.domain.giftMall.dto.GiftOrderProductVo;
import com.sqx.domain.repository.domain.giftMall.dto.OrderDto;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.entity.app.UserMoneyDetails;
import com.sqx.domain.repository.entity.giftMall.*;
import com.sqx.domain.repository.mapper.app.UserMoneyDao;
import com.sqx.domain.repository.mapper.app.UserMoneyDetailsDao;
import com.sqx.domain.repository.mapper.giftMall.GiftOrderMapper;
import com.sqx.domain.repository.service.giftMall.*;
import com.sqx.domain.repository.service.sys.IUserService;
import com.sqx.giftMall.domain.bo.*;
import com.sqx.giftMall.domain.vo.GiftOrderVo;
import com.sqx.giftMall.enums.*;
import com.sqx.giftMall.service.OrderService;
import com.sqx.giftMall.utils.ExpressUtil;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.pay.enums.BusinessType;
import com.sqx.referral.user.service.ReferralUserCommissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author chengming
 * @since 2024/7/19
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private GiftOrderMapper giftOrderMapper;
    @Resource
    private IGiftOrderService giftOrderService;
    @Resource
    private IGiftProductSkuService iGiftProductSkuService;
    @Resource
    private IGiftUserCouponService giftUserCouponService;
    @Resource
    private IGiftProductService giftProductService;
    @Resource
    private IPayDetailsService payDetailsService;
    @Resource
    private IUserService userService;
    @Resource
    private IGiftShareService iGiftShareService;
    @Resource
    private IGiftOrderProductService iGiftOrderProductService;
    @Resource
    private IGiftProductTypeService iGiftProductTypeService;
    @Resource
    private ReferralUserCommissionService referralUserCommissionService;
    @Resource
    private UserMoneyDao userMoneyDao;
    @Resource
    private UserMoneyDetailsDao userMoneyDetailsDao;
    @Resource
    private ExpressUtil expressUtil;
    @Resource
    private IGiftCartService iGiftCartService;

    @Override
    public Result page(Integer page, Integer limit, OrderListBo orderListBo) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        IPage<OrderDto> voIPage = giftOrderMapper.page(initPage, orderListBo);
        if (CollectionUtils.isNotEmpty(voIPage.getRecords())) {
            for (OrderDto orderDto : voIPage.getRecords()) {
                List<GiftOrderProductVo> orderProductList = getOrderProductList(orderDto.getOrderNo());
                orderDto.setProductList(orderProductList);
            }
        }

        return Result.success(voIPage);
    }

    @Override
    public Result selfList(Integer page, Integer limit, Long userId, Integer status) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftOrder::getUserId, userId)
                .eq(GiftOrder::getStatus, status);
        Page<GiftOrder> orderPage = giftOrderService.page(initPage, queryWrapper);
        if (CollectionUtils.isEmpty(orderPage.getRecords())) {
            return Result.success(orderPage);
        }

        List<GiftOrderVo> voList = new ArrayList<>();
        for (GiftOrder giftOrder : orderPage.getRecords()) {
            GiftOrderVo vo = new GiftOrderVo();
            BeanUtils.copyProperties(giftOrder, vo);

            List<GiftOrderProductVo> orderProductList = getOrderProductList(giftOrder.getOrderNo());
            vo.setProductList(orderProductList);

            voList.add(vo);
        }

        IPage<GiftOrderVo> voIPage = PageUtil.copyPage(orderPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result selfRefundList(Integer page, Integer limit, Long userId) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        IPage<OrderDto> voIPage = giftOrderMapper.selfRefundList(initPage, userId);
        return Result.success(voIPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result save(OrderSaveBo orderSaveBo) {
        GiftProduct giftProduct = giftProductService.getById(orderSaveBo.getProductId());
        if (Objects.isNull(giftProduct)) {
            throw new SqxException("商品不存在或已下架");
        }

        OrderServiceImpl currentProxy = (OrderServiceImpl) AopContext.currentProxy();
        String orderNo = getGeneralOrder();
        GiftOrder giftOrder = currentProxy.buildOrderSaveEntity(orderSaveBo, orderNo);
        giftOrderService.save(giftOrder);

        // 订单商品
        GiftOrderProduct giftOrderProduct = new GiftOrderProduct();
        giftOrderProduct.setOrderNo(orderNo);
        giftOrderProduct.setProductId(orderSaveBo.getProductId());
        giftOrderProduct.setNum(orderSaveBo.getNumber());
        giftOrderProduct.setPrice(orderSaveBo.getPrice());
        giftOrderProduct.setSkuId(orderSaveBo.getSkuId());
        giftOrderProduct.setSkuDetailJson(orderSaveBo.getSkuDetailJson());
        giftOrderProduct.setTypeId(orderSaveBo.getTypeId());
        iGiftOrderProductService.save(giftOrderProduct);

        return Result.success(giftOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    public GiftOrder buildOrderSaveEntity(OrderSaveBo orderSaveBo, String orderNo) {
        GiftOrder giftOrder = new GiftOrder();
        BeanUtils.copyProperties(orderSaveBo, giftOrder);

        // 订单号
        giftOrder.setOrderNo(orderNo);
        giftOrder.setStatus(OrderStatus.WAIT_FOR_PAY.code());

        // 判断库存
        Integer orderType = orderSaveBo.getOrderType();
        if (Objects.equals(orderType, OrderType.GENERAL.code())) {
            if (!iGiftProductSkuService.checkStock(orderSaveBo.getSkuId(), orderSaveBo.getNumber())) {
                throw new SqxException("库存不足");
            }

            // 锁定库存
            iGiftProductSkuService.lessStock(orderSaveBo.getSkuId(), orderSaveBo.getNumber());
        }

        //优惠券
        if (orderSaveBo.getUserCouponsId() != null) {
            giftUserCouponService.usedCoupon(orderSaveBo.getUserCouponsId(), UserCouponStatus.USED.code());
        }

        return giftOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveList(OrderSaveListBo orderSaveListBo) {
        GiftOrder giftOrder = new GiftOrder();
        String orderNo = getGeneralOrder();
        giftOrder.setOrderNo(orderNo);
        giftOrder.setUserId(orderSaveListBo.getUserId());
        giftOrder.setDescription(orderSaveListBo.getDescription());
        giftOrder.setPayMoney(orderSaveListBo.getPayMoney());
        giftOrder.setOrderType(orderSaveListBo.getOrderType());
        giftOrder.setUserCouponsId(orderSaveListBo.getUserCouponsId());
        giftOrder.setUserCouponMoney(orderSaveListBo.getUserCouponMoney());
        giftOrder.setCardId(orderSaveListBo.getCardId());
        giftOrder.setCardImg(orderSaveListBo.getCardImg());
        giftOrder.setStatus(OrderStatus.WAIT_FOR_PAY.code());
        giftOrderService.save(giftOrder);

        // 处理订单商品
        if (CollectionUtils.isNotEmpty(orderSaveListBo.getProductInfoList())) {
            List<GiftOrderProduct> giftProductList = new ArrayList<>();
            for (OrderSaveListBo.ProductInfo productInfo : orderSaveListBo.getProductInfoList()) {
                if (!iGiftProductSkuService.checkStock(productInfo.getSkuId(), productInfo.getNum())) {
                    throw new SqxException("库存不足");
                }

                // 锁定库存
                iGiftProductSkuService.lessStock(productInfo.getSkuId(), productInfo.getNum());

                GiftOrderProduct giftOrderProduct = new GiftOrderProduct();
                giftOrderProduct.setOrderNo(orderNo);
                giftOrderProduct.setProductId(productInfo.getProductId());
                giftOrderProduct.setNum(productInfo.getNum());
                giftOrderProduct.setPrice(productInfo.getProductPrice());
                giftOrderProduct.setSkuId(productInfo.getSkuId());
                giftOrderProduct.setSkuDetailJson(productInfo.getSkuDetailJson());
                giftOrderProduct.setTypeId(productInfo.getTypeId());

                giftProductList.add(giftOrderProduct);

                // 清理购物车
                iGiftCartService.removeByProductId(orderSaveListBo.getUserId(), productInfo.getProductId(), productInfo.getSkuId());
            }

            iGiftOrderProductService.saveBatch(giftProductList);
        }

        //优惠券
        if (orderSaveListBo.getUserCouponsId() != null) {
            giftUserCouponService.usedCoupon(orderSaveListBo.getUserCouponsId(), UserCouponStatus.USED.code());
        }

        return Result.success(giftOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result orderCancel(Long orderId) {
        GiftOrder order = giftOrderService.getById(orderId);
        if (Objects.isNull(order)) {
            throw new SqxException("订单不存在");
        }

        if (!Objects.equals(OrderStatus.WAIT_FOR_PAY.code(), order.getStatus())) {
            throw new SqxException("当前订单不可取消");
        }

        // 取消订单
        UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftOrder::getId, orderId)
                .set(GiftOrder::getStatus, OrderStatus.CANCEL.code());

        giftOrderService.update(new GiftOrder(), updateWrapper);

        // 回滚库存
        iGiftProductSkuService.rollbackStock(order.getSkuId(), order.getNumber());

        // 优惠券不支持回滚
        return Result.success();
    }

    @Override
    public Result express(OrderExpressBo orderExpressBo) {
        GiftOrder order = giftOrderService.getById(orderExpressBo.getId());
        if (Objects.isNull(order)) {
            throw new SqxException("订单信息不存在");
        }

        if (!Objects.equals(order.getStatus(), OrderStatus.WAIT_SHIP.code())) {
            throw new SqxException("订单目前不能发货");
        }

        UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftOrder::getId, orderExpressBo.getId())
                .set(GiftOrder::getStatus, OrderStatus.SHIPPED.code())
                .set(GiftOrder::getExpressName, orderExpressBo.getExpressName())
                .set(GiftOrder::getExpressNumber, orderExpressBo.getExpressNumber())
                .set(GiftOrder::getExpressTime, LocalDateTime.now());

        return Result.success(giftOrderService.update(new GiftOrder(), updateWrapper));
    }

    @Override
    public Result obtain(ObtainBo obtainBo) {
        GiftShare giftShare = iGiftShareService.getById(obtainBo.getShareId());
        if (Objects.isNull(giftShare)) {
            throw new SqxException("分享信息不存在");
        }

        if (Objects.equals(2, giftShare.getStatus()) || Objects.equals(3, giftShare.getStatus())) {
            throw new SqxException("礼物已被领取");
        }

        if (Objects.equals(5, giftShare.getStatus())) {
            throw new SqxException("分享链接已失效");
        }

        if (!Objects.equals(1, giftShare.getStatus())) {
            throw new SqxException("礼物异常，不能领取");
        }

        GiftOrder giftOrder = giftOrderService.getByOrderNo(giftShare.getOrderNo());
        if (Objects.isNull(giftOrder)) {
            throw new SqxException("订单信息不存在");
        }

        if (Objects.equals(giftOrder.getStatus(), OrderStatus.WAIT_SHIP.code())) {
            throw new SqxException("礼物已被领取");
        }

        // 修改礼物分享信息
        giftShare.setReceiveUserId(obtainBo.getUserId());
        giftShare.setReceiveTime(LocalDateTime.now());
        giftShare.setStatus(2);
        iGiftShareService.updateById(giftShare);

        UserEntity userEntity = userService.getById(obtainBo.getUserId());

        UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftOrder::getOrderNo, giftShare.getOrderNo())
                .set(GiftOrder::getGiveUserId, obtainBo.getUserId())
                .set(GiftOrder::getGiveUserName, userEntity.getUserName())
                .set(GiftOrder::getStatus, OrderStatus.WAIT_SHIP.code())
                .set(GiftOrder::getConsignee, obtainBo.getConsignee())
                .set(GiftOrder::getPhone, obtainBo.getPhone())
                .set(GiftOrder::getProvinces, obtainBo.getProvince())
                .set(GiftOrder::getCity, obtainBo.getCity())
                .set(GiftOrder::getDistrict, obtainBo.getDistrict())
                .set(GiftOrder::getAddressDetail, obtainBo.getAddressDetail())
                .set(StringUtils.isNotBlank(obtainBo.getBlessing()), GiftOrder::getBlessing, obtainBo.getBlessing());

        return Result.success(giftOrderService.update(new GiftOrder(), updateWrapper));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result refuse(Long id) {
        GiftOrder order = giftOrderService.getById(id);
        if (Objects.isNull(order)) {
            throw new SqxException("订单信息不存在");
        }

//        if (!Objects.equals(order.getStatus(), OrderStatus.GIVEED.code())) {
//            throw new SqxException("目前不能拒绝该礼物");
//        }

//        String paymentWay = getPaymentWay(order.getPayWay());
//        IPaymentStrategy payment = PaymentFactory.getPayment(paymentWay);
//        payment.refund(order.getOrderNo(), order.getPayMoney());

        // 修改订单信息为已拒绝
        UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftOrder::getId, id)
                .set(GiftOrder::getStatus, OrderStatus.WAIT_FOR_GIVE.code());

        return Result.success(giftOrderService.update(new GiftOrder(), updateWrapper));
    }

    private String getPaymentWay(Integer payWay) {
        /**
         * 1app微信 2微信公众号 3微信小程序 4app支付宝 5H5支付宝 6零钱
         */
        if (1 == payWay || 2 == payWay || 3 == payWay) {
            return PaymentWay.WECHAT.code();
        }

        if (4 == payWay || 5 == payWay) {
            return PaymentWay.ALI.code();
        }

        if (6 == payWay) {
            return PaymentWay.LOCAL.code();
        }

        throw new SqxException("不支持的支付方式");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result confirm(Long id) {
        GiftOrder order = giftOrderService.getById(id);
        if (Objects.isNull(order)) {
            throw new SqxException("订单信息不存在");
        }

        if (!Objects.equals(order.getStatus(), OrderStatus.SHIPPED.code())) {
            throw new SqxException("目前不能确认收货");
        }

        // 修改订单信息为已收货
        UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftOrder::getId, id)
                .set(GiftOrder::getStatus, OrderStatus.RECEIVED.code());
        giftOrderService.update(new GiftOrder(), updateWrapper);

        // 增加sku销量
        List<GiftOrderProduct> orderProducts = iGiftOrderProductService.getOrderProductsByOrderId(order.getOrderNo());

        for (GiftOrderProduct orderProduct : orderProducts) {
            iGiftProductSkuService.incrSales(orderProduct.getSkuId(), orderProduct.getNum());

            // 增加商品销量
            giftProductService.incrSales(orderProduct.getProductId(), orderProduct.getNum());
        }

        // 发放佣金
        referralUserCommissionService.saveUserCommission(order.getId());
        return Result.success();
    }

    @Override
    public Result pay(PaymentBo paymentBo) {
        GiftOrder order = giftOrderService.getById(paymentBo.getOrderId());

        // 余额扣除
        UserMoney userMoney = userMoneyDao.selectOne(new LambdaQueryWrapper<UserMoney>().eq(UserMoney::getUserId, order.getUserId()));
        if (Objects.isNull(userMoney)) {
            throw new BizException("用户钱包不存在");
        }
        if (userMoney.getMoney().compareTo(order.getPayMoney()) < 0) {
            throw new BizException("余额不足");
        }
        userMoneyDao.decr(paymentBo.getUserId(), order.getPayMoney());

        // 处理业务
        PayDetails payDetails = new PayDetails();
        payDetails.setUserId(order.getUserId());
        payDetails.setPlatform(5);
        payDetails.setOrderNo(order.getOrderNo());
        payDetails.setAmount(order.getPayMoney());
//        payDetails.setTradeType(paymentBo.getPaymentType());
        payDetails.setBusinessType(BusinessType.ORDER.getCode());
        payDetails.setState(1);
        payDetails.setPayTime(LocalDateTime.now());
        payDetailsService.save(payDetails);

        order.setPayWay(payDetails.getPlatform());
        order.setPayTime(LocalDateTime.now());
        order.setStatus(OrderStatus.WAIT_FOR_GIVE.code());
        giftOrderService.updateById(order);

        UserMoneyDetails userMoneyDetails = new UserMoneyDetails();
        userMoneyDetails.setUserId(payDetails.getUserId());
        userMoneyDetails.setMoney(payDetails.getAmount());
        userMoneyDetails.setTitle("购买礼物");
        userMoneyDetails.setContent("购买礼物花费: " + payDetails.getAmount());
        userMoneyDetails.setType(2);
        userMoneyDetails.setState(2);
        userMoneyDetails.setClassify(UserMoneyDetailsClassify.ORDER.getCode());
        userMoneyDetails.setCreateTime(LocalDateTime.now());
        userMoneyDetailsDao.insert(userMoneyDetails);

        return Result.success();
    }

    @Override
    public void orderNotify(Long orderId, BigDecimal money, String way) {
        // 获取订单详情
        GiftOrder order = giftOrderService.getById(orderId);
        if (Objects.isNull(order)) {
            throw new SqxException("订单信息不存在");
        }

        if (Objects.equals(order.getOrderType(), OrderType.GENERAL.code())) {
            UpdateWrapper<GiftOrder> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .eq(GiftOrder::getId, orderId)
                    .set(GiftOrder::getStatus, OrderStatus.HAVE_PAY.code());

            giftOrderService.update(new GiftOrder(), updateWrapper);
        }
    }

    @Override
    public Result purchased(Integer page, Integer limit, Long userId, Integer status) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        // 获取我的订单列表
        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(userId), GiftOrder::getUserId, userId)
                .eq(Objects.nonNull(status), GiftOrder::getStatus, status)
                .orderByDesc(GiftOrder::getCreateTime);

        Page<GiftOrder> orderPage = giftOrderService.page(initPage, queryWrapper);

        List<GiftOrderVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderPage.getRecords())) {
            for (GiftOrder giftOrder : orderPage.getRecords()) {
                GiftOrderVo vo = new GiftOrderVo();
                BeanUtils.copyProperties(giftOrder, vo);

                List<GiftOrderProductVo> orderProductList = getOrderProductList(giftOrder.getOrderNo());
                vo.setProductList(orderProductList);

                voList.add(vo);
            }
        }

        IPage<GiftOrderVo> voIPage = PageUtil.copyPage(orderPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result received(Integer page, Integer limit, Long userId, Integer status) {
        Page<GiftOrder> initPage = PageUtil.initPage(page, limit);

        // 获取我的订单列表
        QueryWrapper<GiftOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(userId), GiftOrder::getGiveUserId, userId)
                .eq(Objects.nonNull(status), GiftOrder::getStatus, status)
                .orderByDesc(GiftOrder::getCreateTime);

        Page<GiftOrder> orderPage = giftOrderService.page(initPage, queryWrapper);
        List<GiftOrderVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderPage.getRecords())) {
            for (GiftOrder giftOrder : orderPage.getRecords()) {
                GiftOrderVo vo = new GiftOrderVo();
                BeanUtils.copyProperties(giftOrder, vo);

                List<GiftOrderProductVo> orderProductList = getOrderProductList(giftOrder.getOrderNo());
                vo.setProductList(orderProductList);

                UserEntity userEntity = userService.getById(giftOrder.getUserId());
                if (Objects.nonNull(userEntity)) {
                    vo.setShareUserName(userEntity.getUserName());
                } else {
                    vo.setShareUserName("用户已注销");
                }
                voList.add(vo);
            }
        }

        IPage<GiftOrderVo> voIPage = PageUtil.copyPage(orderPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result<GiftOrderVo> info(Long orderId) {
        GiftOrder giftOrder = giftOrderService.getById(orderId);
        if (Objects.isNull(giftOrder)) {
            throw new BizException("订单不存在");
        }

        GiftOrderVo vo = new GiftOrderVo();
        BeanUtils.copyProperties(giftOrder, vo);

        // 订单商品列表
        List<GiftOrderProductVo> giftOrderProductVoList = getOrderProductList(giftOrder.getOrderNo());

        vo.setProductList(giftOrderProductVoList);

        // 领取信息
        GiftShare giftShare = iGiftShareService.getOne(new LambdaQueryWrapper<GiftShare>().eq(GiftShare::getOrderNo, giftOrder.getOrderNo()));
        if (Objects.nonNull(giftShare)) {
            vo.setGiftShare(giftShare);
        }

        return Result.success(vo);
    }

    /**
     * 查询订单商品列表
     *
     * @param orderNo 订单编号
     */
    private List<GiftOrderProductVo> getOrderProductList(String orderNo) {
        List<GiftOrderProduct> giftOrderProductList = iGiftOrderProductService.list(new LambdaQueryWrapper<GiftOrderProduct>().eq(GiftOrderProduct::getOrderNo, orderNo));
        List<GiftOrderProductVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(giftOrderProductList)) {
            for (GiftOrderProduct giftOrderProduct : giftOrderProductList) {
                GiftOrderProductVo giftOrderProductVo = new GiftOrderProductVo();
                BeanUtils.copyProperties(giftOrderProduct, giftOrderProductVo);

                GiftProduct giftProduct = giftProductService.getById(giftOrderProduct.getProductId());
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

    @Override
    public void give(GiveOrderBo giveOrderBo) {
        // 订单信息
        GiftOrder giftOrder = giftOrderService.getById(giveOrderBo.getOrderId());
        if (Objects.isNull(giftOrder)) {
            throw new BizException("订单信息不存在");
        }

        if (!Objects.equals(giftOrder.getUserId(), giveOrderBo.getUserId())) {
            throw new BizException("非法操作");
        }

        if (!Objects.equals(giftOrder.getStatus(), OrderStatus.WAIT_FOR_GIVE.code())) {
            throw new BizException("不允许赠送");
        }

        // 接收人信息
        UserEntity userEntity = userService.getById(giveOrderBo.getReceiverUserId());
        if (Objects.isNull(userEntity)) {
            throw new BizException("接收人信息不存在");
        }

        giftOrder.setGiveUserId(giveOrderBo.getReceiverUserId());
        giftOrder.setGiveUserName(userEntity.getUserName());
        giftOrder.setStatus(OrderStatus.GIVEED.code());

        giftOrderService.updateById(giftOrder);
    }

    @Override
    public GiftShare share(ShareOrderBo shareOrderBo) {
        GiftOrder giftOrder = giftOrderService.getByOrderNo(shareOrderBo.getOrderNo());
        if (Objects.isNull(giftOrder)) {
            throw new BizException("订单信息不存在");
        }

        if (!Objects.equals(giftOrder.getUserId(), shareOrderBo.getUserId())) {
            throw new BizException("非法操作");
        }

        if (!Objects.equals(giftOrder.getStatus(), OrderStatus.WAIT_FOR_GIVE.code())) {
            throw new BizException("不允许赠送");
        }

        giftOrder.setBlessing(shareOrderBo.getBlessing());
//        giftOrder.setStatus(OrderStatus.GIVEED.code());
        giftOrderService.updateById(giftOrder);

        // 查询是否已分享过
        GiftShare oGiftShare = iGiftShareService.getByOrderNo(giftOrder.getOrderNo());
        if (Objects.nonNull(oGiftShare)) {
            return oGiftShare;
        }

        GiftShare giftShare = new GiftShare();
        giftShare.setUserId(shareOrderBo.getUserId());
        giftShare.setOrderNo(shareOrderBo.getOrderNo());
        giftShare.setStatus(1);
        iGiftShareService.save(giftShare);

        return giftShare;
    }

    @Override
    public Result use(UseOrderBo useOrderBo) {
        GiftOrder giftOrder = giftOrderService.getById(useOrderBo.getOrderId());
        if (Objects.isNull(giftOrder)) {
            throw new BizException("订单信息不存在");
        }

        giftOrder.setProvince(useOrderBo.getProvince());
        giftOrder.setCity(useOrderBo.getCity());
        giftOrder.setDistrict(useOrderBo.getDistrict());
        giftOrder.setLng(useOrderBo.getLng());
        giftOrder.setLat(useOrderBo.getLat());
        giftOrder.setAddressDetail(useOrderBo.getAddress());
        giftOrder.setPhone(useOrderBo.getPhone());
        giftOrder.setStatus(OrderStatus.WAIT_SHIP.code());
        giftOrder.setConsignee(useOrderBo.getUserName());

        giftOrderService.updateById(giftOrder);
        return Result.success();
    }

    @Override
    public void bindCard(BingCardBo bingCardBo) {
        GiftOrder giftOrder = giftOrderService.getById(bingCardBo.getOrderId());
        if (Objects.isNull(giftOrder)) {
            throw new SqxException("订单信息不存在");
        }

        if (!Objects.equals(giftOrder.getUserId(), bingCardBo.getUserId())) {
            throw new SqxException("非法操作");
        }

        giftOrder.setCardId(bingCardBo.getCardId());
        giftOrder.setCardImg(bingCardBo.getCardImg());

        giftOrderService.updateById(giftOrder);
    }

    @Override
    public Result updateExpress(OrderExpressBo orderExpressBo) {
        LambdaUpdateWrapper<GiftOrder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GiftOrder::getId, orderExpressBo.getId())
                .set(GiftOrder::getExpressName, orderExpressBo.getExpressName())
                .set(GiftOrder::getExpressNumber, orderExpressBo.getExpressNumber());

        giftOrderService.update(new GiftOrder(), updateWrapper);
        return Result.success();
    }

    @Override
    public JSONObject getLogistics(String expressNumber) {
        return expressUtil.find(expressNumber);
    }

    private PayDetails buildPayDetails(PaymentBo paymentBo, GiftOrder order) {
        PayDetails payDetails = new PayDetails();
        payDetails.setState(0);
        payDetails.setCreateTime(LocalDateTime.now());
        payDetails.setOrderNo(order.getOrderNo());
        payDetails.setUserId(paymentBo.getUserId());
//        payDetails.setMoney(order.getPayMoney());
//        payDetails.setWay(paymentBo.getPaymentWay());
//        payDetails.setBuyType(2);

        return payDetails;
    }

    private boolean checkWxOpenId(Integer paymentType, String openId) {
        return (Objects.equals(paymentType, PayType.WEB.code()) || Objects.equals(paymentType, PayType.JSAPI.code())) && StringUtils.isBlank(openId);
    }

    /**
     * 生成订单编号
     *
     * @return
     */
    private String getGeneralOrder() {
        Date date = new Date();
        String newString = String.format("%0" + 4 + "d", (int) ((Math.random() * 9 + 1) * 1000));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format + newString;
    }
}
