package com.sqx.user.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.common.core.domain.Result;
import com.sqx.domain.repository.domain.giftMall.bo.PayDetailsBO;
import com.sqx.domain.repository.entity.app.UserEntity;
import com.sqx.user.domain.bo.*;
import com.sqx.domain.repository.domain.giftMall.dto.PayDetailsVo;

/**
 * @author chengming
 * @since 2025/4/24
 */
public interface UserService {
    Result<UserEntity> info(Long userId);

    Result<Void> forgetPassword(ForgetPasswordBO forgetPasswordBO);

    Page<UserEntity> list(ListUserBO listUserBO);

    void update(UpdateUserBO updateUserBO);

    void updateMoney(UpdateUserMoneyBO updateUserMoneyBO);

    IPage<PayDetailsVo> payDetails(PayDetailsBO payDetailsBO);
}
