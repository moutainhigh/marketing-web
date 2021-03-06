package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilegeRuleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员权益设置表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
public interface CustomerPrivilegeRuleMapper extends BaseMapper<CustomerPrivilegeRuleEntity> {

    List<CustomerPrivilegeRuleEntity> getByPrivilegeType(@Param("shopId") Long shopId, @Param("privilegeType")int... privilegeType);
}
