package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.OrderDetailDao;
import com.jeefw.model.sys.OrderDetail;
import com.jeefw.service.sys.OrderDetailSerivce;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/18
 */
@Service
public class OrderDetailServiceImpl extends BaseService<OrderDetail> implements OrderDetailSerivce {

    @Resource
    private OrderDetailDao orderDetailDao;

    @Override
    public List<OrderDetail> getByOrderId(String orderId) {
        List<OrderDetail> orderDetailList = orderDetailDao.queryByProerties("orderId", orderId);
        return orderDetailList;
    }
}


