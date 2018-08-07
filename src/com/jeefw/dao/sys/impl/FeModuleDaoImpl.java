package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.FeModuleDao;
import com.jeefw.model.sys.FeModule;
import core.dao.BaseDao;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Empress
 * @data 2018/7/6
 */
@Repository
public class FeModuleDaoImpl extends BaseDao<FeModule> implements FeModuleDao {

    public FeModuleDaoImpl() {
        super(FeModule.class);
    }

    @Override
    public List<FeModule> getByAllianceId(Integer allianceId) {
        Query query = this.getSession().createSQLQuery("select * from fe_module where alliance_id = :allianceId");
        query.setParameter("allianceId", allianceId);
        List<FeModule> result = query.list();
        return result;
    }
}
