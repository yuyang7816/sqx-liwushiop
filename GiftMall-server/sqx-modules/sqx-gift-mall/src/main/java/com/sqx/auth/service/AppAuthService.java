package com.sqx.auth.service;

import com.sqx.auth.domain.bo.*;
import com.sqx.auth.domain.vo.LoginVO;
import com.sqx.auth.domain.vo.WxLoginVo;
import com.sqx.common.core.domain.Result;

/**
 * @author chengming
 * @since 2025/4/22
 */
public interface AppAuthService {
    Result<LoginVO> phonePasswordLogin(PhonePasswordLoginBO phonePasswordLoginBO);

    Result<LoginVO> phoneCodeLogin(PhoneCodeLoginBO phoneCodeLoginBO);

    Result<LoginVO> phoneRegister(PhoneRegisterBO phoneRegisterBO);

    Result<Void> resetPassword(ResetPasswordBO resetPasswordBO);

    Result<WxLoginVo> wxLogin(String code);

    Result<LoginVO> wxJsRegister(WxJsRegisterBo wxJsRegisterBo);

    Result<Object> decryptPhone(DecryptPhoneBo decryptPhoneBo);

    Result<Void> delete(Long userId);
}
