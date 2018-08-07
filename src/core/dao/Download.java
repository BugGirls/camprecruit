package core.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jeefw.app.bean.BackUpDb;
import com.jeefw.model.sys.DbDetail;
import com.jeefw.model.sys.DbMetaDetail;
 
 
 

public class Download extends Thread {
	 
	private Map<String,Integer> filesjindu = new HashMap<String,Integer>();
	public static Map<Integer,BackUpDb> downloadmap = new HashMap<Integer,BackUpDb>();
	
	/*
	 * Map<String,对象>  
	 * 名字和进度。
	 */
	
	public static List<String> httpurllist ;
	
	private int uid; 
	
	private DbDetail srcDB;
	
	private DbDetail destDB;
	
	public Download(  DbDetail srcDB, DbDetail destDB,  int uid){
		 
		this.srcDB = srcDB;
		this.destDB = destDB;
		this.uid=uid;
	}
	
	
	public void run() {
		 
		List <String> tablenames = new ArrayList<String>();
		
		List <String> tableSysNameInsert = new ArrayList<String>();
		tableSysNameInsert.add("authority");
		tableSysNameInsert.add("department");
		tableSysNameInsert.add("role");
		tableSysNameInsert.add("role_authority");
		tableSysNameInsert.add("role_permission");
		tableSysNameInsert.add("sys_user");
		tableSysNameInsert.add("sysuser_role");
		tableSysNameInsert.add("tzz_user");
		
		List <String> tableSysNameDelete = new ArrayList<String>();
		tableSysNameDelete.add("tzz_user");
		tableSysNameDelete.add("role_authority");
		tableSysNameDelete.add("role_permission");
		tableSysNameDelete.add("sysuser_role");
		tableSysNameDelete.add("role");
		tableSysNameDelete.add("sys_user");
		tableSysNameDelete.add("department");
		tableSysNameDelete.add("authority");
		 
		
		
		tablenames.add("tzz_dictionary");
		tablenames.add("tzz_course");
		tablenames.add("tzz_course_ware");
		tablenames.add("tzz_user_tixiku");
		tablenames.add("tzz_user_tixikudetails");
		tablenames.add("tzz_user_order");
		tablenames.add("tzz_product");
		tablenames.add("tzz_user_favorites");
		tablenames.add("tzz_user_shopping");
		
		List <String> condition = new ArrayList<String>();
		condition.add(" where 1= 1");
		condition.add(" where 1= 1");
		condition.add(" where 1= 1");
		condition.add(" where user_id = %d");
		condition.add(" where user_id = %d");
		condition.add(" where user_id = %d");
		condition.add(" where 1= 1");
		condition.add(" where user_id = %d");
		condition.add(" where user_id = %d");
		
		String sql = "select * from ";

		JdbcConnectDataBase  jdbc = new JdbcConnectDataBase(srcDB.getDbIp(),srcDB.getUserName(),srcDB.getPassword());
    	Connection con = jdbc.getConnect();
		
    	JdbcConnectDataBase  jdbc1 = new JdbcConnectDataBase(destDB.getDbIp(),destDB.getUserName(),destDB.getPassword());
    	Connection con1 = jdbc1.getConnect();
    	List <DbMetaDetail> list  = null;
    	List<String> listsql = null ;
    	int user_id = this.uid;
    	BackUpDb backup = null ;
    	for(int i = 0 ; i < tableSysNameDelete.size(); i++ ){
    		backup = downloadmap.get(user_id);
    		backup.setContent(tableSysNameDelete.get(i) + " 正在删除");
			delRstDataBySql(con1, "delete from " + tableSysNameDelete.get(i));
		}
    	
    	for(int i = 0 ; i < tableSysNameInsert.size(); i++ ){
    		backup = downloadmap.get(user_id);
    		backup.setContent(tableSysNameInsert.get(i) + " 正在下载");
			list  =  getSrcMetaName(con,sql + tableSysNameInsert.get(i) );
			listsql = getSrcDbInsertSql(tableSysNameInsert.get(i), list);
			executeDbBySql(con1, listsql);
		}
    	
//		list  = getSrcMetaName(con,sql + tablenames.get(0)+ String.format(condition.get(0),user_id));
//		listsql = getSrcDbUpdateSql(tablenames.get(0), list,String.format(condition.get(0),user_id));
//		executeDbBySql(con1,listsql);
		for(int i = 1 ; i < tablenames.size(); i++ ){
			list  =  getSrcMetaName(con,sql + tablenames.get(i) + String.format(condition.get(i),user_id));
			listsql = getSrcDbInsertSql(tablenames.get(i), list);
			delRstDataBySql(con1, "delete from " + tablenames.get(i) );
			executeDbBySql(con1, listsql);
			backup = downloadmap.get(user_id);
			if(backup != null){
				backup.setContent(tablenames.get(i) + " 正在更新");
			}
		}
		BackUpDb backUpDb = downloadmap.get(uid);
		if (backUpDb != null){
			backUpDb.setFlage(2);
			downloadmap.get(uid).setFlage(2);
		}
//===========================下载================================================		
		  httpurllist= new ArrayList<>();
		  httpurllist= getlist(uid);
		for (int i = 0; i < httpurllist.size(); i++) {
			String saveFile=new String();
			saveFile=httpurllist.get(i).split("/tzz")[1];
			String path = saveFile.split("/")[saveFile.split("/").length-1];
			String filename=path;
			filesjindu.put(filename, 0); 
			path=saveFile.split(path)[0];
			//System.out.println(path);
			File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", path ));
	         if (!filePath.exists()) {
	           filePath.mkdirs();
	         }
	         //System.out.println(filePath+"\\"+saveFile.split("/")[saveFile.split("/").length-1] );
	         saveFile= filePath+"\\"+saveFile.split("/")[saveFile.split("/").length-1]; 
	         System.out.println(httpurllist.get(i)+"==="+filename);
	         try{
	        	 httpDownload(httpurllist.get(i),saveFile,i,uid,filesjindu,filename);
	         }catch(Exception e){
	        	 e.printStackTrace();
	         }
		}
		
		backUpDb = downloadmap.get(uid);
		if (backUpDb != null){
			backUpDb.setFlage(4);
			downloadmap.get(uid).setFlage(4);
		}
	} 
	
