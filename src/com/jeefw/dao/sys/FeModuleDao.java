package com.jeefw.dao.sys;

import com.jeefw.model.sys.FeModule;
import core.dao.Dao;

import java.util.List;

/**
 * @author Empress
 * @data 2018/7/6
 */
public interface FeModuleDao extends Dao<FeModule> {

    List<FeModule> getByAllianceId(Integer allianceId);
}
