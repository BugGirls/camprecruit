package com.jeefw.controller.sys;

import java.io.IOException;  
import java.util.ArrayList; 
import java.util.Date; 
import java.util.List; 

import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
  





 
import net.sf.json.JSONObject;  

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;  
import com.jeefw.model.sys.TzzNews;  
import com.jeefw.service.sys.TzzNewsService;  
import com.jeefw.service.sys.TzzUserService;

import core.util.HtmlUtils;
 

/**
 * 站内消息的控制层
 *  
 */
@Controller
@RequestMapping("/sys/news")
public class NewsController extends JavaEEFrameworkBaseController<TzzNews> implements Constant {

	@Resource
	private TzzNewsService tzzNewsService; 
	@Resource
	private TzzUserService tzzUserService; 
	
	//发送站内消息
	@RequestMapping(value="/sendmsg", method = { RequestMethod.POST, RequestMethod.GET })
	public void sendmsg(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 JSONObject jsonObject=new JSONObject();
		 String title= request.getParameter("title");
		 String names=request.getParameter("users");
		 String content =request.getParameter("content");  
		 
		 List<String> alluserList=new ArrayList<String>();
		 if(names.equals("0")){
			 TzzNews tNews=new TzzNews();
			   
			 tNews.setTitle(title);
			 tNews.setContent(content);
			 tNews.setState("0");
			 tNews.setCreateTime(new Date()); 
			 tNews.setUserId(0);
			 tzzNewsService.persist(tNews);
			  
			 jsonObject.put("v", "已成功发送给所有用户！");
			 writeJSON(response,  jsonObject);
			 return;
		 }
			for (int i = 0; i < names.length(); i++) { 
				if(names.charAt(i)== ',') { 
					String arr[] =names.split(",");
					 for (int j = 0; j < arr.length; j++) {
						 alluserList.add(arr[j]); 
					}
					 break;
				}else if(i==names.length()-1){
					alluserList.add(names);
				} 
			}
			for (int i = 0; i < alluserList.size(); i++) {
				 if(tzzUserService.getByNickname(alluserList.get(i))==null){
					 jsonObject.put("v", "发送失败，输入的有不存在用户！");
					 writeJSON(response,  jsonObject);
					 return;
				 }
			}
			for (int i = 0; i < alluserList.size(); i++) { 
				 TzzNews tNews=new TzzNews(); 
				 tNews.setTitle(title);
				 tNews.setContent(content);
				 tNews.setState("0");
				 tNews.setCreateTime(new Date());  
				 tNews.setUserId(tzzUserService.getByNickname(alluserList.get(i)).getId());
				 tzzNewsService.persist(tNews); 
			}  
		
		 jsonObject.put("v", "发送成功！");
		 writeJSON(response,  jsonObject);
		 
	}
	/**
	 * 搜索
	 * / /sys/news/search
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.POST, RequestMethod.GET })
	public void search(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = "";
		if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){
			name=request.getParameter("name");
		} 
		List<TzzNews> alllist= tzzNewsService.doQueryAll();
		List<TzzNews> nlist = new ArrayList<TzzNews>();
		if(name!=null&&!name.equals("")){
			for (int i = 0; i < alllist.size(); i++) {
				if(alllist.get(i).getTitle().contains(name)){ 
					nlist.add(alllist.get(i));
				}else if(alllist.get(i).getContent().contains(name)){ 
					nlist.add(alllist.get(i));
				}
			}
		} 
		for (int i = 0; i < nlist.size(); i++) {
			nlist.get(i).setContent(HtmlUtils.htmltoText(nlist.get(i).getContent())); 
		}
		JSONObject jsonObject=new JSONObject();  
		if(nlist.size()>0){
			jsonObject.put("sta",1 );
		}else {
			jsonObject.put("sta",0 );
		} 
		jsonObject.put("list",nlist );
		writeJSON(response, jsonObject );
	}
	//分页查询
	@RequestMapping(value = "/getnews", method = { RequestMethod.POST, RequestMethod.GET })
	public void fenye(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String style=request.getParameter("style"); 
	 
		Integer pagenum = Integer.valueOf(request.getParameter("pagenum")); 
 		Integer maxtiaoshu = Integer.valueOf(request.getParameter("maxtiaoshu"));
 		
		List<TzzNews> alllist= tzzNewsService.doQueryAll();
		 
		int newsnum=alllist.size();//总条数
		int totalpage=0;//总页数
		if(newsnum%maxtiaoshu==0)totalpage=newsnum/maxtiaoshu;
		else totalpage=newsnum/maxtiaoshu+1;  
		if(style.equals("+"))
			pagenum+=1;
		if(style.equals("-"))
			pagenum-=1;
		if(style.equals("0"))
			pagenum=1;
		if(style.equals("1"))
			pagenum=totalpage;
		List<TzzNews> list= tzzNewsService.getnewspage(pagenum, maxtiaoshu); 
		for (int i = 0; i < list.size(); i++) { 
			  list.get(i).setContent(HtmlUtils.htmltoText(list.get(i).getContent())); 
		}
		JSONObject jsonObject=new JSONObject(); 
		jsonObject.put("pagenum",pagenum );//页码
		jsonObject.put("totalpage", totalpage); //总页数
		jsonObject.put("newsnum", newsnum); //总条数 
		jsonObject.put("list", list);  
		writeJSON(response, jsonObject );
 
		
	}
	
	/**
	 * 消息详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/showmsg")
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id =request.getParameter("id");
		TzzNews t= tzzNewsService.get(Integer.parseInt(id));
		    
	  
 			JSONObject obj1 = new JSONObject();
 			 int uid=t.getUserId();
		     if(uid==0){ 
		    	 obj1.put("user", "所有人");
		     }else {
				obj1.put("user",tzzUserService.get(uid).getRealname());
			}
 			obj1.put("title", t.getTitle());
 			obj1.put("content", t.getContent());
 			obj1.put("createtime", t.getCreateTime().toString().substring(0, 19));
 			
	 writeJSON(response, obj1);
	}
	// 删除信息
	@RequestMapping("/delmsg")
	public void deleteVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id=request.getParameter("ids");
		String[] idStrings=id.split(","); 
		System.out.println(idStrings.length);
		for (int i = 0; i < idStrings.length; i++) { 
			 tzzNewsService.deleteByPK( Integer.parseInt(idStrings[i]));
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("v", "删除成功！");
		writeJSON(response, jsonObject); 
 
	}
  
}
