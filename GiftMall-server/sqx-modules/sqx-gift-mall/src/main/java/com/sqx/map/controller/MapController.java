package com.sqx.map.controller;

import com.sqx.common.core.domain.Result;
import com.sqx.map.domain.vo.AddressVo;
import com.sqx.map.service.MapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2025/7/22
 */
@Api("用户端-地图")
@RestController
@RequestMapping("app/map")
public class MapController {

    @Resource
    private MapService mapService;

    @ApiOperation("根据经纬度获取地址信息")
    @GetMapping("getByLatAndLng")
    public Result<AddressVo> getByLatAndLng(String lat, String lng) {
        AddressVo vo = mapService.getByLatAndLng(lat, lng);
        return Result.success(vo);
    }
}
