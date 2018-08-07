package com.jeefw.controller.top;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
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
import com.jeefw.model.sys.Dict;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.DictService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 字典的控制层 
 */
@Controller
@RequestMapping("/top/dict")
public class TopDictController extends JavaEEFrameworkBaseController<Dict> implements Constant {

	@Resource
	private DictService dictService;
	
	@RequestMapping(value = "/getTypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTypeSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentDictkey = request.getParameter("parentDictkey");
		Dict dict = new Dict();
		dict.set$eq_parentDictkey(parentDictkey);
		
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("sequence", "asc");
		dict.setSortedConditions(sortedCondition);
		QueryResult<Dict> queryResult = dictService.doPaginationQuery(dict);
		JqGridPageView<Dict> dictListView = new JqGridPageView<Dict>();
		dictListView.setRows(queryResult.getResultList());
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	// 查询字典的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getDict", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDict(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Dict dict = new Dict();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("dictKey") && result.getString("op").equals("eq")) {
					dict.set$eq_dictKey(result.getString("data"));
				}
				if (result.getString("field").equals("dictValue") && result.getString("op").equals("cn")) {
					dict.set$like_dictValue(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				dict.setFlag("OR");
			} else {
				dict.setFlag("AND");
			}
		}
		dict.setFirstResult((firstResult - 1) * maxResults);
		dict.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		dict.setSortedConditions(sortedCondition);
		QueryResult<Dict> queryResult = dictService.doPaginationQuery(dict);
		JqGridPageView<Dict> dictListView = new JqGridPageView<Dict>();
		dictListView.setMaxResults(maxResults);
		List<Dict> dictWithSubList = dictService.queryDictWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	

}
