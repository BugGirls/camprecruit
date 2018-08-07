package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.OrderDetailDao;
import com.jeefw.model.sys.OrderDetail;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/18
 */
@Repository
public class OrderDetailDaoImpl extends BaseDao<OrderDetail> implements OrderDetailDao {

    public OrderDetailDaoImpl() {
        super(OrderDetail.class);
    }

}
