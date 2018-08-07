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
import com.jeefw.model.sys.Emailphone; 
import com.jeefw.service.sys.EmailphoneService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 字典的控制层 
 */
@Controller
@RequestMapping("/sys/emailphone")
public class EmailPhoneController extends JavaEEFrameworkBaseController<Emailphone> implements Constant {

	@Resource
	private EmailphoneService emailphoneService;

	// 查询 的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getep", method = { RequestMethod.POST, RequestMethod.GET })
	public void getep(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Emailphone ep = new Emailphone();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("dictKey") && result.getString("op").equals("eq")) {
				//	dict.set$eq_dictKey(result.getString("data"));
				} 
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				ep.setFlag("OR");
			} else {
				ep.setFlag("AND");
			}
		}
		ep.setFirstResult((firstResult - 1) * maxResults);
		ep.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		ep.setSortedConditions(sortedCondition);
		QueryResult<Emailphone> queryResult = emailphoneService.doPaginationQuery(ep);
		JqGridPageView<Emailphone> dictListView = new JqGridPageView<Emailphone>();
		dictListView.setMaxResults(maxResults);
		List<Emailphone> dictWithSubList = emailphoneService.queryEmailphoneWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	// 保存 的实体Bean
	@RequestMapping(value = "/saveep", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Emailphone entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			emailphoneService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			emailphoneService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作字典的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateep", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateep(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteep(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String email = request.getParameter("email");
			String pwd = request.getParameter("pwd");
			String smtp = request.getParameter("smtp");
			String phone =request.getParameter("phone");
			String estate=request.getParameter("estate");
			String smspwd=request.getParameter("smspwd");
			String channel =request.getParameter("channel");
			String pstate =request.getParameter("pstate");
			 
			Emailphone emailphone = null;
			if (oper.equals("edit")) { 
				emailphone = emailphoneService.get(Integer.valueOf(id));
			} 
				Emailphone entity = new Emailphone();
				entity.setEmail(email);
				entity.setEstate(estate);
				entity.setPhone(phone);
				entity.setSmspwd(smspwd);
				entity.setChannel(channel);
				entity.setPstate(pstate);
				entity.setSmtp(smtp);
				entity.setPwd(pwd);  
				if (oper.equals("edit")) {
					entity.setId(Integer.valueOf(id)); 
					entity.setCreateTime(emailphone.getCreateTime());
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
	@RequestMapping("/deleteep")
	public void deleteep(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = emailphoneService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
