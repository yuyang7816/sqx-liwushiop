package com.sqx.giftMall.controller.app;

import com.sqx.auth.app.annotation.Login;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.ProductListBo;
import com.sqx.giftMall.enums.ProductStatus;
import com.sqx.giftMall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/17
 */
@Api(value = "用户端-商品", tags = {"用户端-商品"})
@RestController
@RequestMapping("/app/product")
public class AppProductController {
    @Resource
    private ProductService productService;

    @ApiOperation("商品列表")
    @GetMapping("page")
    public Result page(Integer page, Integer limit, ProductListBo productListBo) {
        productListBo.setStatus(ProductStatus.ENABLE.code());
        return productService.page(page, limit, productListBo);
    }

    @ApiOperation("商品信息")
    @GetMapping("info")
    public Result info(Long id){
        return productService.info(id);
    }

    @ApiOperation("好礼推荐列表")
    @GetMapping("listGoodPick")
    public Result listGoodPick(Integer page, Integer limit, ProductListBo productListBo) {
        return productService.listGoodPick(page, limit, productListBo);
    }

    @ApiOperation("特价好礼列表")
    @GetMapping("listSpecialOffer")
    public Result listSpecialOffer(Integer page, Integer limit, ProductListBo productListBo) {
        return productService.listSpecialOffer(page, limit, productListBo);
    }

    @Login
    @ApiOperation("搜索")
    @GetMapping("search")
    public Result search(@RequestAttribute Long userId, String keyword) {
        return productService.search(userId, keyword);
    }
}
