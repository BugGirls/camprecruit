package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Dict;
import com.jeefw.service.sys.DictService;

import core.util.CopyUtils; 

import java.io.File;
import java.io.IOException;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 

@Controller
@RequestMapping({"/sys/test"})
public class TestController extends JavaEEFrameworkBaseController<Dict>
  implements Constant
{

  @Resource
  private DictService dictService;
 
 
  /**
   * 所有视频
   * @param request
   * @param response
   * @throws IOException
   */
	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response)throws IOException {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd"); 
		//String oldPath="F:/video";
		String oldPath="D:/video";
		JSONObject jsonObject = new JSONObject();  
		//新
	    File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/video/" + sdf.format(new Date())));
	    if (!filePath.exists()) {
	        filePath.mkdirs();
	    } 
	    String newPath=filePath.getPath();
	    System.out.println("filePath.getPath():"+filePath.getPath());
	    System.out.println("filePath.getName():"+filePath.getName());
	    CopyUtils.moveFolder(oldPath, newPath); 
	    filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/video/"));
	    int num=filePath.getPath().length();
	    
	    List<String> plist=new ArrayList<>();
	    plist=listp(filePath,plist);
	    List<String> nlist =new ArrayList<String>(); 
	    for (int i = 0; i < plist.size(); i++) {  
	    	String a=plist.get(i).substring(num+1,plist.get(i).length() );
	    	a=a.replace('\\', '/'); 
	    	nlist.add(a); 
		}
	    jsonObject.put("plist", nlist);
		writeJSON(response, jsonObject);
	}
	
	/**
	* 查询视频
	* @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping({"/search"})
	public void search(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		String time=request.getParameter("time");
		String name = request.getParameter("name");
		System.out.println(time); 
		JSONObject jsonObject = new JSONObject();  
		//新  
		System.out.println("sas"+time);
	    File filePath=new File (getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/video/"));
	    int num=filePath.getPath().length();
	    if(time!=null&&!time.equals("")){
	    	String[] times =time.split("-");
	    	String ntime ="";
	    	for (int i = 0 ; i <times.length; i++) {
				ntime+=times[i];
			}
	    	System.out.println("time="+ntime);
	    filePath= new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/video/" + ntime));
		 } 
	    List<String> plist= new ArrayList<String>();
	    List<String> nlist =new ArrayList<String>();
	    plist=listp(filePath,plist);
	    for (int i = 0; i < plist.size(); i++) {  
	    	String a=plist.get(i).substring(num+1,plist.get(i).length() );
	    	a=a.replace('\\', '/'); 
	    	nlist.add(a); 
		} 
	  
	    if(name!=null&&!name.equals("")){
	    	name=name.trim();
	    	for (int i = 0; i < nlist.size(); i++) {
				if(!nlist.get(i).contains(name)){
					nlist.remove(i); 
				}
			}
	    }
	    int pages=0;
	    if(nlist.size()%15==0){ 
		    pages=nlist.size()/15;
	    }else {
	    	 pages=nlist.size()/15+1;
		} 
	    int page=1;
	    if(request.getParameter("page")!=null&&!request.getParameter("page").equals("")){
	    	page = Integer.parseInt(request.getParameter("page"));
	    }
	    int endnum=0;
	    if(page*15>nlist.size()){
	    	endnum=nlist.size()-1;
	    }else {
	    	endnum=page*15-1;
		}
	    StringBuffer content=new StringBuffer(); 
	    System.out.println(endnum);
	   // <li><span>"+value+"</span><input  name='del' type='button' style='float:right;'  value='删除' /></li>";
	    for (int i = (page-1)*15; i <= endnum; i++) { 
			content.append("<li><span>"+nlist.get(i)+"</span><input  name='del' type='button' style='float:right;'  value='删除' /></li>");
		} 
	    System.out.println(nlist.size());
	    jsonObject.put("content", content.toString());
	    jsonObject.put("pages", pages);
	    jsonObject.put("plist",nlist);
        writeJSON(response, jsonObject); 
	}  
	
	
	public List<String> listp(File path, List<String> hm) { 
		if (!path.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (path.isFile()) {  
					hm.add(path.getPath()) ;
					System.out.println(path.getPath());
			}else { 
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) { 
					listp(files[i] ,hm); 
					System.out.println(files[i]);
				}
			}
		} 
		return hm;
	}
	

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping({ "/del" })
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		String name =request.getParameter("name");  
		JSONObject jsonObject = new JSONObject();
		File file=new File (getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/video/"+name));
	    if (file.isFile()) {
	    	if(file.delete()) 
	    	  jsonObject.put("a", "删除成功！");
	    	else{
	        	 jsonObject.put("a", "删除成功！");
	        }
        } else{
       	 jsonObject.put("a", "删除成功！");
       }
	    test(request, response);  
	} 
//	/**
//	 * 文档转换
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 */
//	@RequestMapping({"/zhuanhuan"})
//	public void zhuanhuan(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
//		JSONObject jsonObject = new JSONObject();  
//		
//		//SELECT _nextval('mz_order_id');
//		//自增语句
//		  
//			File file=new File (getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/zhuanhuan/"));
//			    
//	       // Runtime.getRuntime().exec("cmd /C  "+file.getPath()+"/test.bat").waitFor();   
//	        //pdf转swf
//			String command = "cmd /C  D:\\tomcat7\\webapps\\tzz\\static\\upload\\zhuanhuan\\test.bat"; 
//			//String command = "cmd /C  F:\\zhuanhuan\\test.bat";  
//	        Process p = Runtime.getRuntime().exec(command);
//	        InputStream in = p.getInputStream();
//	        int c ;
//	        while ((c = in.read()) != -1) { 
//	        }
//	        in.close(); 
//	        try {
//				p.waitFor();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//		   
//	        writeJSON(response, jsonObject);
//		
//	}
	
}