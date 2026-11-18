package com.sqx.map.service;


import com.sqx.map.domain.vo.AddressVo;

/**
 * @author chengming
 * @since 2025/7/22
 */
public interface MapService {
    AddressVo getByLatAndLng(String lat, String lng);
}
