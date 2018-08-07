package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.WeChatMaterialImageDao;
import com.jeefw.model.sys.WechatMaterialImage;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Empress
 * @data 2018/7/8
 */
@Repository
public class WechatMaterialImageDaoImpl extends BaseDao<WechatMaterialImage> implements WeChatMaterialImageDao {

    public WechatMaterialImageDaoImpl() {
        super(WechatMaterialImage.class);
    }
}
