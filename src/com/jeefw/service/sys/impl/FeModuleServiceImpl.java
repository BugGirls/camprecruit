package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.EmailphoneDao;
import com.jeefw.dao.sys.FeModuleDao;
import com.jeefw.model.sys.FeModule;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.service.sys.EmailphoneService;
import com.jeefw.service.sys.FeModuleService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Empress
 * @data 2018/7/6
 */
@Service
public class FeModuleServiceImpl extends BaseService<FeModule> implements FeModuleService {

    private FeModuleDao feModuleDao;

    @Resource
    public void setFeModuleDao(FeModuleDao feModuleDao) {
        this.feModuleDao = feModuleDao;
        this.dao = feModuleDao;
    }

    @Override
    public List<FeModule> getByAllianceId(Integer allianceId) {
        return feModuleDao.getByAllianceId(allianceId);
    }
}
