package com.jeefw.controller.sys;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Dict;
import com.jeefw.service.sys.DictService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 字典的控制层 
 */
@Controller
@RequestMapping("/sys/dict")
public class DictController extends JavaEEFrameworkBaseController<Dict> implements Constant {

	@Resource
	private DictService dictService;

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

	// 保存字典的实体Bean
	@RequestMapping(value = "/saveDict", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Dict entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			dictService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			dictService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作字典的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateDict", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateDict(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
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
			String dictKey = request.getParameter("dictKey");
			String dictValue = request.getParameter("dictValue");
			String sequence = request.getParameter("sequence");
			String parentDictkey = request.getParameter("parentDictkey");
			Dict dict = null;
			if (oper.equals("edit")) {
				dict = dictService.get(Integer.valueOf(id));
			}
			Dict dictKeyDict = dictService.getByProerties("dictKey", dictKey);
			Dict parentDictkeyDict = dictService.getByProerties("dictKey", parentDictkey);
			if (StringUtils.isBlank(dictKey) || StringUtils.isBlank(dictValue)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写字典编码和字典名称");
				writeJSON(response, result);
			} else if (null != dictKeyDict && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此字典编码已存在，请重新输入");
				writeJSON(response, result);
			} else if (null != dictKeyDict && !dict.getDictKey().equalsIgnoreCase(dictKey) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此字典编码已存在，请重新输入");
				writeJSON(response, result);
			} else if (StringUtils.isNotBlank(parentDictkey) && null == parentDictkeyDict) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "上级字典编码输入有误，请重新输入");
				writeJSON(response, result);
			} else {
				Dict entity = new Dict();
				entity.setDictKey(dictKey);
				entity.setDictValue(dictValue);
				entity.setSequence(sequence == "" ? null : Long.valueOf(sequence));
				entity.setParentDictkey(parentDictkey);
				if (oper.equals("edit")) {
					entity.setId(Integer.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除字典
	@RequestMapping("/deleteDict")
	public void deleteDict(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = dictService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	/**
	 * 根据No查询
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping(value="/getDicBydictKey")
	@ResponseBody
	public Dict getDicBydictKey(HttpServletRequest request, HttpServletResponse response){
		String dictKey = request.getParameter("dictKey");
		Dict dict = dictService.getByProerties("dictKey", dictKey);
		return dict;
	}
	
	/**
	 * 根据父类查询子类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDictSelectListByParentKey", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDictSelectListByParentKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentDictkey = request.getParameter("parentDictkey");
		String dictKey = request.getParameter("dictKey");
		List<Dict> dictList = dictService.queryByProerties("parentDictkey", parentDictkey);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < dictList.size(); i++) {
			if(StringUtils.isNotEmpty(dictKey) && dictKey.equals(dictList.get(i).getDictKey())){
				builder.append("<option value='" + dictList.get(i).getDictKey() + "' selected='selected'>" +dictList.get(i).getDictValue()+  "</option>");
			} else {
				builder.append("<option value='" + dictList.get(i).getDictKey() + "'>" +dictList.get(i).getDictValue()+  "</option>");
			}
		}
		writeJSON(response, builder.toString());
	}

}
	
	
