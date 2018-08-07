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
import com.jeefw.model.sys.TzzMessage; 
import com.jeefw.service.sys.TzzMessageService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 底部相关声明的控制层 
 */
@Controller
@RequestMapping("/sys/message")
public class TzzMessageController extends JavaEEFrameworkBaseController<TzzMessage> implements Constant {

	@Resource
	private TzzMessageService tzzMessageService;

	// 查询底部相关声明的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getmsg", method = { RequestMethod.POST, RequestMethod.GET })
	public void getmsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzMessage tzzMessage = new TzzMessage();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("title") && result.getString("op").equals("cn")) {
					tzzMessage.set$like_title(result.getString("data"));
				} 
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				tzzMessage.setFlag("OR");
			} else {
				tzzMessage.setFlag("AND");
			}
		}
		tzzMessage.setFirstResult((firstResult - 1) * maxResults);
		tzzMessage.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzMessage.setSortedConditions(sortedCondition);
		QueryResult<TzzMessage> queryResult =tzzMessageService.doPaginationQuery(tzzMessage);
		JqGridPageView<TzzMessage> dictListView = new JqGridPageView<TzzMessage>();
		dictListView.setMaxResults(maxResults);
		List<TzzMessage> dictWithSubList = tzzMessageService.queryTzzMessageHTMLList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	// 保存底部相关声明的实体Bean
	@RequestMapping(value = "/savemsg", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzMessage entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			tzzMessageService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzMessageService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作底部相关声明的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operatemsg", method = { RequestMethod.POST, RequestMethod.GET })
	public void operatemsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deletemsg(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String type = request.getParameter("type");
			String title = request.getParameter("title");
			String content = request.getParameter("content"); 
			TzzMessage tzzMessage = null;
			if (id!=null&&!id.equals("")) {
				tzzMessage = tzzMessageService.get(Integer.valueOf(id));
			}
			TzzMessage titlemsg = tzzMessageService.getByProerties("title", title); 
			if (StringUtils.isBlank(title)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写标题");
				writeJSON(response, result);
			} else if (null != titlemsg && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此标题信息编码已存在，请重新输入");
				writeJSON(response, result);
			} else if (null != titlemsg && !tzzMessage.getTitle().equalsIgnoreCase(title) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此标题信息编码已存在，请重新输入");
				writeJSON(response, result);
			} else {
				TzzMessage entity = new TzzMessage();
				entity.setTitle(title); 
				entity.setType(Integer.valueOf(type)); 
				entity.setContent(content);
				if (id!=null&&!id.equals("")) {
					entity.setId(Integer.valueOf(id));
					entity.setCreateTime(tzzMessageService.get(Integer.valueOf(id)).getCreateTime()); 
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else {
					entity.setCmd("new");
					entity.setCreateTime(new Date());
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除底部相关声明
	@RequestMapping("/deletemsg")
	public void deletemsg(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzMessageService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
