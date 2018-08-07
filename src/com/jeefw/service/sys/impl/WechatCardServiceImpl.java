package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatCardDao;
import com.jeefw.model.sys.WechatCard;
import com.jeefw.service.sys.WechatCardService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/7/11
 */
@Service
public class WechatCardServiceImpl extends BaseService<WechatCard> implements WechatCardService {

    private WechatCardDao wechatCardDao;

    @Resource
    public void setWechatCardDao(WechatCardDao wechatCardDao) {
        this.wechatCardDao = wechatCardDao;
        this.dao = wechatCardDao;
    }

}
