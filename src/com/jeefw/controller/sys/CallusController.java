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
  


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.TzzJianjie; 
import com.jeefw.service.sys.TzzJianjieService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 信息发布的控制层 
 */
@Controller
@RequestMapping("/sys/callus")
public class CallusController extends JavaEEFrameworkBaseController<TzzJianjie> implements Constant {

	@Resource
	private TzzJianjieService tzzJianjieService;

	// 查询信息发布的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getjianjie", method = { RequestMethod.POST, RequestMethod.GET })
	public void getInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord"); 
		TzzJianjie tzzJianjie = new TzzJianjie(); 
		tzzJianjie.set$eq_id(4); 
		tzzJianjie.setFlag("AND"); 
		
		//}
		tzzJianjie.setFirstResult((firstResult - 1) * maxResults);
		tzzJianjie.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzJianjie.setSortedConditions(sortedCondition);
		QueryResult<TzzJianjie> queryResult = tzzJianjieService.doPaginationQuery(tzzJianjie);
		JqGridPageView<TzzJianjie> informationListView = new JqGridPageView<TzzJianjie>();
		informationListView.setMaxResults(maxResults);
		List<TzzJianjie> informationHTMLList = tzzJianjieService.queryTzzJianjieHTMLList(queryResult.getResultList());
		informationListView.setRows(informationHTMLList);
		informationListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, informationListView);
	}

	// 全文检索信息
	@RequestMapping(value = "/getjianjieHibernateSearch", method = { RequestMethod.POST, RequestMethod.GET })
	public void getInformationHibernateSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
	}

	// 保存信息发布的实体Bean

	@RequestMapping(value = "/savejianjie", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzJianjie entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			tzzJianjieService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzJianjieService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作信息发布的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operatejianjie", method = { RequestMethod.POST, RequestMethod.GET })
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
			String content = request.getParameter("content"); 
			TzzJianjie entity = new TzzJianjie(); 
			entity.setContent(content);  
			entity.setRefreshTime(new Date());
			if (StringUtils.isNotBlank(id)) {
				entity.setId(Integer.valueOf(id));
				entity.setImage(tzzJianjieService.get(Integer.valueOf(id)).getImage()); 
				entity.setCmd("edit");
				doSave(entity, request, response);
			} else {
				entity.setCmd("new");
				doSave(entity, request, response);
			}
			
		}
	}

	// 删除信息发布
	@RequestMapping("/deletejianjie")
	public void deleteInformation(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzJianjieService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
