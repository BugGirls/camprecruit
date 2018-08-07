package com.jeefw.controller.top;
      
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
import com.jeefw.model.sys.TzzMessage; 
import com.jeefw.service.sys.TzzMessageService;
import com.jeefw.service.sys.TzzUserService; 
 

/**
 * 底部条款
 */
@Controller
@RequestMapping("/top/message") 
public class TopMessageController extends JavaEEFrameworkBaseController<TzzMessage> implements Constant {

	 
	 
	@Resource
	private TzzUserService tzzUserService;
	@Resource
	private TzzMessageService tzzMessageService;
	
	
	/**
	 * 获取 列表
	 * /top/message/getall
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getall", method = { RequestMethod.POST, RequestMethod.GET })
	public void getallq(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	 
		List<TzzMessage> list = tzzMessageService.doQueryAll();
		JSONObject jsonObject= new JSONObject(); 
		jsonObject.put("data", list); 
		writeJSON(response, jsonObject); 		
	}
	
	/**
	 * 获取 列表
	 * /top/message/getservicecenter
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getservicecenter", method = { RequestMethod.POST, RequestMethod.GET })
	public void getservicecenter(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	 
		List<TzzMessage> list = tzzMessageService.queryByProerties("type", 3);
		request.setAttribute("data", list);	
		
		int id = list.get(0).getId();
		
		TzzMessage tzzMessage= new TzzMessage();
		tzzMessage=tzzMessageService.get(id);
		request.setAttribute("msg",tzzMessage);
		
		request.setAttribute("id", id); 
		
		request.getRequestDispatcher("/jsp/servicecenter.jsp").forward(request,response);
	}
	
	/**
	 * 获取详情
	 * /top/message/getxq
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getservicecenterxq", method = { RequestMethod.POST, RequestMethod.GET })
	public void getservicecenterxq(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		int id= Integer.parseInt(request.getParameter("id"));
		int type= Integer.parseInt(request.getParameter("type"));
		List<TzzMessage> list = tzzMessageService.queryByProerties("type", type); 
		request.setAttribute("data", list);
		TzzMessage tzzMessage= new TzzMessage();
		tzzMessage=tzzMessageService.get(id);
		request.setAttribute("msg",tzzMessage);
		
		request.setAttribute("id", id); 
		request.getRequestDispatcher("/jsp/servicecenter.jsp").forward(request,response); 
	}

	/**
	 * 获取详情
	 * /top/message/getxq
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxq", method = { RequestMethod.POST, RequestMethod.GET })
	public void getxq(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		int id= Integer.parseInt(request.getParameter("id"));
		List<TzzMessage> list = tzzMessageService.doQueryAll(); 
		request.setAttribute("data", list);
		TzzMessage tzzMessage= new TzzMessage();
		tzzMessage=tzzMessageService.get(id);
		request.setAttribute("msg",tzzMessage);
		
		request.setAttribute("id", id); 
		request.getRequestDispatcher("/jsp/about.jsp").forward(request,response); 
	}
 
  	  
}
