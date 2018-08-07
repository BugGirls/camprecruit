package com.jeefw.controller.top;
      

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
      

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;   
import com.jeefw.model.sys.Information;  
import com.jeefw.model.sys.TzzJianjie; 
import com.jeefw.service.sys.TzzJianjieService; 
 

/**
 * 成功案例
 */
@Controller
@RequestMapping("/top/jianjie") 
public class TopJianjieController extends JavaEEFrameworkBaseController<Information> implements Constant {

	  
	@Resource
	private TzzJianjieService tzzJianjieService;
	
	
	/**
	 * 获取冒险家简介
	 * /top/jianjie/gettzz
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/gettzz", method = { RequestMethod.POST, RequestMethod.GET })
	public void gettzz(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		TzzJianjie tzzJianjie=tzzJianjieService.get(1);  
		request.setAttribute("tzzJianjie", tzzJianjie);
		request.getRequestDispatcher("/jsp/jieshao.jsp").forward(request,response); 
	}

	/**
	 * 获取vip权益
	 * /top/jianjie/getvip
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getvip", method = { RequestMethod.POST, RequestMethod.GET })
	public void getvip(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		TzzJianjie tzzJianjie=tzzJianjieService.get(2);  
		request.setAttribute("tzzJianjie", tzzJianjie);
		request.getRequestDispatcher("/jsp/jieshao.jsp").forward(request,response); 
	}
	/**
	 * 获取冒险家介绍
	 * /top/jianjie/getzhyd
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getzhyd", method = { RequestMethod.POST, RequestMethod.GET })
	public void getzhyd(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		TzzJianjie tzzJianjie=tzzJianjieService.get(3);  
		request.setAttribute("tzzJianjie", tzzJianjie);
		request.getRequestDispatcher("/jsp/jieshao.jsp").forward(request,response); 
	}
	/**
	 * 获取联系我们
	 * /top/jianjie/getzhyd
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcallus", method = { RequestMethod.POST, RequestMethod.GET })
	public void getcallus(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		TzzJianjie tzzJianjie=tzzJianjieService.get(4);  
		request.setAttribute("tzzJianjie", tzzJianjie);
		request.getRequestDispatcher("/jsp/jieshao.jsp").forward(request,response); 
	}
	/**
	 * 获取用户协议
	 * /top/jianjie/getxy
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxy", method = { RequestMethod.POST, RequestMethod.GET })
	public void getxy(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		TzzJianjie tzzJianjie=tzzJianjieService.get(5);   
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("xieyi",tzzJianjie ); 
		writeJSON(response, jsonObject); 		
	}
	
		  
}
