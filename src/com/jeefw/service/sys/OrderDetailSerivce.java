package com.jeefw.service.sys;

import com.jeefw.model.sys.OrderDetail;
import core.service.Service;

import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public interface OrderDetailSerivce extends Service<OrderDetail> {

    List<OrderDetail> getByOrderId(String orderId);
}
