package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.WechatQrcodePuttingCardDao;
import com.jeefw.model.sys.WechatQrcodePuttingCard;
import com.jeefw.service.sys.WechatQrcodePuttingCardService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/13
 */
@Service
public class WechatQrcodePuttingCardServiceImpl extends BaseService<WechatQrcodePuttingCard> implements WechatQrcodePuttingCardService {

    private WechatQrcodePuttingCardDao wechatQrcodePuttingCardDao;

    @Resource
    public void setWechatQrcodePuttingCardDao(WechatQrcodePuttingCardDao wechatQrcodePuttingCardDao) {
        this.wechatQrcodePuttingCardDao = wechatQrcodePuttingCardDao;
        this.dao = wechatQrcodePuttingCardDao;
    }

//    public List<WechatQrcodePuttingCard> queryWechatQrcodePuttingCardCnList(List<WechatQrcodePuttingCard> wechatQrcodePuttingCardList) {
//
//    }
}
