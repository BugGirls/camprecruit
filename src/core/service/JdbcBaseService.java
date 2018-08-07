package core.service;

import java.util.List;
import java.util.Map;
 

import core.dao.JdbcBaseDao;

/**
 * @ 
 */
public interface JdbcBaseService extends JdbcBaseDao{
	
	public List<Map<String,Object>> getDatabySql(String sql) ;

}
