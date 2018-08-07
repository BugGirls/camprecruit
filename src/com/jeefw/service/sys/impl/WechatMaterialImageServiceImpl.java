package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WeChatMaterialImageDao;
import com.jeefw.model.sys.WechatMaterialImage;
import com.jeefw.service.sys.WechatMaterialImageService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Empress
 * @data 2018/7/8
 */
@Service
public class WechatMaterialImageServiceImpl extends BaseService<WechatMaterialImage> implements WechatMaterialImageService {

    private WeChatMaterialImageDao materialImageDao;

    @Resource
    public void setWechatMaterialImageDao(WeChatMaterialImageDao materialImageDao) {
        this.materialImageDao = materialImageDao;
        this.dao = materialImageDao;
    }

}
