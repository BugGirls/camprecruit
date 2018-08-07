package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.OrderMasterDao;
import com.jeefw.model.sys.OrderMaster;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/18
 */
@Repository
public class OrderMasterDaoImpl extends BaseDao<OrderMaster> implements OrderMasterDao {

    public OrderMasterDaoImpl() {
        super(OrderMaster.class);
    }
}
