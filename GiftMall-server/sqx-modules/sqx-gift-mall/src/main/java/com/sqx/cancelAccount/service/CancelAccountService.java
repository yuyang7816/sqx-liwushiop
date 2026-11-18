package com.sqx.cancelAccount.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.cancelAccount.domain.bo.CancelAccountInfoBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountPageBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountSaveBo;
import com.sqx.cancelAccount.domain.bo.CancelAccountUpdateBo;
import com.sqx.cancelAccount.domain.vo.CancelAccountVo;

/**
 * 用户注销申请 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-07 09:31:24
 */
public interface CancelAccountService {

    void saveInfo(CancelAccountSaveBo cancelAccountSaveBo);

    IPage<CancelAccountVo> pageInfo(CancelAccountPageBo cancelAccountPageBo);

    CancelAccountVo info(CancelAccountInfoBo cancelAccountInfoBo);

    void updateInfo(CancelAccountUpdateBo cancelAccountUpdateBo);

    void delete(CancelAccountInfoBo cancelAccountInfoBo);

    void cancel(Long id);

    void revoke(Long userId);

    void remove(Long userId);
}
