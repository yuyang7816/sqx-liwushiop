package com.sqx.domain.repository.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.repository.entity.app.UserEntity;

/**
 * 用户
 *
 * @author fang
 * @date 2021/2/27
 */
public interface IUserService extends IService<UserEntity> {

    UserEntity getUserByInvitationCode(String code);

    UserEntity getByOpenId(String openid);
}
