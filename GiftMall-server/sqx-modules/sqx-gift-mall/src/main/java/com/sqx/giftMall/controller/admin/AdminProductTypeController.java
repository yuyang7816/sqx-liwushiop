package com.sqx.giftMall.controller.admin;

import com.sqx.common.core.domain.Result;
import com.sqx.giftMall.domain.bo.ProductTypeSaveBo;
import com.sqx.giftMall.domain.bo.ProductTypeListBo;
import com.sqx.giftMall.domain.bo.ProductTypeUpdateBo;
import com.sqx.giftMall.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/7/17
 */

@Api(value = "管理端-商品分类", tags = {"管理端-商品分类"})
@RestController
@RequestMapping("admin/productType")
public class AdminProductTypeController {
    @Resource
    private ProductTypeService productTypeService;

    @ApiOperation("商品分类列表")
    @GetMapping("list")
    public Result list(Integer page, Integer limit, ProductTypeListBo productTypeListBo) {
        return productTypeService.list(page, limit, productTypeListBo);
    }

    @ApiOperation("商品分类列表（不分页）")
    @GetMapping("listNoPage")
    public Result listNoPage(ProductTypeListBo productTypeListBo) {
        return productTypeService.listNoPage(productTypeListBo);
    }

    @ApiOperation("商品分类信息")
    @GetMapping("info")
    public Result info(Long id) {
        return productTypeService.info(id);
    }

    @ApiOperation("商品分类保存")
    @PostMapping("save")
    public Result save(@RequestBody ProductTypeSaveBo productTypeSaveBo) {
        return productTypeService.save(productTypeSaveBo);
    }

    @ApiOperation("商品分类修改")
    @PostMapping("update")
    public Result update(@RequestBody ProductTypeUpdateBo productTypeUpdateBo) {
        return productTypeService.update(productTypeUpdateBo);
    }

    @ApiOperation("商品分类删除")
    @GetMapping("delete")
    public Result delete(Long id) {
        return productTypeService.delete(id);
    }
}
