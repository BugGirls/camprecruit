package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatUserGetCardDao;
import com.jeefw.model.sys.WechatUserGetCard;
import com.jeefw.service.sys.WechatUserGetCardService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/7/23
 */
@Service
public class WechatUserGetCardServiceImpl extends BaseService<WechatUserGetCard> implements WechatUserGetCardService {

    private WechatUserGetCardDao wechatUserGetCardDao;

    @Resource
    public void setWechatUserGetCardDao(WechatUserGetCardDao wechatUserGetCardDao) {
        this.wechatUserGetCardDao = wechatUserGetCardDao;
        this.dao = wechatUserGetCardDao;
    }
}
