package com.sqx.domain.repository.service.giftMall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.common.core.exception.BizException;
import com.sqx.domain.repository.entity.giftMall.GiftProductSku;
import com.sqx.domain.repository.mapper.giftMall.GiftProductSkuMapper;
import com.sqx.domain.repository.service.giftMall.IGiftProductSkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【gift_product_sku(商品SKU)】的数据库操作Service实现
* @createDate 2024-07-16 17:46:55
*/
@Service
public class IGiftProductSkuServiceImpl extends ServiceImpl<GiftProductSkuMapper, GiftProductSku>
    implements IGiftProductSkuService {
    @Resource
    private GiftProductSkuMapper giftProductSkuMapper;

    @Override
    public List<GiftProductSku> findByProductId(Long productId) {
        QueryWrapper<GiftProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductSku::getProductId, productId);

        return this.list(queryWrapper);
    }

    /**
     * 校验库存
     * @param skuId skuId
     * @param number 数量
     * @return
     */
    @Override
    public boolean checkStock(Long skuId, Integer number) {
        GiftProductSku giftProductSku = this.getById(skuId);
        if (Objects.isNull(giftProductSku)) {
            throw new BizException("商品sku不存在");
        }

        return giftProductSku.getStock() >= number;
    }

    /**
     * 锁定库存
     * @param skuId skuId
     * @param number 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void lessStock(Long skuId, Integer number) {
        giftProductSkuMapper.lessStock(skuId, number);
    }

    /**
     * 回滚库存
     * @param skuId skuId
     * @param number 数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void rollbackStock(Long skuId, Integer number) {
        giftProductSkuMapper.rollbackStock(skuId, number);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void incrSales(Long skuId, Integer num) {
        giftProductSkuMapper.incrSales(skuId, num);
    }
}




