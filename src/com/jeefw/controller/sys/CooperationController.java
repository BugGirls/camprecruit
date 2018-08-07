package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.TzzCooperation; 
import com.jeefw.service.sys.TzzCooperationService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 信息发布的控制层 
 */
@Controller
@RequestMapping("/sys/tzzcooperation")
public class CooperationController extends JavaEEFrameworkBaseController<TzzCooperation> implements Constant {

	@Resource
	private TzzCooperationService tzzCooperationService;

	// 查询信息发布的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getcooperation", method = { RequestMethod.POST, RequestMethod.GET })
	public void getInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		//String filters = request.getParameter("filters");
		TzzCooperation tzzCooperation = new TzzCooperation();
		//	}
	//	} 
		tzzCooperation.setFlag("AND"); 
		tzzCooperation.setFirstResult((firstResult - 1) * maxResults);
		tzzCooperation.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzCooperation.setSortedConditions(sortedCondition);
		QueryResult<TzzCooperation> queryResult = tzzCooperationService.doPaginationQuery(tzzCooperation);
		JqGridPageView<TzzCooperation> informationListView = new JqGridPageView<TzzCooperation>();
		informationListView.setMaxResults(maxResults);
		informationListView.setRows(queryResult.getResultList());
		informationListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, informationListView);
	}

	// 全文检索信息
	@RequestMapping(value = "/getcooperationHibernateSearch", method = { RequestMethod.POST, RequestMethod.GET })
	public void getInformationHibernateSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
	}

	// 保存信息发布的实体Bean

	@RequestMapping(value = "/savecooperation", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzCooperation entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			tzzCooperationService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzCooperationService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作信息发布的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operatecooperation", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteInformation(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
		} else if (oper.equals("excel")) {
			response.setContentType("application/msexcel;charset=UTF-8");
			try {
				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
				OutputStream out = response.getOutputStream();
				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Map<String, Object> result = new HashMap<String, Object>(); 
			String image =request.getParameter("image"); 
			String content = request.getParameter("content");
		 
			TzzCooperation entity = new TzzCooperation(); 
			entity.setContent(content); 
//			entity.setImage(image);
//			entity.setRefreshTime(new Date());
			if (StringUtils.isNotBlank(id)) {
				entity.setId(Integer.valueOf(id));
				entity.setCmd("edit");
				doSave(entity, request, response);
			} else {
				entity.setCmd("new");
				doSave(entity, request, response);
			}
			
		}
	}

	// 删除信息发布
	@RequestMapping("/deletecooperation")
	public void deleteInformation(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzCooperationService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
