package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WechatQrcodePuttingCardDao;
import com.jeefw.model.sys.WechatQrcodePuttingCard;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/7/13
 */
@Repository
public class WechatQrcodePuttingCardDaoImpl extends BaseDao<WechatQrcodePuttingCard> implements WechatQrcodePuttingCardDao {

    public WechatQrcodePuttingCardDaoImpl() {
        super(WechatQrcodePuttingCard.class);
    }
}
