package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.controller.model.CustomerLevelRuleModel;
import com.zhongmei.yunfu.controller.model.CustomerScoreRuleModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchSettingVo;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员等级积分表 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/internal")
public class CustomerLevelRuleController  extends BaseController{

    @Autowired
    CustomerLevelRuleService mCustomerLevelRuleService;
    @Autowired
    CustomerScoreRuleService mCustomerScoreRuleService;
    @Autowired
    CustomerSearchRuleService customerSearchRuleService;
    @Autowired
    AuthUserService authUserService;
    @Autowired
    CommercialCustomSettingsService mCommercialCustomSettingsService;

    @RequestMapping("/customerLevelRule/gotoPage")
    public String gotoPage(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel) {

        Long creatorId = mCustomerLevelRuleModel.getCreatorId();
        Long shopIdenty = mCustomerLevelRuleModel.getShopIdenty();
        Map<String, String> permissionData = authUserService.getAuthPermissionMap(creatorId,shopIdenty);

        if(permissionData.get("CUSTOMER_SETTING") == null || permissionData.get("CUSTOMER_SETTING").equals("")){
            model.addAttribute("customer_setting", 0);
        }else{
            model.addAttribute("customer_setting", 1);
        }
        model.addAttribute("customerSetting", mCustomerLevelRuleModel);
        return "customer_setting_main";
    }

    @RequestMapping("/customerSetting/customerLevelSetting")
    public String customerLevelSetting(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel){

        try {
            List<CustomerLevelRuleEntity> listData = mCustomerLevelRuleService.queryRuleData(mCustomerLevelRuleModel);
            for (CustomerLevelRuleEntity m : listData) {
                if (m.getLevelCode() == 1) {
                    mCustomerLevelRuleModel.setIdYK(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeYK(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreYK(m.getLevelScore());

                } else if (m.getLevelCode() == 2) {
                    mCustomerLevelRuleModel.setIdJK(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeJK(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreJK(m.getLevelScore());

                } else if (m.getLevelCode() == 3) {
                    mCustomerLevelRuleModel.setIdBJ(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeBJ(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreBJ(m.getLevelScore());

                } else if (m.getLevelCode() == 4) {
                    mCustomerLevelRuleModel.setIdHJ(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeHJ(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreHJ(m.getLevelScore());

                } else if (m.getLevelCode() == 5) {
                    mCustomerLevelRuleModel.setIdZS(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeZS(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreZS(m.getLevelScore());

                } else if (m.getLevelCode() == 6) {
                    mCustomerLevelRuleModel.setIdZZ(m.getId());
                    mCustomerLevelRuleModel.setLevelCodeZZ(m.getLevelCode());
                    mCustomerLevelRuleModel.setLevelScoreZZ(m.getLevelScore());
                }

            }
            model.addAttribute("levelRule", mCustomerLevelRuleModel);
            model.addAttribute("successOrfail", mCustomerLevelRuleModel.getSuccessOrfail());
        }catch (Exception e){
            e.printStackTrace();
        }

        return "customer_level_setting";
    }

    @RequestMapping("/customerSetting/searchGroup")
    public String customerGroupSetting(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel){
        //查询会员查询规则
        CustomerSearchRuleEntity customerSearchRuleEntity = customerSearchRuleService.selectByShopId(
                mCustomerLevelRuleModel.getBrandIdenty(),
                mCustomerLevelRuleModel.getShopIdenty());
        if(customerSearchRuleEntity == null){
            customerSearchRuleEntity = new CustomerSearchRuleEntity();
        }
        model.addAttribute("searchRuleEntity", customerSearchRuleEntity);
        model.addAttribute("searchRuleModel", mCustomerLevelRuleModel);

        return "customer_search_group_setting";
    }

    @RequestMapping("/customerSetting/customerPaySetting")
    public String customerPaySetting(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel){
        try {

            CommercailSettingModel mCommercailSettingModel = new CommercailSettingModel();
            mCommercailSettingModel.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCommercailSettingModel.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCommercailSettingModel.setSettingKey("IS_CKECK_PASSWORD_DOPAY");//获取支付密码验证设置
            CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = mCommercialCustomSettingsService.queryByKey(mCommercailSettingModel);
            model.addAttribute("mCommercialCustomSettingsEntity", mCommercialCustomSettingsEntity);

            model.addAttribute("successOrfail", mCustomerLevelRuleModel.getSuccessOrfail());
            model.addAttribute("paySettingModel", mCustomerLevelRuleModel);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "customer_pay_setting";
    }

    @RequestMapping("/customerLevelRule/modify")
    public String modifyCustomerLevelRule(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel) {

        Boolean isSuccess = true;
        try {
            mCustomerLevelRuleModel.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRuleModel.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRuleModel.setUpdatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRuleModel.setUpdatorName(mCustomerLevelRuleModel.getCreatorName());

            isSuccess = mCustomerLevelRuleService.modifyCustomerLevelRule(mCustomerLevelRuleModel);

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        if (isSuccess) {

            mCustomerLevelRuleModel.setSuccessOrfail("success");
        } else {
            mCustomerLevelRuleModel.setSuccessOrfail("fail");
        }

        return String.format("redirect:/internal/customerSetting/customerLevelSetting?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                mCustomerLevelRuleModel.getBrandIdenty(), mCustomerLevelRuleModel.getShopIdenty(), mCustomerLevelRuleModel.getCreatorId(), mCustomerLevelRuleModel.getCreatorName(),mCustomerLevelRuleModel.getSuccessOrfail());

    }

    @Autowired
    CustomerSearchRuleService customerSettingSearchRuleService;

    @RequestMapping("/customer/search/rule/save")
    public String saveSearchSetting(CustomerSearchSettingVo settingVo) {
        //LoginManager.setUser(settingVo);
        boolean isSuccess = customerSettingSearchRuleService.insertEntity(settingVo);
        String successOrfail = "success";
        if(! isSuccess){
            successOrfail = "fail";
        }
        return String.format("redirect:/internal/customerSetting/searchGroup?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                settingVo.getBrandIdenty(), settingVo.getShopIdenty(), settingVo.getCreatorId(), settingVo.getCreatorName(),successOrfail);
    }

}

