package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.ProductListBo;
import com.sqx.giftMall.domain.bo.GenSkuBo;
import com.sqx.giftMall.domain.bo.ProductSaveBo;
import com.sqx.giftMall.domain.bo.ProductUpdateBo;

/**
 * @author chengming
 * @since 2024/7/16
 */
public interface ProductService {
    Result page(Integer page, Integer limit, ProductListBo productListBo);

    Result info(Long id);

    Result save(ProductSaveBo productSaveBo);

    Result update(ProductUpdateBo productUpdateBo);

    Result delete(Long id);

    Result enable(Long id, Integer status);

    Result listGoodPick(Integer page, Integer limit, ProductListBo productListBo);

    Result listSpecialOffer(Integer page, Integer limit, ProductListBo productListBo);

    Result search(Long userId, String keyword);

    Result genSku(GenSkuBo genSkuBo);

    Result genSingleSku(GenSkuBo genSkuBo);

    Result formatAttr(Long productId);
}
