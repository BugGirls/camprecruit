//package com.jeefw.controller.sys;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.jeefw.core.Constant;
//import com.jeefw.core.JavaEEFrameworkBaseController;
//import com.jeefw.model.sys.DbDetail;
//import com.jeefw.model.sys.Dict;
//import com.jeefw.model.sys.TzzUser; 
//import com.jeefw.service.sys.TzzUserService;
// 
//
//@Controller
//@RequestMapping({"/sys/backupdb"})
//public class TzzBackupDatabase extends JavaEEFrameworkBaseController<Dict>
//implements Constant {
//	
//	@Resource
//	private TzzUserService tzzUserService;
//	
//	
//	
//	@RequestMapping(value = "/backupdb", method = { RequestMethod.POST, RequestMethod.GET })
//	public void getcourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject jsonObj = new JSONObject();
//		try{
//			/*
//			 * 列举一下需要备份的表。
//			 */
//			String userIdStr = request.getParameter("userId");
//			Integer userId = 0 ;
//			if(userIdStr != null && userIdStr.matches("[\\d]+")){
//				userId = Integer.valueOf(userIdStr);
//			}
//			TzzUser tzzUser=tzzUserService.get(userId);
//			if (tzzUser == null){ 
//				jsonObj.put("err", 1) ;
//				jsonObj.put("msg", "要备份的用户信息错误！");
//				
//				writeJSON(response, jsonObj);
//			}
//			
//			String ip =  tzzUser.getIp();
//			String sqlpwd = tzzUser.getSqlpwd();
//			String sqlname = tzzUser.getSqlname();
//			/*
//			 * 存到某个地方。
//			 */
//			if(ip == null || sqlname == null){
//				jsonObj.put("err", 1) ;
//				jsonObj.put("msg", "要备份的用户没有配置数据库信息！");
//				writeJSON(response, jsonObj);
//			}
//			
//			DbDetail srcDB = new DbDetail();
//			srcDB.setDbIp("192.168.1.42");
//			srcDB.setUserName("root");
//			srcDB.setPassword("123456");
//			
//			DbDetail destDB = new DbDetail();
//			destDB.setDbIp(ip);
//			destDB.setUserName(sqlname);
//			destDB.setPassword(sqlpwd);
//			BackupDatabase backup = new BackupDatabase(srcDB,destDB,userId);
//			backup.start();
//			/*
//			 * 备份的时候给出备份状态
//			 */
//			if (BackupDatabase.flageBackup != null){
//				//BackupDatabase.flageBackup.put(userId, 1);
//			}
//			jsonObj.put("err", 0) ;
//			jsonObj.put("flageBackup", BackupDatabase.flageBackup.get(userId));
//			jsonObj.put("msg", "数据库正在备份，请稍候！");
//			writeJSON(response, jsonObj);
//		}catch(Exception e){
//			
//		}
//		 
//	}
//	
//	
//	
//	/*
//	 * 客户端定时的刷新，查看备份是否完成。
//	 */
//	@RequestMapping(value = "/checkBackup", method = { RequestMethod.POST, RequestMethod.GET })
//	public void checkBackup(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject jsonObj = new JSONObject();
//		try{
//			String userIdStr = request.getParameter("userId");
//			Integer userId = 0 ;
//			if(userIdStr != null && userIdStr.matches("[\\d]+")){
//				userId = Integer.valueOf(userIdStr);
//			}
//			Integer flage = 0 ;
//			if (BackupDatabase.flageBackup != null){
//				//Integer flage1 = BackupDatabase.flageBackup.get(userId);
//			//	if (flage1 != null){
//			//		flage = flage1;
//			//	}
//			}
//			jsonObj.put("err", 0) ;
//			jsonObj.put("flageBackup", flage);
//			jsonObj.put("msg", "数据库正在备份，请稍候！");
//			
//			
//		}catch(Exception e){
//			
//		}
//		
//		
//	}
//	 
//
//}
