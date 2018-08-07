package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.net.aso.j;
import oracle.net.aso.n;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Authority;
import com.jeefw.model.sys.Role;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.RoleService;
import com.jeefw.service.sys.SysUserService;
import com.jeefw.service.sys.TzzDictionaryService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 角色的控制层 
 */
@Controller
@RequestMapping("/sys/TzzCustomerService")
public class CustomerServiceQQController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	TzzDictionaryService dictionaryService;
	

	@RequestMapping(value = "/getServiceList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzDictionary dictionary = new TzzDictionary();
		dictionary.set$eq_type("7");
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					dictionary.set$like_name(result.getString("data"));
				}
				if (result.getString("field").equals("title") && result.getString("op").equals("eq")) {
					dictionary.set$eq_title(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				dictionary.setFlag("OR");
			} else {
				dictionary.setFlag("AND");
			}
			
		}
		dictionary.setFirstResult((firstResult - 1) * maxResults);
		dictionary.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		dictionary.setSortedConditions(sortedCondition);
		QueryResult<TzzDictionary> queryResult = dictionaryService.doPaginationQuery(dictionary);
		List <TzzDictionary> queryList = queryResult.getResultList();
		queryList = dictionaryService.queryTzzDictCnList(queryList);
		JqGridPageView<TzzDictionary> authorityListView = new JqGridPageView<TzzDictionary>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteDict")
		public void deleteDict(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = dictionaryService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveAuthority", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(TzzDictionary tzzDictionary, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) tzzDictionary);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				dictionaryService.merge(tzzDictionary);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				dictionaryService.persist(tzzDictionary);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateDict", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		String type = "7";
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteDict(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String categoryName = request.getParameter("name");
			String categoryTitle = request.getParameter("title"); 
			String categoryLevel = ""; 
			int categoryParentIdNum = 0 ;
			
			TzzDictionary tzzDictionary = null;
			if (oper.equals("edit")) {
				tzzDictionary = dictionaryService.get(Integer.valueOf(id));
			}
			TzzDictionary sameNameDict = dictionaryService.getByProerties("name", categoryName);
			//TzzDictionary parentMenuCodeAuthority = dictionaryService.getByProerties("id", categoryParentIdNum);

			if (StringUtils.isBlank(categoryName) || StringUtils.isBlank(categoryTitle)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写客服名字和QQ号！");
				writeJSON(response, result);
			} else if (null != sameNameDict && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "QQ客服已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != sameNameDict && !tzzDictionary.getName().equals(categoryName) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "QQ客服的名字重复，请重新输入");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (categoryName != null && !"".equals(categoryName)) {
						tzzDictionary.setName(categoryName);
					}
					if(categoryTitle!= null && !"".equals(categoryTitle)){
						tzzDictionary.setTitle(categoryTitle);
					} 
					tzzDictionary.setParentId(categoryParentIdNum);
					tzzDictionary.setType(type);
					tzzDictionary.setLevel(categoryLevel);	
					tzzDictionary.setCmd("edit");
					doSave(tzzDictionary, request, response);
				} else if (oper.equals("add")) {
					List <TzzDictionary> dictList = dictionaryService.queryByProerties("type", "7");
					if (dictList != null && dictList.size() >= 2){
						response.setStatus(HttpServletResponse.SC_CONFLICT);
						result.put("message", "客服的QQ只能添加两条记录！");
						writeJSON(response, result);
					}else{
						TzzDictionary tzzDictionary1 = new TzzDictionary();
						tzzDictionary1.setName(categoryName);
						tzzDictionary1.setTitle(categoryTitle);
						tzzDictionary1.setParentId(categoryParentIdNum);
						tzzDictionary1.setType(type);
						tzzDictionary1.setLevel(categoryLevel);	
						tzzDictionary1.setCreateTime(new Date());
						tzzDictionary1.setCmd("new");
						doSave(tzzDictionary1, request, response);
					}
				}
			}
		}
	}
}
