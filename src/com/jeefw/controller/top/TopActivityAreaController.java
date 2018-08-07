package com.jeefw.controller.top;
     
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Information;
import com.jeefw.model.sys.TzzActivityArea;
import com.jeefw.service.sys.TzzActivityAreaService; 
 

/**
 * 活动专区
 */
@Controller
@RequestMapping("/top/successfulCase/activityarea") 
public class TopActivityAreaController extends JavaEEFrameworkBaseController<Information> implements Constant {

	@Resource
	TzzActivityAreaService activityAreaService;
	
	/**
	 * 获取活动专区列表
	 * /top/activityarea/getalli
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getalli", method = { RequestMethod.POST, RequestMethod.GET })
	public void getallq(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "pagenum", required = false) Integer pagenum,@RequestParam(value = "type", required = false) Integer type) throws Exception { 
		if(null==type)type=140;
		if(null==pagenum)pagenum=1;
		request.setAttribute("type",type );
		int nums = activityAreaService.searchActivityAreaCount();
		int pages=0;
		if(nums%9==0){
			pages=nums/9;
		}else {
			pages=nums/9+1;
		}
		request.setAttribute("pages",pages );
		List<TzzActivityArea> ilist=activityAreaService.searchActivityArea(pagenum);
		ilist=activityAreaService.queryTopInformationHTMLList(ilist);
		request.setAttribute("ilist", ilist);
		request.getRequestDispatcher("/jsp/activity_area.jsp").forward(request,response); 
	}
	 
	
	/**
	 * /top/activityarea/getixq
	 * 活动专区详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getixq", method = { RequestMethod.POST, RequestMethod.GET })
	public void getixq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 if(request.getParameter("id")!=null&&!request.getParameter("id").equals("")){
			 Integer id=Integer.parseInt(request.getParameter("id"));
			 TzzActivityArea news=  activityAreaService.get(id);
			 request.setAttribute("zh", news);
		 }else {
			request.setAttribute("err", 1);
		} 
		 request.getRequestDispatcher("/jsp/activity_area_xq.jsp").forward(request,response); 
	 }
		  
}
