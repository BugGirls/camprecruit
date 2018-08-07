package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatPoiDao;
import com.jeefw.model.sys.WechatPoi;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/24
 */
@Repository
public class WechatPoiDaoImpl extends BaseDao<WechatPoi> implements WechatPoiDao {

    public WechatPoiDaoImpl() {
        super(WechatPoi.class);
    }
}
