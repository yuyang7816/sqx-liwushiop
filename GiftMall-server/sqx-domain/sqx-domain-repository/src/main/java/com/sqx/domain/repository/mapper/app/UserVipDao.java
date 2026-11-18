package com.sqx.domain.repository.mapper.app;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqx.domain.repository.entity.app.UserVip;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVipDao extends BaseMapper<UserVip> {

    int updateUserVip();

}
