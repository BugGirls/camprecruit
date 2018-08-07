package com.jeefw.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.DepartmentDao;
import com.jeefw.model.sys.Department;

import core.dao.BaseDao;

/**
 * 部门的数据持久层的实现类
 * @ 
 */
@Repository
public class DepartmentDaoImpl extends BaseDao<Department> implements DepartmentDao {

	public DepartmentDaoImpl() {
		super(Department.class);
	}

	@Override
	public Map<String, String> getDepartmentMap() {
		String sql = "select department_key,department_value from department";
		Query query = this.getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		Map<String,String> result = new HashMap<String,String>();
		for (Object[] obj : list) {
			result.put((String)obj[0],(String)obj[1]);
		}
		return result;
	}

}
