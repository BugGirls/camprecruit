package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatMemberStoresDao;
import com.jeefw.model.sys.WechatMemberStores;
import com.jeefw.service.sys.WechatMemberStoresService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/8/13
 */
@Service
public class WechatMemberStoresServiceImpl extends BaseService<WechatMemberStores> implements WechatMemberStoresService {

    private WechatMemberStoresDao wechatMemberStoresDao;

    @Resource
    public void setWechatMemberStoresDao(WechatMemberStoresDao wechatMemberStoresDao) {
        this.dao = wechatMemberStoresDao;
        this.wechatMemberStoresDao = wechatMemberStoresDao;
    }
}
