package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 门店菜品 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishShopService extends IService<DishShopEntity> {

    /**
     * 获取次卡数据列表
     * @return
     */
    Page<DishShopEntity> queryDishShopList(CardTimeModel mCardTimeModel) throws Exception;

    /**
     * 查询卡项
     * @param mCardTimeModel
     * @return
     * @throws Exception
     */
    DishShopEntity queryDishShopById(CardTimeModel mCardTimeModel)throws Exception;

    /**
     * 添加品项
     * @param mCardTimeModel
     * @return
     * @throws Exception
     */
    DishShopEntity addDishShop(CardTimeModel mCardTimeModel)throws Exception;

    /**
     * 更改卡项
     * @param mCardTimeModel
     * @return
     * @throws Exception
     */
    Boolean modifyDishShop(CardTimeModel mCardTimeModel)throws Exception;

    /**
     * 删除卡项
     * @param dishId
     * @return
     * @throws Exception
     */
    Boolean deleteDishShop(Long dishId) throws Exception;

    /**
     * 获取子菜
     * @return
     * @throws Exception
     */
    List<DishShopEntity> listDishShop(Long dishId)throws Exception;

    /**
     * 查收门店所有品项
     * @param mDishShopEntity
     * @return
     * @throws Exception
     */
    List<DishShopEntity> queryAllDishShop(DishShopEntity mDishShopEntity)throws Exception;

    /**
     * 查收门店所有品项
     * @param mDishShopEntity
     * @return
     * @throws Exception
     */
    Page<DishShopEntity> queryAllDishShop(DishShopEntity mDishShopEntity,int pageNo, int pageSize)throws Exception;
    /**
     * 添加品项
     * @param mDishShopEntity
     * @return
     * @throws Exception
     */
    DishShopEntity addDishShop(DishShopEntity mDishShopEntity)throws Exception;

    /**
     * 新增和更新商品数据
     * @param mDishShopEntity
     * @return
     * @throws Exception
     */
    DishShopEntity addOrUpdateDishShop(DishShopEntity mDishShopEntity)throws Exception;

    /**
     * 修复品项
     * @param mDishShopEntity
     * @return
     * @throws Exception
     */
    boolean modfityDishShop(DishShopEntity mDishShopEntity)throws Exception;

    /**
     * 删除品项
     * @param dishShopId
     * @return
     * @throws Exception
     */
    boolean deleteDishShopData(Long dishShopId)throws Exception;

    /**
     * 根据品项id查询品项信息
     * @param dishShopId
     * @return
     * @throws Exception
     */
    DishShopEntity queryDishShopById(Long dishShopId)throws Exception;
}
