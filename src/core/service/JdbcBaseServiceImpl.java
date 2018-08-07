package core.service;

import java.util.List;
import java.util.Map;
 

import core.dao.JdbcBaseDao;

public class JdbcBaseServiceImpl implements JdbcBaseService {
	private JdbcBaseDao jdbcDao ;
	
	public JdbcBaseDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(JdbcBaseDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	@Override
	public List<Map<String,Object>> getDatabySql(String sql) {
		List<Map<String,Object>> srs = jdbcDao.getDatabySql(sql);
		return srs;
	}

}
