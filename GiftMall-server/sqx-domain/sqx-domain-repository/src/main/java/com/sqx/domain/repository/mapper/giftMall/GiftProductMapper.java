package com.sqx.domain.repository.mapper.giftMall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.domain.repository.domain.giftMall.dto.GiftProductDto;
import com.sqx.domain.repository.domain.giftMall.bo.ProductListBo;
import com.sqx.domain.repository.entity.giftMall.GiftProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【gift_product(商品基本信息)】的数据库操作Mapper
* @createDate 2024-07-15 16:45:14
* @Entity com.sqx.domain.repository.entity.giftMall.GiftProduct
*/
public interface GiftProductMapper extends BaseMapper<GiftProduct> {

    IPage<GiftProductDto> page(Page<GiftProduct> pages, @Param("param") ProductListBo productListBo);

    void incrSales(@Param("orderId") Long orderId, @Param("num") Integer num);
}




