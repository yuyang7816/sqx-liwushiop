package com.sqx.map.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sqx.common.core.utils.HttpClientUtil;
import com.sqx.domain.repository.service.sys.CommonInfoService;
import com.sqx.map.domain.vo.AddressVo;
import com.sqx.map.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengming
 * @since 2025/7/22
 */
@Slf4j
@Service
public class MapServiceImpl implements MapService {

    @Resource
    private CommonInfoService commonInfoService;

    @Override
    public AddressVo getByLatAndLng(String lat, String lng) {
        String key = commonInfoService.getCommonValue(415);
        String url = "http://api.tianditu.gov.cn/geocoder";
        Map<String, String> param = new HashMap<>();
        JSONObject postStr = new JSONObject();
        postStr.put("lon", lng);
        postStr.put("lat", lat);
        postStr.put("ver", "1");
        param.put("postStr", postStr.toJSONString());
        param.put("type", "geocode");
        param.put("tk", key);
        String s = HttpClientUtil.doGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(s);
        log.info("{}", jsonObject);
        String status = jsonObject.getString("status");
        if ("0".equals(status)) {
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject addressComponent = result.getJSONObject("addressComponent");

            String nation = addressComponent.getString("nation");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            String county = addressComponent.getString("county");
            String town = addressComponent.getString("town");
            String road = addressComponent.getString("road");
            String poi = addressComponent.getString("poi");

            AddressVo vo = new AddressVo();
            vo.setCountry(nation);
            vo.setProvince(province);
            vo.setCity(city);
            vo.setDistrict(county);
            vo.setTown(town);
            vo.setRoad(road);
            vo.setAddress(poi);

            return vo;
        }

        return null;
    }
}
