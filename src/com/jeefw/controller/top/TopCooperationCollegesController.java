package com.jeefw.controller.top;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.CooperationColleges;
import com.jeefw.service.sys.CooperationCollegesService;

/**
 * 合作院校的控制层
 */
@Controller
@RequestMapping("/top/cooperationcolleges")
public class TopCooperationCollegesController extends JavaEEFrameworkBaseController<CooperationColleges> implements Constant {
	@Resource
	CooperationCollegesService cooperationCollegesService;
	
	// 保存用户订单信息
	@RequestMapping(value="/getdetail", method = { RequestMethod.POST, RequestMethod.GET })
	public void getdetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer id = Integer.parseInt(request.getParameter("id")) ;
		CooperationColleges cooperationColleges = cooperationCollegesService.get(id);
		request.setAttribute("cooperationColleges", cooperationColleges);
		request.getRequestDispatcher("/jsp/cooperation_colleges.jsp").forward(request, response);
	}

}