	public static boolean httpDownload(String httpUrl,String saveFile,int num, int uid,Map<String, Integer>filesjindu,String filename){  
       //下载网络文件  
       Long bytesum = 0l;  
       Long byteread = 0l;  
       URL url = null;  
	   try {   
		 try {
			url = new URL(httpUrl.split(filename)[0]+URLEncoder.encode(filename,"UTF-8"));
		 } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    } catch (MalformedURLException e1) {
	        // TODO Auto-generated catch block  
	        e1.printStackTrace();  
	        return false;  
	    }   
	    URLConnection conn = null; 
	    InputStream inStream = null ;
	    FileOutputStream fs  = null ;
	       try { 
	           conn = url.openConnection();  
	           inStream = conn.getInputStream();  
	           fs = new FileOutputStream(saveFile);  
	           //找到要下载的文件
	           HttpURLConnection urlcon=(HttpURLConnection)url.openConnection(); 
	         //获取相应的文件长度
	         Long fileLength=Long.valueOf( urlcon.getContentLength());
	         System.out.println(fileLength);
	           byte[] buffer = new byte[1048576];  
	           while ((byteread =Long.valueOf( inStream.read(buffer))) != -1) {   
	        	   bytesum += byteread; 
	        	   filesjindu.put(filename, Integer.valueOf(bytesum*100/fileLength+""));
	        	   downloadmap.get(uid).getFileProgress().put(filename, Integer.valueOf(bytesum*100/fileLength+""));
//	               downloadmap.put(uid,filesjindu );
	               if(bytesum*100/fileLength==60){
	            	   //System.out.println(filename+"==+"+fileLength+"+="+bytesum*100/fileLength);
	               } 
	               fs.write(buffer, 0, Integer.valueOf(byteread+""));  
	           }  
	           return true;  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	           return false;  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	           return false;  
	       } finally{
    		 try {
    			if(fs != null){
    				fs.close();
    			   }
    			if(inStream != null){
    				inStream.close();
    			   } 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       }
	   }  
	
	
	

	public List<String> getSrcDbInsertSql(String tableName, List<DbMetaDetail> list){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List <String> sqlList = new ArrayList<String>();
	    	DbMetaDetail metaDetail =  list.get(0);
	    	if (metaDetail.getColumnValue().size() > 0 ){
	    		for(int j = 0 ; j < metaDetail.getColumnValue().size(); j ++){
	    			StringBuffer sbColumn = new StringBuffer();
		            StringBuffer sbValue = new StringBuffer();
	    			for(int i = 0 ; i < list.size() ; i++){
	    				DbMetaDetail metaTmp = list.get(i);
	    				if (sbColumn.length() == 0 ){
	    					if (metaTmp.getColumnValue().get(j) == null ){
	    						continue;
	    					}
	    					sbColumn.append(metaTmp.getColumnName());
	    					if (metaTmp.getColumnType1().equals("String")){
	    						sbValue.append("'" + metaTmp.getColumnValue().get(j) + "'");
	    					}else if(metaTmp.getColumnType1().equals("int")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("float")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("double")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("Date")){
	    						Date date = (Date)metaTmp.getColumnValue().get(j);
	    						String dateStr = sdf.format(date);
	    						sbValue.append("'" + dateStr + "'");
	    					}
	    					
	    				}else{
	    					if (metaTmp.getColumnValue().get(j) == null ){
	    						continue;
	    					}
	    					sbColumn.append( " , "+ metaTmp.getColumnName());
	    					sbValue.append(" , ");
	    					if (metaTmp.getColumnType1().equals("String")){
	    						sbValue.append("'" + metaTmp.getColumnValue().get(j) + "'");
	    					}else if(metaTmp.getColumnType1().equals("int")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("float")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("double")){
	    						sbValue.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("Date")){
	    						Date date = (Date)metaTmp.getColumnValue().get(j);
	    						String dateStr = sdf.format(date);
	    						sbValue.append("'" + dateStr + "'");
	    					}
	    				}
	    				
	    			}
	    			String rstSql = "insert into " + tableName + " (" + sbColumn.toString() + " ) values ( " + sbValue.toString() + ");";
	    			sqlList.add(rstSql);
    		}
    	}
		return sqlList;
	}
	
	public List<String> getSrcDbUpdateSql(String tableName, List<DbMetaDetail> list, String condition){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List <String> sqlList = new ArrayList<String>();
	    	DbMetaDetail metaDetail =  list.get(0);
	    	if (metaDetail.getColumnValue().size() > 0 ){
	    		for(int j = 0 ; j < metaDetail.getColumnValue().size(); j ++){
	    			StringBuffer sbColumn = new StringBuffer();
		            
	    			for(int i = 0 ; i < list.size() ; i++){
	    				DbMetaDetail metaTmp = list.get(i);
	    				if (sbColumn.length() == 0 ){
	    					if (metaTmp.getColumnValue().get(j) == null ){
	    						continue;
	    					}
	    					sbColumn.append(metaTmp.getColumnName() + "= " );
	    					if (metaTmp.getColumnType1().equals("String")){
	    						sbColumn.append("'" + metaTmp.getColumnValue().get(j) + "'");
	    					}else if(metaTmp.getColumnType1().equals("int")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("float")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("double")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("Date")){
	    						Date date = (Date)metaTmp.getColumnValue().get(j);
	    						String dateStr = sdf.format(date);
	    						sbColumn.append("'" + dateStr + "'");
	    					}
	    					
	    				}else{
	    					if (metaTmp.getColumnValue().get(j) == null ){
	    						continue;
	    					}
	    					sbColumn.append( " , "+ metaTmp.getColumnName() + " = ");
	    	
	    					if (metaTmp.getColumnType1().equals("String")){
	    						sbColumn.append("'" + metaTmp.getColumnValue().get(j) + "'");
	    					}else if(metaTmp.getColumnType1().equals("int")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("float")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("double")){
	    						sbColumn.append(metaTmp.getColumnValue().get(j));
	    					}else if(metaTmp.getColumnType1().equals("Date")){
	    						Date date = (Date)metaTmp.getColumnValue().get(j);
	    						String dateStr = sdf.format(date);
	    						sbColumn.append("'" + dateStr + "'");
	    					}
	    				}
	    				
	    			}
	    			String rstSql = "update " + tableName + " set " + sbColumn.toString() + "  " + condition ;
	    			
	    			
	    			sqlList.add(rstSql);
    		}
    	}
    
		
		return sqlList;
	}


	public List<DbMetaDetail> getSrcMetaName(Connection con, String sql){
		List <DbMetaDetail> list = new ArrayList<DbMetaDetail>();
    	ResultSet rs = null;
    	PreparedStatement pStemt = null;
        try {
            pStemt = con.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount();   //统计列
            for (int i = 0 ; i < size; i++){
            	DbMetaDetail metaDetail = new DbMetaDetail();
            	metaDetail.setColumnName(rsmd.getColumnName(i + 1));
            	metaDetail.setColumnType(rsmd.getColumnTypeName(i + 1));
            	metaDetail.setColumnValue(new ArrayList<Object>());
            	list.add(metaDetail);
            }
            
            rs = pStemt.executeQuery(sql);
            while(rs.next()){
            	for(int i = 0 ; i < list.size() ; i++){
            		DbMetaDetail metaDetail =  list.get(i);
            		if (metaDetail.getColumnType().equalsIgnoreCase("VARCHAR") ||metaDetail.getColumnType().equalsIgnoreCase("VARCHAR") ){
            			String  value = rs.getString(metaDetail.getColumnName());
            			metaDetail.getColumnValue().add(value);
            			metaDetail.setColumnType1("String");
            		}else	if(metaDetail.getColumnType().equalsIgnoreCase("BIGINT") || metaDetail.getColumnType().equalsIgnoreCase("INT") || metaDetail.getColumnType().equalsIgnoreCase("SMALLINT")||metaDetail.getColumnType().equalsIgnoreCase("BIT")){
            			Integer  value = rs.getInt(metaDetail.getColumnName());
            			metaDetail.getColumnValue().add(value);
            			metaDetail.setColumnType1("int");
            		}else if(metaDetail.getColumnType().equalsIgnoreCase("DATE") ||metaDetail.getColumnType().equalsIgnoreCase("DATETIME") || metaDetail.getColumnType().equalsIgnoreCase("TIMESTAMP")){
            			Date  value = rs.getDate(metaDetail.getColumnName());
            			metaDetail.getColumnValue().add(value);
            			metaDetail.setColumnType1("Date");
            		}else if(metaDetail.getColumnType().equals("FLOAT")){
            			Float  value = rs.getFloat(metaDetail.getColumnName());
            			metaDetail.getColumnValue().add(value);
            			metaDetail.setColumnType1("float");
            		}else if(metaDetail.getColumnType().equals("DOUBLE")){
            			Double  value = rs.getDouble(metaDetail.getColumnName());
            			metaDetail.getColumnValue().add(value);
            			metaDetail.setColumnType1("double");
            		}else{
            			metaDetail.getColumnValue().add(null);
            		}
            	}
            }
        }catch(Exception e){
	        	e.printStackTrace();
        }finally{
        	
        		try {
        			if(pStemt != null){
        				pStemt.close();
        			}
        			if(rs != null){
        				rs.close();
        			}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
      
	    return list;
	}
	/*
	 * 检测用户表中是否存在相关记录
	 */
	public boolean checkdateExist(String sql){
		boolean flage = false;
		JdbcConnectDataBase  jdbc = new JdbcConnectDataBase(destDB.getDbIp(),destDB.getUserName(),destDB.getPassword());
    	Connection con = jdbc.getConnect();
    	ResultSet rs = null;
    	Statement state = null;
    	try{
    		state = con.createStatement();
    		rs = state.executeQuery(sql);
    		flage = rs.next();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			try {
				if (rs != null){
					rs.close();
				}
				if (state != null){
					state.close();
				}
				if (con != null){
					con.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return flage;
	}
	
	/*
	 * 删除数据库中相关的记录
	 */
	public boolean delRstDataBySql(Connection con, String sql){
		System.out.println(sql);
		boolean flage = false;
    	Statement state = null;
    	try{
    		state = con.createStatement();
    		flage = state.execute(sql);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				if (state != null){
					state.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
		return flage;
	}
	
	/*
	 * 根据sql 插入数据库数据。
	 */
	
	public boolean executeDbBySql(Connection con, List<String> sqls){
		boolean flage = false;
    	Statement state = null;
    	try{
    		state = con.createStatement();
    		String sql = "";
    		for(int i = 0 ; i < sqls.size() ; i++){
    			sql = sqls.get(i);
    			System.out.println(sql);
    			flage = state.execute(sql);
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				if (state != null){
					state.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
		return flage;
	}
	 
//	

 	public static void main(String[] args) {
// 		DbDetail destDB = new DbDetail();
//		destDB.setDbIp("localhost");
//		destDB.setUserName("root");
//		destDB.setPassword("123456");
// 		List<String> aList=getlist(1,destDB);
// 		System.out.println(aList.size());
// 		for (int i = 0; i < aList.size(); i++) {
//			System.out.println(aList.get(i));
// 		}
 	}
 	

public List<String> getlist( int uid  ) {
	String httpurl = "http://127.0.0.1:8080/tzz"; 
	List <String> list = new ArrayList<String>();
	try {	
	JdbcConnectDataBase  jdbc1 = new JdbcConnectDataBase(destDB.getDbIp(),destDB.getUserName(),destDB.getPassword());
    	Connection con = jdbc1.getConnect();
		 
    	List<String> aList=new ArrayList<String>();
		Statement state = null;
		
		state= con.createStatement();
		 
		List<String> pdfnums = new ArrayList<String>();
		String sql = "select image, href, pdfnum from tzz_course";
		ResultSet rs = state.executeQuery(sql);
	 	while (rs.next()) {
			list.add(httpurl+rs.getString(1));
	 		aList.add( rs.getString(2));
	 		pdfnums.add(rs.getString(3));
 		}
	 	if(rs!=null){
	 		rs.close();
	 	}
		if(state!=null){
			state.close();
		}
		 
		
		//课程图片 文档
		for (int i = 0; i < aList.size(); i++) { 
			//File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/",""));
		 	//String path=filePath.getAbsolutePath(); 
			String href= ( aList.get(i)).replace("\\", "/");
			System.out.println("文件名"+href);
			int pdfnum = Integer.valueOf( pdfnums.get(i));
			String pdfname=(aList.get(i).split("[.]")[0]);
			for (int j = 0; j < pdfnum ; j++) {
				list.add(httpurl+pdfname+j+".swf");
			} 
		}
	
		List<String> lista=new ArrayList<String>();
		state= con.createStatement();
		sql = "select  vedio_href from tzz_course_ware";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			lista.add(httpurl+rs.getString(1));
		}
		rs.close();
		state.close();
		
		 Set<String> set2=new HashSet<String>();         
		 set2.addAll(lista);//给set填充         
		 lista.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的         
		 lista.addAll(set2); 
		for (int i = 0; i < lista.size(); i++) {
			list.add(lista.get(i));
		}
		
		state= con.createStatement();
		sql = "select image from tzz_user where id="+uid;
		rs = state.executeQuery(sql);
		while (rs.next()) {
			list.add(httpurl+rs.getString(1));
		}
		rs.close();
		state.close();
		
		
		state= con.createStatement();
		sql = "select image,image1,image2,image3 from tzz_product ";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			if(rs.getString(1)!=null&&!rs.getString(1).equals("")){
				list.add(httpurl+rs.getString(1));
			}
			if(rs.getString(2)!=null&&!rs.getString(2).equals("")){
				list.add(httpurl+rs.getString(2));
			}
			if(rs.getString(3)!=null&&!rs.getString(3).equals("")){
				list.add(httpurl+rs.getString(3));
			}
			if(rs.getString(4)!=null&&!rs.getString(4).equals("")){
				list.add(httpurl+rs.getString(4));
			}
			
		}
		rs.close();
		state.close();
		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return list;
				
	} 

}
