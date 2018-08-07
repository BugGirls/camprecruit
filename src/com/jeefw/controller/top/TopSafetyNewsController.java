package com.jeefw.controller.top;
     
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.dao.sys.InformationDao;
import com.jeefw.model.sys.Information;
import com.jeefw.model.sys.TzzSafetyNews;
import com.jeefw.service.sys.InformationService;
import com.jeefw.service.sys.TzzSafetyNewsService;
import com.jeefw.service.sys.TzzUserService; 
 

/**
 * 安全新闻
 */
@Controller
@RequestMapping("/top/safetynews") 
public class TopSafetyNewsController extends JavaEEFrameworkBaseController<Information> implements Constant {

	 
	 
	@Resource
	private TzzUserService tzzUserService;
	@Resource
	private InformationService informationService;
	@Resource
	TzzSafetyNewsService safetyNewsService;
	
	/**
	 * 获取安全新闻列表
	 * /top/safetynews/getalli
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getalli", method = { RequestMethod.POST, RequestMethod.GET })
	public void getallq(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "pagenum", required = false) Integer pagenum,@RequestParam(value = "type", required = false) Integer type) throws Exception { 
		if(null==type)type=140;
		if(null==pagenum)pagenum=1;
		request.setAttribute("type",type );
		int nums = safetyNewsService.searchSafetyNewsCount(type);
		int pages=0;
		if(nums%9==0){
			pages=nums/9;
		}else {
			pages=nums/9+1;
		}
		request.setAttribute("pages",pages );
		List<TzzSafetyNews> ilist=safetyNewsService.searchSafetyNews(pagenum,type);
		ilist=safetyNewsService.queryTopInformationHTMLList(ilist);
		request.setAttribute("ilist", ilist);
		request.getRequestDispatcher("/jsp/safety_news.jsp").forward(request,response); 
	}
	 
	
	/**
	 * /top/safetynews/getixq
	 * 安全新闻详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getixq", method = { RequestMethod.POST, RequestMethod.GET })
	public void getixq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 if(request.getParameter("id")!=null&&!request.getParameter("id").equals("")){
			 Integer id=Integer.parseInt(request.getParameter("id"));
			 TzzSafetyNews news=  safetyNewsService.get(id);
			 request.setAttribute("zh", news);
		 }else {
			request.setAttribute("err", 1);
		} 
		 request.getRequestDispatcher("/jsp/safety_news_xq.jsp").forward(request,response); 
	 }
		  
}
