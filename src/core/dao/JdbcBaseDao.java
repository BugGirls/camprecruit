package core.dao;
 

import java.util.List;
import java.util.Map;
 

/**
 * @ 
 */
public interface JdbcBaseDao {
	public List<Map<String,Object>> getDatabySql(String sql); 
}
