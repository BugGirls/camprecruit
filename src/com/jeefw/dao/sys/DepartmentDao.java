package com.jeefw.dao.sys;

import java.util.Map;

import com.jeefw.model.sys.Department;

import core.dao.Dao;

/**
 * 部门的数据持久层的接口
 * @ 
 */
public interface DepartmentDao extends Dao<Department> {

	Map<String, String> getDepartmentMap();

}
