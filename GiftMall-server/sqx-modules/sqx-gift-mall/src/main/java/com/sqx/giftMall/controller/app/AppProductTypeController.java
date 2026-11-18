package com.sqx.giftMall.controller.app;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.ProductTypeListBo;
import com.sqx.giftMall.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/17
 */

@Api(value = "用户端-商品分类", tags = {"用户端-商品分类"})
@RestController
@RequestMapping("app/productType")
public class AppProductTypeController {
    @Resource
    private ProductTypeService productTypeService;

    @ApiOperation("商品分类列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, ProductTypeListBo productTypeListBo) {
        return productTypeService.list(page, limit, productTypeListBo);
    }
}
