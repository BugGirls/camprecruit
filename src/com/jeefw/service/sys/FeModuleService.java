package com.jeefw.service.sys;

import com.jeefw.model.sys.FeModule;
import core.service.Service;

import java.util.List;

/**
 * @author Empress
 * @data 2018/7/6
 */
public interface FeModuleService extends Service<FeModule> {

    List<FeModule> getByAllianceId(Integer allianceId);
}
