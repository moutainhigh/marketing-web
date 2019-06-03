package com.zhongmei.yunfu.api.internal;

import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.internal.vo.CustomerBalanceReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.service.CustomerStoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/internal/customer/balance")
public class CustomerStoredBalanceInternalApi extends InternalApi {

    @Autowired
    CustomerStoredService customerStoredService;

    /**
     * 充值
     *
     * @return
     */
    @RequestMapping("/recharge")
    public BaseDataModel recharge(@RequestBody CustomerBalanceReq req) throws Exception {
        if (req.getGiveAmout() != null && req.getGiveAmout().compareTo(BigDecimal.ZERO) > 0) {
            //判断当前的赠送金额与服务器的赠送金额是否一致，不一致抛出错误
            //throw new ApiRespStatusException(ApiRespStatus.CUSTOMER_STORED_BALANCE_RECHARGE_GIVE_ERR);
        }
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.recharge(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    /**
     * 消费
     *
     * @return
     */
    @RequestMapping("/expense")
    public BaseDataModel expense(@RequestBody CustomerBalanceReq req) throws Exception {
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.expense(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    /**
     * 退款
     *
     * @param req
     * @return
     */
    @RequestMapping("/refund")
    public BaseDataModel refund(@RequestBody CustomerBalanceReq req) throws Exception {
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.refund(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    private CustomerStoredEntity toCustomerStoredEntity(CustomerBalanceReq req) {
        CustomerStoredEntity customerStored = new CustomerStoredEntity();
        customerStored.setCustomerId(req.getCustomerId());
        customerStored.setTradeAmount(req.getUsefulAmount());
        customerStored.setGiveAmount(req.getGiveAmout());
        customerStored.setTradeId(req.getTradeId());
        customerStored.setPaymentItemId(req.getPaymentItemId());
        customerStored.setShopIdenty(req.getShopId());
        customerStored.setBrandIdenty(req.getBrandId());
        customerStored.setCreatorId(req.getCreatorId());
        customerStored.setCreatorName(req.getCreatorName());
        return customerStored;
    }
}
