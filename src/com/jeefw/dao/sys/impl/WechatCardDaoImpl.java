package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatCardDao;
import com.jeefw.model.sys.WechatCard;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/11
 */
@Repository
public class WechatCardDaoImpl extends BaseDao<WechatCard> implements WechatCardDao {

    public WechatCardDaoImpl() {
        super(WechatCard.class);
    }
}
