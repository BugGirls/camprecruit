package core.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class JdbcConnectDataBase {
	
	String driver = "com.mysql.jdbc.Driver";
	
	private String dbIp;
	
	private String userName;
	
	private String password;
	
	public JdbcConnectDataBase(String dbIp, String userName, String pwd){
		this.dbIp = dbIp;
		this.userName = userName;
		this.password = pwd;
	}
	
	public Connection  getConnect(){
		String url = "jdbc:mysql://%s:3306/tzz";
		url = String.format(url, dbIp);
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  conn;
	}
	
	public void closeConnect(Connection conn){
		if (conn != null ){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] arg) throws SQLException{
		JdbcConnectDataBase  jdbc = new JdbcConnectDataBase("192.168.1.42","root","123456");
		Connection conn = jdbc.getConnect();
		Statement statement = conn.createStatement();
         // 要执行的SQL语句
         String sql = "select * from sys_user";
         // 结果集
         ResultSet rs = statement.executeQuery(sql);
         String name = null;
         while(rs.next()) {
          // 选择sname这列数据
        	 name = rs.getString("email");
        	 System.out.println(name);
         }
	}

}
