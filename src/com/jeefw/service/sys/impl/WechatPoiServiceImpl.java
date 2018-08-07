package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatPoiDao;
import com.jeefw.model.sys.WechatPoi;
import com.jeefw.service.sys.WechatPoiService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/7/24
 */
@Service
public class WechatPoiServiceImpl extends BaseService<WechatPoi> implements WechatPoiService {

    private WechatPoiDao wechatPoiDao;

    @Resource
    public void setWechatPoiDao(WechatPoiDao wechatPoiDao) {
        this.wechatPoiDao = wechatPoiDao;
        this.dao = wechatPoiDao;
    }
}
