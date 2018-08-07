package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatUserConsumeCardDao;
import com.jeefw.model.sys.WechatUserConsumeCard;
import com.jeefw.service.sys.WechatUserConsumeCardService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/7/23
 */
@Service
public class WechatUserConsumeCardServiceImpl extends BaseService<WechatUserConsumeCard> implements WechatUserConsumeCardService {

    private WechatUserConsumeCardDao wechatUserConsumeCardDao;

    @Resource
    public void setWechatUserConsumeCardDao(WechatUserConsumeCardDao wechatUserConsumeCardDao) {
        this.wechatUserConsumeCardDao = wechatUserConsumeCardDao;
        this.dao = wechatUserConsumeCardDao;
    }
}
