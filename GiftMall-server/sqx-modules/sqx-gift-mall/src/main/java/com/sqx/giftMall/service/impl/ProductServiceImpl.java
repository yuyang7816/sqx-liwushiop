package com.sqx.giftMall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.common.core.exception.SqxException;
import com.sqx.domain.repository.domain.giftMall.bo.ProductListBo;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductAttrDto;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductDto;
import com.sqx.domain.repository.entity.giftMall.*;
import com.sqx.domain.repository.mapper.giftMall.GiftProductMapper;
import com.sqx.domain.repository.service.giftMall.*;
import com.sqx.giftMall.domain.bo.GenSkuBo;
import com.sqx.giftMall.domain.bo.ProductSaveBo;
import com.sqx.giftMall.domain.bo.ProductUpdateBo;
import com.sqx.giftMall.domain.vo.GiftProductVo;
import com.sqx.giftMall.enums.GoodPickEnum;
import com.sqx.giftMall.enums.ProductStatus;
import com.sqx.giftMall.enums.SpecialOfferEnum;
import com.sqx.giftMall.service.ProductService;
import com.sqx.giftMall.utils.PageUtil;
import com.sqx.giftMall.utils.SkuUtil;
import com.sqx.searchHistory.db.service.ISearchHistoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private GiftProductMapper giftProductMapper;
    @Resource
    private IGiftProductService giftProductService;
    @Resource
    private IGiftProductTypeService giftProductTypeService;
    @Resource
    private IGiftSceneService giftSceneService;
    @Resource
    private IGiftTargetService giftTargetService;
    @Resource
    private IGiftProductAttrService giftProductAttrService;
    @Resource
    private IGiftProductSkuService giftProductSkuService;
    @Resource
    private IGiftProductAttrValueService giftProductAttrValueService;
    @Resource
    private ISearchHistoryService iSearchHistoryService;

    @Override
    public Result page(Integer page, Integer limit, ProductListBo productListBo) {
        Page<GiftProduct> pages = PageUtil.initPage(page, limit);

        // 获取商品列表
        IPage<GiftProductDto> iPage = giftProductMapper.page(pages, productListBo);

        // 获取商品分类列表
        List<GiftProductType> productTypeList = giftProductTypeService.list();
        Map<Long, GiftProductType> productTypeMap = productTypeList.stream()
                .collect(HashMap::new, (map, item) -> map.put(item.getId(), item), HashMap::putAll);

        // 填充商品分类信息
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (GiftProductDto giftProduct : iPage.getRecords()) {
                if (productTypeMap.containsKey(giftProduct.getTypeId())) {
                    giftProduct.setGiftProductType(productTypeMap.get(giftProduct.getTypeId()));
                }
            }
        }

        return Result.success(iPage);
    }

    @Override
    public Result info(Long id) {
        GiftProductVo vo = new GiftProductVo();

        // 商品基本信息
        GiftProduct giftProduct = giftProductService.getById(id);
        if (Objects.isNull(giftProduct)) {
            throw new SqxException("商品不存在");
        }
        BeanUtils.copyProperties(giftProduct, vo);

        // 商品分类信息
        GiftProductType giftProductType = giftProductTypeService.getById(giftProduct.getTypeId());
        if (Objects.nonNull(giftProductType)) {
            vo.setGiftProductType(giftProductType);
        }

        // 商品规格
        List<GiftProductAttrDto> giftProductAttrList = giftProductAttrService.findByProductId(giftProduct.getId());
        if (CollectionUtils.isNotEmpty(giftProductAttrList)) {
            vo.setGiftProductAttrList(giftProductAttrList);
        }

        // 商品sku
        List<GiftProductSku> giftProductSkuList = giftProductSkuService.findByProductId(giftProduct.getId());
        if (CollectionUtils.isNotEmpty(giftProductSkuList)) {
            vo.setGiftProductSkuList(giftProductSkuList);
        }

        // 送礼场景信息
        GiftScene giftScene = giftSceneService.getById(giftProduct.getSceneId());
        if (Objects.nonNull(giftScene)) {
            vo.setSceneName(giftScene.getName());
        }

        // 送礼对象信息
        GiftTarget giftTarget = giftTargetService.getById(giftProduct.getTargetId());
        if (Objects.nonNull(giftTarget)) {
            vo.setTargetName(giftTarget.getName());
        }

        return Result.success(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result save(ProductSaveBo productSaveBo) {
        // 保存商品基本信息
        GiftProduct giftProduct = new GiftProduct();
        BeanUtils.copyProperties(productSaveBo, giftProduct);
        giftProduct.setSales(0);
        giftProduct.setStatus(ProductStatus.ENABLE.code());
        giftProductService.save(giftProduct);

        // 保存商品规格
        List<GiftProductAttrDto> giftProductAttrList = productSaveBo.getGiftProductAttrList();
        for (GiftProductAttrDto giftProductAttr : giftProductAttrList) {
            giftProductAttr.setProductId(giftProduct.getId());
            giftProductAttrService.saveBody(giftProductAttr);
        }

        // 保存商品sku
        List<GiftProductSku> giftProductSkuList = productSaveBo.getGiftProductSkuList();
        for (GiftProductSku giftProductSku : giftProductSkuList) {
            giftProductSku.setProductId(giftProduct.getId());
            giftProductSkuService.save(giftProductSku);
        }

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result update(ProductUpdateBo productUpdateBo) {
        if (!checkProductExistsById(productUpdateBo.getId())) {
            throw new SqxException("商品信息不存在");
        }

        // 修改商品规格
        if (CollectionUtils.isNotEmpty(productUpdateBo.getGiftProductAttrList())) {
            giftProductAttrService.updateBody(productUpdateBo.getId(), productUpdateBo.getGiftProductAttrList());
        } else {
            // 修改为单规格, 删除所有attr
            giftProductAttrService.deleteAttrAndValue(productUpdateBo.getId());
        }

        // 修改商品sku
        if (CollectionUtils.isNotEmpty(productUpdateBo.getGiftProductSkuList())) {
            updateProductSku(productUpdateBo.getId(), productUpdateBo.getGiftProductSkuList());
        }

        // 修改商品信息
        GiftProduct giftProduct = new GiftProduct();
        BeanUtils.copyProperties(productUpdateBo, giftProduct);
        giftProductService.updateById(giftProduct);

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Long id) {
        if (!checkProductExistsById(id)) {
            throw new SqxException("商品信息不存在");
        }

        ProductServiceImpl currentProxy = (ProductServiceImpl) AopContext.currentProxy();

        // 删除商品attr-value
        currentProxy.removeProductAttrValue(id);

        // 删除商品attr
        currentProxy.removeProductAttr(id);

        // 删除商品sku
        currentProxy.removeProductSku(id);

        // 删除商品信息
        giftProductService.removeById(id);

        return Result.success();
    }

    @Override
    public Result enable(Long id, Integer status) {
        if (!checkProductExistsById(id)) {
            throw new SqxException("商品信息不存在");
        }

        UpdateWrapper<GiftProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(GiftProduct::getId, id)
                .set(GiftProduct::getStatus, status);

        if (!giftProductService.update(new GiftProduct(), updateWrapper)) {
            throw new SqxException("操作失败");
        }

        return Result.success();
    }

    @Override
    public Result listGoodPick(Integer page, Integer limit, ProductListBo productListBo) {
        Page<GiftProduct> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftProduct::getStatus, ProductStatus.ENABLE.code())
                .eq(GiftProduct::getGoodPick, GoodPickEnum.YES.code())
                .eq(Objects.nonNull(productListBo.getSceneId()), GiftProduct::getSceneId, productListBo.getSceneId())
                .eq(Objects.nonNull(productListBo.getTargetId()), GiftProduct::getTargetId, productListBo.getTargetId())
                .between(Objects.nonNull(productListBo.getStartBudget()) && Objects.nonNull(productListBo.getEndBudget()), GiftProduct::getPrice, productListBo.getStartBudget(), productListBo.getEndBudget());

        IPage<GiftProduct> iPage = giftProductService.page(pages, queryWrapper);
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            iPage.getRecords().sort(Comparator.comparing(GiftProduct::getSales)
                    .thenComparing(GiftProduct::getPrice)
                    .thenComparing(GiftProduct::getCreateTime));
        }

        return Result.success(iPage);
    }

    @Override
    public Result listSpecialOffer(Integer page, Integer limit, ProductListBo productListBo) {
        Page<GiftProduct> pages = PageUtil.initPage(page, limit);

        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GiftProduct::getStatus, ProductStatus.ENABLE.code())
                .eq(GiftProduct::getGoodPick, SpecialOfferEnum.YES.code());

        IPage<GiftProduct> iPage = giftProductService.page(pages, queryWrapper);
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            iPage.getRecords().sort(Comparator.comparing(GiftProduct::getPrice)
                    .thenComparing(GiftProduct::getSales)
                    .thenComparing(GiftProduct::getCreateTime));
        }

        return Result.success(iPage);
    }

    @Override
    public Result search(Long userId, String keyword) {
        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(GiftProduct::getName, keyword)
                .or()
                .like(GiftProduct::getDescription, keyword);
        List<GiftProduct> list = giftProductService.list(queryWrapper);

        // 记录搜索历史
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUserId(userId);
        searchHistory.setValue(keyword);
        iSearchHistoryService.save(searchHistory);

        return Result.success(list);
    }

    @Override
    public Result genSku(GenSkuBo genSkuBo) {
        Map<String, Object> map = new HashMap<>();

        GiftProductAttrDto productAttr = genSkuBo.getProductAttr();
        List<GiftProductAttrValue> attrValues = productAttr.getGiftProductAttrValueList();
        if (CollectionUtils.isEmpty(attrValues)) {
            map.put("header", Collections.emptyList());
            map.put("value", Collections.emptyList());
            return Result.success(map);
        }

        // 构建表头
        List<String> header = new ArrayList<>();
        for (GiftProductAttrValue attr : attrValues) {
            header.add(attr.getValue());
        }

        header.add("图片");
        header.add("原价");
        header.add("售价");
        header.add("库存");

        // 构建规格值矩阵
        List<List<String>> optionsMatrix = new ArrayList<>();
        for (GiftProductAttrValue attr : attrValues) {
            String detail = attr.getDetail();
            if (StringUtils.isBlank(detail)) {
                return Result.failed("规格值不能为空");
            }

            String[] options = detail.split(",");
            optionsMatrix.add(Arrays.asList(options));
        }

        // 计算笛卡尔积（SKU组合）
        List<List<String>> skuCombinations = SkuUtil.cartesian(optionsMatrix);

        // 构建返回行数据
        List<Map<String, Object>> value = new ArrayList<>();
        for (List<String> combination : skuCombinations) {
            Map<String, Object> skuRow = new HashMap<>();

            for (int i = 0; i < combination.size(); i++) {
                skuRow.put("value" + i, combination.get(i));
            }

            String specStr = String.join(",", combination);
            skuRow.put("detailJson", specStr); // "红色,256G"

            skuRow.put("skuImg", genSkuBo.getCoverImg());    // 默认图
            skuRow.put("skuOriginPrice", genSkuBo.getOriginalPrice());    // 默认图
            skuRow.put("skuPrice", genSkuBo.getPrice());     // 默认价
            skuRow.put("stock", 0); // 默认库存0

            value.add(skuRow);
        }

        map.put("header", header);
        map.put("value", value);
        return Result.success(map);
    }

    @Override
    public Result genSingleSku(GenSkuBo genSkuBo) {
        Map<String, Object> map = new HashMap<>();
        List<String> header = new ArrayList<>();

        //表头
        header.add("图片");
        header.add("原价");
        header.add("售价");
        header.add("库存");

        //规格排列组合
        List<Map<String, Object>> value = new ArrayList<>();
        Map<String, Object> sku = new HashMap<>();
        sku.put("detailJson", "");
        sku.put("specs", "");
        sku.put("skuImg", genSkuBo.getCoverImg());
        sku.put("skuOriginPrice", genSkuBo.getOriginalPrice());
        sku.put("skuPrice", genSkuBo.getPrice());
        sku.put("stock", 0); // 默认库存0
        value.add(sku);

        //数据放入返回map
        map.put("header", header);
        map.put("value", value);
        return Result.success(map);
    }

    @Override
    public Result formatAttr(Long productId) {
        List<GiftProductAttrDto> attrList = giftProductAttrService.findByProductId(productId);
        if (CollectionUtils.isEmpty(attrList)) {
            return Result.success(Collections.emptyList());
        }

        List<GiftProductSku> skuList = giftProductSkuService.findByProductId(productId);
        if (CollectionUtils.isEmpty(skuList)) {
            return Result.success(attrList);
        }

        Map<String, Object> map = new HashMap<>();
        List<String> header = new ArrayList<>();
        List<Map<String, Object>> value = new ArrayList<>();

        for (GiftProductAttrDto attr : attrList) {
            for (GiftProductAttrValue attrValue : attr.getGiftProductAttrValueList()) {
                header.add(attrValue.getValue());
            }
        }

        header.add("图片");
        header.add("原价");
        header.add("售价");
        header.add("库存");

        for (GiftProductSku sku : skuList) {
            Map<String, Object> skuMap = new HashMap<>();

            String detailJson = sku.getDetailJson();
            if (StringUtils.isNotBlank(detailJson)) {
                String[] jsonArr= detailJson.split(",");
                for (int i = 0; i < jsonArr.length; i++) {
                    skuMap.put("value" + i, jsonArr[i]);
                }
            }

            skuMap.put("detailJson", detailJson);
            skuMap.put("skuImg", sku.getSkuImg());
            skuMap.put("skuOriginPrice", sku.getSkuOriginPrice());
            skuMap.put("skuPrice", sku.getSkuPrice());
            skuMap.put("stock", sku.getStock());

            value.add(skuMap);
        }

        map.put("header", header);
        map.put("value", value);

        return Result.success(map);
    }

    /**
     * 删除商品attr
     * @param productId 商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeProductSku(Long productId) {
        QueryWrapper<GiftProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductSku::getProductId, productId);

        giftProductSkuService.remove(queryWrapper);
    }

    /**
     * 删除商品attr
     * @param productId 商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeProductAttr(Long productId) {
        QueryWrapper<GiftProductAttr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttr::getProductId, productId);

        giftProductAttrService.remove(queryWrapper);
    }

    /**
     * 删除商品attr-value
     * @param productId 商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeProductAttrValue(Long productId) {
        QueryWrapper<GiftProductAttrValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductAttrValue::getProductId, productId);

        giftProductAttrValueService.remove(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProductSku(Long productId, List<GiftProductSku> giftProductSkuList) {
        // 删除之前的sku
        QueryWrapper<GiftProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProductSku::getProductId, productId);
        giftProductSkuService.remove(queryWrapper);

        // 保存本次sku
        List<GiftProductSku> giftProductSkus = giftProductSkuList.stream().map(item -> {
            item.setProductId(productId);

            return item;
        }).collect(Collectors.toList());

        giftProductSkuService.saveBatch(giftProductSkus);
    }

    /**
     * 校验商品信息是否存在
     * @param id 商品id
     * @return
     */
    private boolean checkProductExistsById(Long id) {
        QueryWrapper<GiftProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GiftProduct::getId, id);

        return giftProductService.exists(queryWrapper);
    }
}
