package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.ActivitySearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.domain.mapper.PushPlanActivityMapper;
import com.zhongmei.yunfu.service.PushPlanActivityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 活动推送活动方案 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class PushPlanActivityServiceImpl extends ServiceImpl<PushPlanActivityMapper, PushPlanActivityEntity> implements PushPlanActivityService {


    @Override
    public Page<PushPlanActivityEntity> findListPage(Long brandIdentity, Long shopIdentity, Integer planState, String name,Integer sourceType, int pageIdx, int pageSize) {
        PushPlanActivityEntity mPushPlanActivity = new PushPlanActivityEntity();
        mPushPlanActivity.setBrandIdentity(brandIdentity);
        mPushPlanActivity.setShopIdentity(shopIdentity);
        mPushPlanActivity.setPlanState(planState);
        mPushPlanActivity.setStatusFlag(1);
        mPushPlanActivity.setName(name);
        mPushPlanActivity.setSourceType(sourceType);
        Page<PushPlanActivityEntity> listPage = new Page<>(pageIdx, pageSize);
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(mPushPlanActivity);
        eWrapper.setSqlSelect("id,img_url,brand_identity,shop_identity,name,plan_state,begin_time,end_time,scan_number,share_number,status_flag");
        eWrapper.orderBy("server_create_time", false);
        Page<PushPlanActivityEntity> listActivity = selectPage(listPage, eWrapper);
        return listActivity;
    }

    @Override
    public Page<PushPlanActivityEntity> findListPage(ActivitySearchModel searchModel) {
        PushPlanActivityEntity mPushPlanActivity = new PushPlanActivityEntity();
        mPushPlanActivity.setStatusFlag(1);
        mPushPlanActivity.setBrandIdentity(searchModel.getBrandIdenty());
        mPushPlanActivity.setShopIdentity(searchModel.getShopIdenty());
        mPushPlanActivity.setPlanState(searchModel.getPlanState());
        mPushPlanActivity.setName(searchModel.getName());
        Page<PushPlanActivityEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(mPushPlanActivity);
        eWrapper.setSqlSelect("id,img_url,brand_identity,shop_identity,name,plan_state,begin_time,end_time,scan_number,share_number,status_flag");
        Page<PushPlanActivityEntity> roleDOList = selectPage(page, eWrapper);
        return roleDOList;
    }

    @Override
    public PushPlanActivityEntity findActivityById(Long activityId) {
        PushPlanActivityEntity mPushPlanActivity = selectById(activityId);
        return mPushPlanActivity;
    }

    @Override
    public PushPlanActivityEntity findActivityDetailById(Long activityId) {
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.eq("id",activityId);
        eWrapper.setSqlSelect("id,name,plan_desc,img_url,begin_time,end_time");
        return selectOne(eWrapper);
    }

    @Override
    public Boolean addActivity(PushPlanActivityEntity mPushPlanActivity) {
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(mPushPlanActivity);
        Boolean isSuccess = insert(mPushPlanActivity);
        return isSuccess;
    }

    @Override
    public Boolean modifyActivity(PushPlanActivityEntity mPushPlanActivity) {
        Boolean isSuccess = updateById(mPushPlanActivity);
        return isSuccess;
    }

    @Override
    public Boolean updateActivityNumber(Long id, Integer scanNumber, Integer shareNumber) throws Exception {
        PushPlanActivityEntity mPushPlanActivity = new PushPlanActivityEntity();

        if (scanNumber != null) {
            mPushPlanActivity.setScanNumber(scanNumber);
        }

        if (shareNumber != null) {
            EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
            eWrapper.setSqlSelect("id, share_number");
            eWrapper.eq("id", id);
            PushPlanActivityEntity m = selectOne(eWrapper);
            if (m == null) {
                mPushPlanActivity.setShareNumber(1);
            } else {
                mPushPlanActivity.setShareNumber(m.getShareNumber() + 1);
            }

        } else {

        }

        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.eq("id", id);
        Boolean isSuccess = update(mPushPlanActivity, eWrapper);
        return isSuccess;
    }

    @Override
    public Boolean updateActivityState(Long id, Integer planState, Long creatorId,String creatorname) {
        PushPlanActivityEntity mPushPlanActivity = new PushPlanActivityEntity();
        mPushPlanActivity.setPlanState(planState);
        mPushPlanActivity.setUpdatorId(creatorId);
        mPushPlanActivity.setUpdatorName(creatorname);
        if (planState == 1) {
            mPushPlanActivity.setBeginTime(new Date());
        }
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.eq("id", id);
        Boolean isSuccess = update(mPushPlanActivity, eWrapper);
        return isSuccess;
    }

    @Override
    public Boolean deleteActivity(Long activityId) {
        Boolean isSuccess = deleteById(activityId);
        return isSuccess;
    }

    @Override
    public List<PushPlanActivityEntity> queryListBySourceId(Long brandIdenty, Long sourceId) throws Exception {
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.setSqlSelect("id, brand_identity,shop_identity,source_type,source_id");
        eWrapper.eq("brand_identity", brandIdenty);
        eWrapper.eq("source_id", sourceId);
        eWrapper.eq("source_type", 3);
        List<PushPlanActivityEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean modiftyStateBySource(Long brandIdenty, Long sourceId, Integer state) throws Exception {
        PushPlanActivityEntity entity = new PushPlanActivityEntity();
        entity.setPlanState(state);
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.eq("brand_identity", brandIdenty);
        eWrapper.eq("source_id", sourceId);
        eWrapper.eq("source_type", 3);
        Boolean isSuccess = update(entity,eWrapper);
        return isSuccess;
    }

    @Override
    public boolean batchModiftyBySource(PushPlanActivityEntity entity, String ids) throws Exception {
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.in("id", ids);
        Boolean isSuccess = update(entity,eWrapper);
        return isSuccess;
    }

    @Override
    public boolean batchAdd(List<PushPlanActivityEntity> listData) throws Exception {

        boolean isSuccess = insertBatch(listData);
        return isSuccess;
    }

    @Override
    public boolean batchDelete(List<Long> ids) throws Exception {
        Boolean isSuccess = deleteBatchIds(ids);
        return isSuccess;
    }

    @Override
    public boolean batchBySourceId(Long brandIdenty, Long sourceId) throws Exception {
        EntityWrapper<PushPlanActivityEntity> eWrapper = new EntityWrapper<>(new PushPlanActivityEntity());
        eWrapper.eq("source_id", sourceId);
        eWrapper.eq("brand_identy", brandIdenty);
        Boolean isSuccess = delete(eWrapper);
        return isSuccess;
    }

}
