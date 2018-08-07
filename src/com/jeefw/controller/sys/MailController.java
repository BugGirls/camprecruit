package com.jeefw.controller.sys;
 
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
  



import net.sf.json.JSONObject;
  



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.Emailphone; 
import com.jeefw.model.sys.Mail;
import com.jeefw.model.sys.SysUser;   
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.EmailphoneService;
import com.jeefw.service.sys.MailService;
  


import com.jeefw.service.sys.TzzUserService;

import email.SendMail;



/**
 * 邮件的控制层 
 */
@Controller
@RequestMapping("/sys/email")
public class MailController extends JavaEEFrameworkBaseController<Mail> implements Constant {

	@Resource
	private MailService mailService; 
	
	@Resource
	private TzzUserService tzzUserService; 
	@Resource
	private EmailphoneService emailphoneService;
	
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

  
	/**
	 * 删除邮件/sys/mail/delmail
	 * @param request
	 * @param response
	 * @throws IOException
	 */
		@RequestMapping("/delmail")
		public void delmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String id=request.getParameter("ids");
			String[] idStrings=id.split(","); 
			System.out.println(idStrings.length);
			for (int i = 0; i < idStrings.length; i++) { 
				 mailService.deleteByPK( Long.parseLong(idStrings[i]));
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("v", "删除成功！");
			writeJSON(response, jsonObject); 
	 
		}
		/**
		 * 消息详情/sys/mail/showmail
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping("/showmail")
		public void test(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id =request.getParameter("id");
			Mail m= mailService.get(Long.parseLong(id));
			     
	 		JSONObject obj1 = new JSONObject(); 
			obj1.put("user",m.getRecipient()); 
	 		obj1.put("title", m.getSubject());
	 		obj1.put("content", m.getMessage());
	 		obj1.put("createtime", m.getCreateTime().toString().substring(0, 19));
	 		
		 writeJSON(response, obj1);
		}
		
		/**
		 * 搜索
		 * / /sys/email/search
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
			List<Mail> alllist=mailService.queryByProerties("userId",((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
			
			//List<TzzNews> alllist= tzzNewsService.doQueryAll();
			List<Mail> nlist = new ArrayList<Mail>();
			if(name!=null&&!name.equals("")){
				for (int i = 0; i < alllist.size(); i++) {
					if(alllist.get(i).getMessage().contains(name)){
						nlist.add(alllist.get(i));
					} else if(alllist.get(i).getSubject().contains(name) ) {
						nlist.add(alllist.get(i)); 
					}else if(alllist.get(i).getRecipient().contains(name)){
						nlist.add(alllist.get(i)); 
					} 	
				}
			} 
			nlist=mailService.queryEmailHTMLToList(nlist);
			JSONObject jsonObject=new JSONObject();  
			if(nlist.size()>0){
				jsonObject.put("sta",1 );
			}else {
				jsonObject.put("sta",0 );
			} 
			jsonObject.put("list",nlist );
			writeJSON(response, jsonObject );
		}
		
		
		
		
		/**
		 * 分页查询/sys/mail/getmail
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value = "/getmail", method = { RequestMethod.POST, RequestMethod.GET })
		public void fenye(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String style=request.getParameter("style"); 
			Integer pagenum = Integer.valueOf(request.getParameter("pagenum")); 
	 		Integer maxtiaoshu = Integer.valueOf(request.getParameter("maxtiaoshu"));
	 		
			//List<Mail> alllist= mailService.doQueryAll();
			List<Mail> alllist=mailService.queryByProerties("userId",((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
			int mailnum=alllist.size();//总条数
			int totalpage=0;//总页数
			if(mailnum%maxtiaoshu==0)totalpage=mailnum/maxtiaoshu;
			else totalpage=mailnum/maxtiaoshu+1;  
			if(style.equals("+"))
				pagenum+=1;
			if(style.equals("-"))
				pagenum-=1;
			if(style.equals("0"))
				pagenum=1;
			if(style.equals("1"))
				pagenum=totalpage;
			List<Mail> list= mailService.getmailpage( pagenum, maxtiaoshu); 
			list=mailService.queryEmailHTMLToList(list);
			JSONObject jsonObject=new JSONObject(); 
			jsonObject.put("pagenum",pagenum );//页码
			jsonObject.put("totalpage", totalpage); //总页数
			jsonObject.put("newsnum", mailnum); //总条数 
			jsonObject.put("list", list);  
			writeJSON(response, jsonObject );
	 
			
		}
 
	
		
	// 发送邮件/sys/mail/sendEmail
	@RequestMapping(value = "/sendEmail", method = { RequestMethod.POST, RequestMethod.GET })
	public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
 	 	String recipient = request.getParameter("users");
		String subject = request.getParameter("title");
		String message = request.getParameter("content");
		String[] multiRecipient = recipient.split(",");
 		Emailphone emailphone=new Emailphone();
		emailphone=emailphoneService.getByProerties("estate", "1"); 
	  
		Map<String, Object> result = new HashMap<String, Object>();
		 result.put("result","&nbsp;&nbsp;&nbsp;");
		 
		for (int j = 0; j < multiRecipient.length; j++) {
			try { 
				 Mail mail=new Mail();
				 mail.setMessage(message);
				 mail.setSubject(subject);
				 mail.setUserId(((SysUser) request.getSession().getAttribute(SESSION_SYS_USER)).getId());
				 mail.setSender(emailphone.getEmail()); 
				 mail.setRecipient(multiRecipient[j]);
				 mail.setCreateTime(new Date());
				 SendMail sendMail=new SendMail(emailphone.getSmtp());
				 
				 String a= sendMail.send(mail, emailphone.getSmtp(), emailphone.getPwd());
				 if(a.equals("1")){
					mailService.persist(mail); 
				 }else {  
					 result.put("result", result.get("result")+multiRecipient[j]+"发送失败");
					 result.put("result", result.get("result")+"&nbsp;&nbsp;&nbsp;" ); 
				} 
				//====  
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if(result.get("result")=="&nbsp;&nbsp;&nbsp;"){
			result.put("result", "邮件发送成功");
		} 
		writeJSON(response, result);
	}
}
