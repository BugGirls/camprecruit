package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatUserGetCardDao;
import com.jeefw.model.sys.WechatUserGetCard;
import com.jeefw.model.sys.param.WechatUserGetCardParameter;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/23
 */
@Repository
public class WechatUserGetCardDaoImpl extends BaseDao<WechatUserGetCard> implements WechatUserGetCardDao {

    public WechatUserGetCardDaoImpl() {
        super(WechatUserGetCard.class);
    }
}
