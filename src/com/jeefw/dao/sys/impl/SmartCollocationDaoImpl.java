package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.SmartCollocationDao;
import com.jeefw.model.sys.SmartCollocation;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/9/1
 */
@Repository
public class SmartCollocationDaoImpl extends BaseDao<SmartCollocation> implements SmartCollocationDao {

    public SmartCollocationDaoImpl() {
        super(SmartCollocation.class);
    }
}
