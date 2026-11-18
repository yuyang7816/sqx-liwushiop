package com.sqx.domain.repository.service.sys.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.domain.repository.mapper.app.UserDao;
import com.sqx.domain.repository.service.sys.IUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;



/**
 * 用户
 *
 * @author fang
 * @date 2021/2/27
 */

@Service("authUserServiceImpl")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements IUserService {

    @Override
    public UserEntity getUserByInvitationCode(String code) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getInvitationCode, code);

        return this.getOne(queryWrapper);
    }

    @Override
    public UserEntity getByOpenId(String openid) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getOpenId, openid);

        return this.getOne(queryWrapper);
    }
}
