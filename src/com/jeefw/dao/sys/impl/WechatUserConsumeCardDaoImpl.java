package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatUserConsumeCardDao;
import com.jeefw.model.sys.WechatUserConsumeCard;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/23
 */
@Repository
public class WechatUserConsumeCardDaoImpl extends BaseDao<WechatUserConsumeCard> implements WechatUserConsumeCardDao {

    public WechatUserConsumeCardDaoImpl() {
        super(WechatUserConsumeCard.class);
    }

}
