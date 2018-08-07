package com.jeefw.service.sys;

import com.jeefw.model.sys.OrderMaster;
import core.dto.OrderMasterDTO;
import core.service.Service;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public interface OrderMasterService extends Service<OrderMaster> {

    OrderMasterDTO create(OrderMasterDTO orderMasterDTO) throws Exception;

    OrderMasterDTO getOrderById(String orderId) throws Exception;
}
