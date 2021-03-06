package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.UserSalaryReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
public interface AuthUserMapper extends BaseMapper<AuthUserEntity> {

    @Select("SELECT * FROM auth_user WHERE status_flag = 1 AND account = #{account} AND shop_identy = #{shopId}")
    AuthUserEntity findByAccount(@Param("account") String account, @Param("shopId") Long shopId);


    @Select("SELECT\n" +
            "  ap.code,\n" +
            "  ap.name\n" +
            "FROM\n" +
            "  auth_permission ap\n" +
            "  LEFT JOIN auth_role_permission arp\n" +
            "    ON ap.id = arp.permission_id\n" +
            "  LEFT JOIN auth_user au\n" +
            "    ON arp.role_id = au.role_id\n" +
            "WHERE ap.platform = 1\n" +
            "  AND au.id = #{userId}")
    List<AuthPermissionEntity> getAuthPermissionEntityBy(@Param("userId") Long userId);

    @Select("SELECT\n" +
            "  ap.code,\n" +
            "  ap.name\n" +
            "FROM\n" +
            "  auth_permission ap\n" +
            "  LEFT JOIN auth_role_permission arp\n" +
            "    ON ap.id = arp.permission_id\n" +
            "  LEFT JOIN auth_user au\n" +
            "    ON arp.role_id = au.role_id\n" +
            "WHERE ap.platform = 1\n" +
            "  AND au.id = #{authUserId}" +
            "  AND au.shop_identy = #{shopId}")
    List<AuthPermissionEntity> getAuthPermissionEntity(@Param("authUserId") Long authUserId, @Param("shopId") Long shopId);

//    @Select("SELECT tu.`user_name` as userName , sum(ti.`actual_amount`) as amount, count(ti.`id`)  as count FROM  `trade_user`  tu LEFT JOIN `trade_item` ti on tu.`trade_item_id` = ti.`id` LEFT JOIN `trade` t on ti.`trade_id` = t.`id` \n" +
//            "${ew.sqlSegment} \n" +
//            "GROUP BY tu.`user_id` \n" +
//            "ORDER BY sum(ti.`actual_amount`) desc; ")

    @Select("SELECT sum(a.amount) as amount ,a.name as userName, count(1) as count from(\n" +
            "SELECT t.`trade_amount`as amount,  t.`trade_time` as time , tu.`user_name` as name,tu.`user_id` as userId, t.`business_type` as type  FROM `trade_user` tu LEFT JOIN `trade` t on t.`id` = tu.`trade_id` \n" +
            "WHERE tu.`trade_item_id` IS NULL ${ew.sqlSegment}\n" +
            "union\n" +
            "SELECT ti.`actual_amount` as amount, ti.`server_create_time`as time, tu.`user_name` as name , tu.`user_id`as userId, t.`business_type` as type    FROM `trade_user` tu LEFT JOIN `trade_item` ti on tu.`trade_item_id` = ti.`id` LEFT JOIN `trade` t on ti.`trade_id` = t.`id` \n" +
            "WHERE tu.`trade_item_id` IS NOT NULL ${ew.sqlSegment} \n" +
            "ORDER BY time desc) a\n" +
            "GROUP BY a.userId\n" +
            "ORDER BY amount desc;")
    List<UserSalaryReport> querUserSaleryReport(@Param("ew") Condition wrapper);

//    @Select("SELECT t.business_type as businessType,tu.`user_name` as userName , ti.`actual_amount`  as amount, ti.`dish_name` as dishName, ti.`server_create_time` as tradeDate FROM  `trade_user`  tu LEFT JOIN `trade_item` ti on tu.`trade_item_id` = ti.`id` LEFT JOIN `trade` t on ti.`trade_id` = t.`id`  \n" +
//            "${ew.sqlSegment} \n" +
//            "ORDER BY ti.`server_create_time` desc; ")

    @Select("SELECT a.type as businessType,a.name as userName,a.amount as amount,a.dishName as dishName,a.time as tradeDate\n" +
            "from (\n" +
            "SELECT t.`trade_amount`as amount,  t.`trade_time` as time , tu.`user_name` as name,tu.`user_id` as userId, t.`business_type` as type , '--整单服务--' as dishName\n" +
            "FROM `trade_user` tu LEFT JOIN `trade` t on t.`id` = tu.`trade_id` \n" +
            "WHERE tu.`trade_item_id` IS NULL ${ew.sqlSegment} \n" +
            "union\n" +
            "SELECT ti.`actual_amount` as amount, ti.`server_create_time`as time, tu.`user_name` as name , tu.`user_id`as userId, t.`business_type` as type,ti.`dish_name` as dishName\n" +
            "FROM `trade_user` tu LEFT JOIN `trade_item` ti on tu.`trade_item_id` = ti.`id` LEFT JOIN `trade` t on ti.`trade_id` = t.`id` \n" +
            "WHERE tu.`trade_item_id` IS NOT NULL ${ew.sqlSegment} \n" +
            "ORDER BY time desc ) a")
    List<UserSalaryReport> querUserSaleryDetailReport(@Param("ew") Condition wrapper);
}
