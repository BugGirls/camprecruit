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
import com.jeefw.model.sys.TzzAnswer; 
import com.jeefw.service.sys.TzzAnswerService; 

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 信息发布的控制层 
 */
@Controller
@RequestMapping("/sys/answer")
public class TzzAnswerController extends JavaEEFrameworkBaseController<TzzAnswer> implements Constant {

	//@Resource
	//private TzzQuestionService tzzQuestionService;
	@Resource
	private TzzAnswerService tzzAnswerService;
	
	// 查询信息发布的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getAnswer", method = { RequestMethod.POST, RequestMethod.GET })
	public void getQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String qid=request.getParameter("qid");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzAnswer tzzAnswer = new TzzAnswer();
		tzzAnswer.set$eq_questionId(Integer.parseInt(qid)); 
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("content") && result.getString("op").equals("cn")) {
					tzzAnswer.set$like_content(result.getString("data"));
				}
			}
			 
				tzzAnswer.setFlag("AND");
			 
		}
		tzzAnswer.setFirstResult((firstResult - 1) * maxResults);
		tzzAnswer.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzAnswer.setSortedConditions(sortedCondition);
		QueryResult<TzzAnswer> queryResult = tzzAnswerService.doPaginationQuery(tzzAnswer);
		JqGridPageView<TzzAnswer> tzzAnswerListView = new JqGridPageView<TzzAnswer>();
		tzzAnswerListView.setMaxResults(maxResults);
		List<TzzAnswer> informationHTMLList = tzzAnswerService.queryTzzAnswerHTMLList(queryResult.getResultList());
		tzzAnswerListView.setRows(informationHTMLList);
		tzzAnswerListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzAnswerListView);
	}

	// 全文检索信息
	@RequestMapping(value = "/getAnswerHibernateSearch", method = { RequestMethod.POST, RequestMethod.GET })
	public void getQuestionHibernateSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String luceneName = request.getParameter("luceneName");
		// Integer firstResult = Integer.valueOf(request.getParameter("page"));
		// Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		Integer firstResult = 1;
		Integer maxResults = 10;
		TzzAnswer tzzAnswer = new TzzAnswer();
		tzzAnswer.setFirstResult((firstResult - 1) * maxResults);
		tzzAnswer.setMaxResults(maxResults);
		JqGridPageView<TzzAnswer> tzzAnswerListView = new JqGridPageView<TzzAnswer>();
		tzzAnswerListView.setMaxResults(maxResults);
		List<TzzAnswer> tzzAnswerLuceneList = tzzAnswerService.queryByTzzAnswerName(luceneName);
		tzzAnswerListView.setRows(tzzAnswerLuceneList);
		tzzAnswerListView.setRecords(tzzAnswerLuceneList.size());
		writeJSON(response, tzzAnswerListView);
	}

	// 保存信息发布的实体Bean

	@RequestMapping(value = "/saveAnswer", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzAnswer entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
	 	ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
	 	if (CMD_EDIT.equals(parameter.getCmd())) {
	 		TzzAnswer tzzAnswer=tzzAnswerService.get(entity.getId());
	 		entity.setAdopt(tzzAnswer.getAdopt()); 
	 		entity.setQuestionId(tzzAnswer.getQuestionId());
	 		entity.setContent(tzzAnswer.getContent());
	 		entity.setCreateTime(tzzAnswer.getCreateTime()); 
	 		entity.setUserId(tzzAnswer.getUserId());
	 		tzzAnswerService.merge(entity);
	 	} else if (CMD_NEW.equals(parameter.getCmd())) {
	 		tzzAnswerService.persist(entity);
	 	}
	 	parameter.setSuccess(true);
	 	writeJSON(response, parameter);
	}

	// 操作信息发布的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateAnswer", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteTzzAnswer(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
		}
		else {
			Map<String, Object> result = new HashMap<String, Object>();  
			TzzAnswer entity = new TzzAnswer();  
			if (oper.equals("edit")) {
				entity.setId(Integer.valueOf(id));
				entity.setCmd("edit");
				doSave(entity, request, response);
			} else if (oper.equals("add")) {
				entity.setCmd("new");
				entity.setAdopt("0"); 
		 		entity.setQuestionId(Integer.parseInt((String)request.getParameter("qid")));
		 		entity.setContent(request.getParameter("content"));
		 		entity.setCreateTime(new Date()); 
		 		entity.setUserId(0);
				doSave(entity, request, response);
			}
		}
	}

	// 删除信息发布
	@RequestMapping("/deleteAnswer")
	public void deleteTzzAnswer(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzAnswerService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	// 操作信息发布的删除、导出Excel、字段判断和保存
//	//sys/answer/saveanswer
	@RequestMapping(value = "/saveanswer", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveanswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	int qid = Integer.valueOf(request.getParameter("qid"));
	 	String content = request.getParameter("con");
	 	TzzAnswer tzzAnswer = new TzzAnswer();
	 	tzzAnswer.setQuestionId(qid);
	 	tzzAnswer.setUserId(0);
	 	tzzAnswer.setContent(content);
	 	tzzAnswer.setAnswerId(0);
	 	tzzAnswer.setType(1);
	 	tzzAnswer.setAdopt("0");
	 	tzzAnswer.setCreateTime(new Date());
	 	tzzAnswer.setZan(0);
	 	tzzAnswerService.persist(tzzAnswer);
	 	writeJSON(response, "{err:0}");
	 	
	}
	
	
}
