package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatMemberStoresDao;
import com.jeefw.model.sys.WechatMemberStores;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/8/13
 */
@Repository
public class WechatMemberStoresDaoImpl extends BaseDao<WechatMemberStores> implements WechatMemberStoresDao {

    public WechatMemberStoresDaoImpl() {
        super(WechatMemberStores.class);
    }
}
