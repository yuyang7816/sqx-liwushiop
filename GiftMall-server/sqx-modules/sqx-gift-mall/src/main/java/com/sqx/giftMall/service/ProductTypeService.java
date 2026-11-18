package com.sqx.giftMall.service;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.ProductTypeSaveBo;
import com.sqx.giftMall.domain.bo.ProductTypeListBo;
import com.sqx.giftMall.domain.bo.ProductTypeUpdateBo;

/**
 * @author chengming
 * @since 2024/7/17
 */
public interface ProductTypeService {
    Result list(Integer page, Integer limit, ProductTypeListBo productTypeListBo);

    Result info(Long id);

    Result save(ProductTypeSaveBo productTypeSaveBo);

    Result update(ProductTypeUpdateBo productTypeUpdateBo);

    Result delete(Long id);

    Result listNoPage(ProductTypeListBo productTypeListBo);
}
