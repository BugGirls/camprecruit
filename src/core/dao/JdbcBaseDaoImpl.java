package core.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.metamodel.relational.Database;
import org.springframework.jdbc.core.support.JdbcDaoSupport; 

public class JdbcBaseDaoImpl extends JdbcDaoSupport implements JdbcBaseDao {
	
	Database  dateSource; 
	public Database getDateSource() {
		return dateSource;
	}
	public void setDateSource(Database dateSource) {
		this.dateSource = dateSource;
	}
	@Override
	public List<Map<String,Object>> getDatabySql(String sql) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> srs = this.getJdbcTemplate().queryForList(sql);
//		while(srs.next()){
//			System.out.println(srs.getString("id"));
//		}
		return srs;
	}

}
