package com.sqx.banner.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.banner.domain.bo.*;
import com.sqx.banner.domain.vo.BannerVo;

/**
 * @author chengming
 * @since 2025/5/16
 */
public interface BannerService {

    void saveInfo(BannerSaveBo bannerSaveBo);

    IPage<BannerVo> pageInfo(BannerPageBo bannerPageBo);

    BannerVo info(BannerInfoBo bannerInfoBo);

    void updateInfo(BannerUpdateBo bannerUpdateBo);

    void delete(BannerInfoBo bannerInfoBo);

    void updateState(BannerUpdateStateBo bannerUpdateStateBo);
}
