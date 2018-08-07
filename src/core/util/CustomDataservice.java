package core.util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomDataservice {
    /**定义一个Connection 用来连接数据库*/  
    private Connection conn=null;  
    /**定义一个int记录更新的记录数量*/  
    int count=0;  
	private ResultSet rs=null;
    private PreparedStatement pstmt=null;
    private Statement stmt=null;
    
	/**
	 * 
	 * 获取自定义数据源
	 * @return
	 */
	public  Connection getConn() {
		Properties props = new Properties();
		String filePath=this.getClass().getClassLoader().getResource("custom_dataService.properties").getPath(); 
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
		} catch (IOException e) {
			System.out.println("读取自定义数据源配置文件出错,配置文件名称：custom_dataService.properties，"+e);
		}
		
		String jdbcdriver=StringHelper.null2String(props.getProperty("jdbcdriver"));
		String jdbcurl=StringHelper.null2String(props.getProperty("jdbcurl"));
		String jdbcusername=StringHelper.null2String(props.getProperty("jdbcusername"));
		String jdbcpassword=StringHelper.null2String(props.getProperty("jdbcpassword"));
		Connection conn = null;		
        try {
        	Class.forName(jdbcdriver);
        	conn = java.sql.DriverManager.getConnection(jdbcurl, jdbcusername, jdbcpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		this.conn = conn;
		return conn;
	}

	/** 
     * 查询数据集
     * @param sql 查询数据集
     * @return 
     */  
	@SuppressWarnings({ "finally", "unchecked" })
	public List queryList(String sql){
		List ls=new ArrayList();
		Map rowData=null;
        try {  
        	conn=getConn();
            pstmt = conn.prepareStatement(sql);  
            /**查询*/  
            rs = pstmt.executeQuery();  
            ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
            int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数  ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
           
            while (rs.next()) {   
            	rowData = new HashMap(); 
                for (int i = 1; i <= columnCount; i++) { 
                	if(rs==null || rs.getObject(i)==null){
                		
                		rowData.put(md.getColumnName(i), "");  
                	}else{
                		rowData.put(md.getColumnLabel(i).toLowerCase(),rs.getObject(i));  
                	}
                }   
                ls.add(rowData);
            }
        } catch (SQLException e) {  
        	String msg = "SQL执行异常！";
        	try {
				throw new Exception(msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }finally{  
        	coles();
           
        }  
        return ls;  
	}
	
	/** 
     * 查询数据集
     * @param sql 查询数据集
     * @return 
     */  
	@SuppressWarnings({ "finally", "unchecked" })
	public String queryStr(String sql){
		String res="";
        try {  
        	conn=getConn();
            pstmt = conn.prepareStatement(sql);  
            /**查询*/  
            rs = pstmt.executeQuery();  
            ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
           
            while (rs.next()) {   
                res=StringHelper.null2String(rs.getObject(1)); 
            }
        } catch (SQLException e) {  
        	String msg = "SQL执行异常！";
        	try {
				throw new Exception(msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }finally{  
        	coles();
           
        }  
        return res;  
	}

    /** 
     * 更新数据 
     * @param sql 更新sql语句 
     * @return 
     */  
    public int update(String sql){  
        try {  
            pstmt = conn.prepareStatement(sql);  
            count=pstmt.executeUpdate();  
        } catch (SQLException e) {  
            e.printStackTrace();  
            System.out.println("执行更新出错了");  
        }  
        return count;  
    } 
    
    
    /**关闭连接*/  
    public boolean coles(){  
        boolean isColes = false;  
        if(rs!=null){  
            try {  
                rs.close();  
                isColes=true;  
            } catch (SQLException e) {  
                isColes=false;  
                e.printStackTrace();  
                System.out.println("关闭结果集发生错误");  
            }  
        }  
        if(pstmt!=null){  
            try {  
                pstmt.close();  
                isColes=true;  
            } catch (SQLException e) {  
                isColes=false;  
                e.printStackTrace();  
                System.out.println("关闭pstmt发生异常");  
            }  
        }  
        if(conn!=null){  
            try{  
                conn.close();  
                isColes=true;  
            }catch (Exception e) {  
                isColes=false;  
                e.printStackTrace();  
                System.out.println("关闭conn发生异常");  
            }  
        }  
        return isColes;  
    }  
}
