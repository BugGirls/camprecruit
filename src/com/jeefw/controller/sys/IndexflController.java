package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.TzzDictionary; 
import com.jeefw.service.sys.TzzDictionaryService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 字典的控制层 
 */
@Controller
@RequestMapping("/sys/indexfl")
public class IndexflController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {

	@Resource
	private TzzDictionaryService tzzDictionaryService;

	// /sys/indexfl/getindexfl
	// 查询字典的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getindexfl", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDict(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzDictionary tzzDictionary = new TzzDictionary();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			//for (int i = 0; i < jsonArray.size(); i++) {
				//JSONObject result = (JSONObject) jsonArray.get(i);  
				//if (result.getString("field").equals("dictValue") && result.getString("op").equals("cn")) {
				//	tzzDictionary.set$like_dictValue(result.getString("data"));
				//}
			//}
			
		tzzDictionary.setFlag("AND"); 
		}tzzDictionary.set$eq_type("4");  
		tzzDictionary.setFirstResult((firstResult - 1) * maxResults);
		tzzDictionary.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzDictionary.setSortedConditions(sortedCondition);
		QueryResult<TzzDictionary> queryResult = tzzDictionaryService.doPaginationQuery(tzzDictionary);
		JqGridPageView<TzzDictionary> dictListView = new JqGridPageView<TzzDictionary>();
		dictListView.setMaxResults(maxResults);
		List<TzzDictionary> dictWithSubList=new ArrayList<TzzDictionary>();
		for (int i = 0; i < queryResult.getResultList().size(); i++) {
			TzzDictionary tzzd=new TzzDictionary();
			System.out.println(queryResult.getResultList().get(i).getParentId());
			tzzd.setId(((TzzDictionary)queryResult.getResultList().get(i)).getId());
			tzzd.setParentcn(tzzDictionaryService.get(((TzzDictionary)queryResult.getResultList().get(i)).getParentId()).getName());
			tzzd.setParentId(((TzzDictionary)queryResult.getResultList().get(i)).getParentId());
			tzzd.setCreateTime(((TzzDictionary)queryResult.getResultList().get(i)).getCreateTime());
			dictWithSubList.add(tzzd); 
		} 
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	// 保存字典的实体Bean
	@RequestMapping(value = "/saveindexfl", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzDictionary entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			tzzDictionaryService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzDictionaryService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	//   /sys/indexfl/operateindexfl
	// 操作字典的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateindexfl", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateindexfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteindexfl(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			  
			String parentId = request.getParameter("parentcn"); 
			TzzDictionary tzzDictionary = null;
			if (oper.equals("edit")) {
				tzzDictionary = tzzDictionaryService.get(Integer.valueOf(id));
			}  
			TzzDictionary entity = new TzzDictionary();
			entity.setLevel("0"); 
			entity.setParentId(Integer.parseInt(parentId)); 
			entity.setType("4");
			if (oper.equals("edit")) {
				entity.setCreateTime(tzzDictionary.getCreateTime());
				entity.setId(Integer.valueOf(id)); 
				entity.setCmd("edit"); 
				doSave(entity, request, response);
			} else if (oper.equals("add")) {
				entity.setCreateTime(new Date());
				entity.setCmd("new");
				doSave(entity, request, response);
			}
		}
		
	}

	// 删除字典
	@RequestMapping("/deleteindexfl")
	public void deleteindexfl(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzDictionaryService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	

	/**
	 * 获取首页分类列表
	 * /sys/indexfl/getindexfenleiList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping(value = "/getindexfenleiList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getvipSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception { 
			
			String[] name=new String[2];
			String[] v=new String[2];
			name[0]="type";
			name[1]="level";
			v[0]="1";
			v[1]="1";
			List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties(name, v);
			StringBuilder builder = new StringBuilder();
			builder.append("<select> "); 
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
			}
			builder.append("</select>"); 
			writeJSON(response, builder.toString());
		}
		
}
