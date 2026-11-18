package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.entity.giftMall.GiftCart;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.sqx.domain.repository.service.giftMall.IGiftCartService;
import com.sqx.domain.repository.service.giftMall.IGiftProductService;
import com.sqx.domain.repository.service.giftMall.IGiftProductSkuService;
import com.sqx.giftMall.domain.bo.CartSaveBo;
import com.sqx.giftMall.domain.bo.CartUpdateBo;
import com.sqx.giftMall.domain.vo.GiftCartInfoVo;
import com.sqx.giftMall.domain.vo.GiftCartVo;
import com.sqx.giftMall.enums.ProductStatus;
import com.sqx.giftMall.service.CartService;
import com.sqx.giftMall.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private IGiftCartService giftCartService;
    @Resource
    private IGiftProductService giftProductService;
    @Resource
    private IGiftProductSkuService giftProductSkuService;
    @Resource
    private IGiftProductSkuService iGiftProductSkuService;


    @Override
    public Result page(Integer page, Integer limit, Long userId) {
        Page<GiftCart> pages = PageUtil.initPage(page, limit);
        List<GiftCartVo> voList = new ArrayList<>();

        QueryWrapper<GiftCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCart::getUserId, userId);
        IPage<GiftCart> cartPage = giftCartService.page(pages, queryWrapper);

        if (CollectionUtils.isNotEmpty(cartPage.getRecords())) {
            List<GiftCart> cartPageRecords = cartPage.getRecords();
            List<Long> productIds = cartPageRecords.stream().map(GiftCart::getProductId).collect(Collectors.toList());

            // 商品信息
            List<GiftProduct> giftProductList = getProductListByIds(productIds);
            Map<Long, GiftProduct> giftProductMap = giftProductList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

            // 商品sku
            List<GiftProductSku> giftProductSkuList = getProductSkuListByProductIds(productIds);
            Map<Long, GiftProductSku> giftProductSkuMap = giftProductSkuList.stream().collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

            for (GiftCart giftCart : cartPageRecords) {
                GiftCartVo vo = new GiftCartVo();
                BeanUtils.copyProperties(giftCart, vo);

                // 商品信息
                if (giftProductMap.containsKey(giftCart.getProductId())) {
                    vo.setGiftProduct(giftProductMap.get(giftCart.getProductId()));
                }

                // 商品sku
                if (giftProductSkuMap.containsKey(giftCart.getSkuId())) {
                    vo.setGiftProductSku(giftProductSkuMap.get(giftCart.getSkuId()));
                } else {
                    // 商品sku不存在
                    vo.getGiftProduct().setStatus(ProductStatus.OTHERS.code());
                }

                voList.add(vo);
            }
        }

        IPage<GiftCartVo> voIPage = PageUtil.copyPage(cartPage, voList);
        return Result.success(voIPage);
    }

    @Override
    public Result info(Long userId) {
        QueryWrapper<GiftCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCart::getUserId, userId);
        List<GiftCart> giftCartList = giftCartService.list(queryWrapper);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (GiftCart giftCart : giftCartList) {
            GiftProductSku giftProductSku = iGiftProductSkuService.getById(giftCart.getSkuId());
            BigDecimal skuPrice = giftProductSku.getSkuPrice().multiply(new BigDecimal(giftCart.getNumber()));
            totalPrice = totalPrice.add(skuPrice);
        }

        GiftCartInfoVo vo = new GiftCartInfoVo();
        vo.setProductNum(giftCartList.size());
        vo.setTotalPrice(totalPrice);
        return Result.success(vo);
    }

    @Override
    public Result save(CartSaveBo cartSaveBo) {
        QueryWrapper<GiftCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftCart::getUserId, cartSaveBo.getUserId())
                .eq(GiftCart::getProductId, cartSaveBo.getProductId())
                .eq(GiftCart::getSkuId,cartSaveBo.getSkuId());

        GiftCart giftCart = giftCartService.getOne(queryWrapper);
        if (Objects.isNull(giftCart)) {
            giftCart = new GiftCart();
            giftCart.setUserId(cartSaveBo.getUserId());
            giftCart.setProductId(cartSaveBo.getProductId());
            giftCart.setSkuId(cartSaveBo.getSkuId());
            giftCart.setNumber(cartSaveBo.getNum());
            giftCartService.save(giftCart);
        } else {
            giftCart.setNumber(giftCart.getNumber() + cartSaveBo.getNum());
            giftCartService.updateById(giftCart);
        }

        return Result.success();
    }

    @Override
    public Result update(CartUpdateBo cartUpdateBo) {
        if (!checkCartExistById(cartUpdateBo.getId())) {
            throw new SqxException("购物车信息不存在");
        }

        GiftCart giftCart = new GiftCart();
        BeanUtils.copyProperties(cartUpdateBo, giftCart);

        giftCartService.updateById(giftCart);

        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if (!checkCartExistById(id)) {
            throw new SqxException("购物车信息不存在");
        }

        giftCartService.removeById(id);
        return Result.success();
    }

    @Override
    public Result deleteByIds(List<Long> ids) {
        giftCartService.removeBatchByIds(ids);
        return Result.success();
    }

    @Override
    public Result changeNumber(Long id, Integer number) {
        if (!checkCartExistById(id)) {
            throw new SqxException("购物车信息不存在");
        }

        UpdateWrapper<GiftCart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(GiftCart::getId, id)
                .set(GiftCart::getNumber, number);

        giftCartService.update(new GiftCart(), updateWrapper);
        return Result.success();
    }

    /**
     * 校验购物车商品是否存在
     * @param id 购物车id
     * @return
     */
    private boolean checkCartExistById(Long id) {
        QueryWrapper<GiftCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftCart::getId, id);

        return giftCartService.exists(queryWrapper);
    }

    /**
     * 商品sku列表
     * @param productIds 商品id列表
     * @return
     */
    private List<GiftProductSku> getProductSkuListByProductIds(List<Long> productIds) {
        QueryWrapper<GiftProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GiftProductSku::getProductId, productIds);

        return giftProductSkuService.list(queryWrapper);
    }

    /**
     * 商品列表
     * @param productIds 商品id列表
     * @return
     */
    private List<GiftProduct> getProductListByIds(List<Long> productIds) {
        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GiftProduct::getId, productIds);

        return giftProductService.list(queryWrapper);
    }
}
