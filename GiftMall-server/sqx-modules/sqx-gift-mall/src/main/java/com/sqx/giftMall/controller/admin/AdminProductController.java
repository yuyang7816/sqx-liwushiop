package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.ProductListBo;
import com.sqx.giftMall.domain.bo.GenSkuBo;
import com.sqx.giftMall.domain.bo.ProductSaveBo;
import com.sqx.giftMall.domain.bo.ProductUpdateBo;
import com.sqx.giftMall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/16
 */
@Api(value = "管理端-商品", tags = {"管理端-商品"})
@RestController
@RequestMapping("admin/product")
public class AdminProductController {
    @Resource
    private ProductService productService;

    @ApiOperation("商品列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, ProductListBo productListBo) {
        return productService.page(page, limit, productListBo);
    }

    @ApiOperation("商品信息")
    @GetMapping("info")
    public Result info(Long id) {
        return productService.info(id);
    }

    @ApiOperation("商品保存")
    @PostMapping("save")
    public Result save(@RequestBody @Validated ProductSaveBo productSaveBo) {
        return productService.save(productSaveBo);
    }

    @ApiOperation("商品修改")
    @PostMapping("update")
    public Result update(@RequestBody @Validated ProductUpdateBo productUpdateBo) {
        return productService.update(productUpdateBo);
    }

    @ApiOperation("商品删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return productService.delete(id);
    }

    @ApiOperation("上/下架")
    @GetMapping("enable")
    public Result enable(Long id, Integer status) {
        return productService.enable(id, status);
    }

    @ApiOperation("生成sku(多规格)")
    @PostMapping("genSku")
    public Result genSku(@RequestBody GenSkuBo genSkuBo) {
        return productService.genSku(genSkuBo);
    }

    @ApiOperation("生成sku(单规格)")
    @PostMapping("genSingleSku")
    public Result genSingleSku(@RequestBody GenSkuBo genSkuBo) {
        return productService.genSingleSku(genSkuBo);
    }

    @ApiOperation("回显sku")
    @GetMapping("formatAttr")
    public Result formatAttr(Long productId) {
        return productService.formatAttr(productId);
    }
}
