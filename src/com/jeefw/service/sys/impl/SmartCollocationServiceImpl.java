package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.SmartCollocationDao;
import com.jeefw.model.sys.SmartCollocation;
import com.jeefw.service.sys.SmartCollocationService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/9/1
 */
@Service
public class SmartCollocationServiceImpl extends BaseService<SmartCollocation> implements SmartCollocationService {

    private SmartCollocationDao smartCollocationDao;

    @Resource
    public void setSmartCollocationDao(SmartCollocationDao smartCollocationDao) {
        this.dao = smartCollocationDao;
        this.smartCollocationDao = smartCollocationDao;
    }
}
