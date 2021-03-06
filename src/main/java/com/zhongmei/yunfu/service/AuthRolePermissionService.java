package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.AuthRolePermissionEntity;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface AuthRolePermissionService extends IService<AuthRolePermissionEntity> {

    /**
     * 添加角色权限
     * @return
     * @throws Exception
     */
    Boolean andAuthRolePermission(AuthRolePermissionEntity mAuthRolePermissionEntity)throws Exception;

    /**
     * 初始化角色权限
     * @return
     * @throws Exception
     */
    Boolean insertBatchData(List<AuthRolePermissionEntity> listData) throws Exception;
}
