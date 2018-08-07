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

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController; 
import com.jeefw.model.sys.TzzAnswer;
import com.jeefw.model.sys.TzzQuestion;
import com.jeefw.service.sys.TzzAnswerService;
import com.jeefw.service.sys.TzzQuestionService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 信息发布的控制层 
 */
@Controller
@RequestMapping("/sys/question")
public class TzzQuestionController extends JavaEEFrameworkBaseController<TzzQuestion> implements Constant {

	@Resource
	private TzzQuestionService tzzQuestionService;
	@Resource
	private TzzAnswerService tzzAnswerService;

	// 查询问题发布的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getQuestion", method = { RequestMethod.POST, RequestMethod.GET })
	public void getQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzQuestion tzzQuestion = new TzzQuestion();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("title") && result.getString("op").equals("cn")) {
					tzzQuestion.set$like_title(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				tzzQuestion.setFlag("OR");
			} else {
				tzzQuestion.setFlag("AND");
			}
		}
		tzzQuestion.setFirstResult((firstResult - 1) * maxResults);
		tzzQuestion.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzQuestion.setSortedConditions(sortedCondition);
		QueryResult<TzzQuestion> queryResult = tzzQuestionService.doPaginationQuery(tzzQuestion);
		JqGridPageView<TzzQuestion> tzzQuestionListView = new JqGridPageView<TzzQuestion>();
		tzzQuestionListView.setMaxResults(maxResults);
		List<TzzQuestion> informationHTMLList = tzzQuestionService.queryTzzQuestionHTMLList(queryResult.getResultList());
		tzzQuestionListView.setRows(informationHTMLList);
		tzzQuestionListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzQuestionListView);
	}

	// 全文检索信息
	@RequestMapping(value = "/getQuestionHibernateSearch", method = { RequestMethod.POST, RequestMethod.GET })
	public void getQuestionHibernateSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String luceneName = request.getParameter("luceneName");
		// Integer firstResult = Integer.valueOf(request.getParameter("page"));
		// Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		Integer firstResult = 1;
		Integer maxResults = 10;
		TzzQuestion tzzQuestion = new TzzQuestion();
		tzzQuestion.setFirstResult((firstResult - 1) * maxResults);
		tzzQuestion.setMaxResults(maxResults);
		JqGridPageView<TzzQuestion> tzzQuestionListView = new JqGridPageView<TzzQuestion>();
		tzzQuestionListView.setMaxResults(maxResults);
		List<TzzQuestion> tzzQuestionLuceneList = tzzQuestionService.queryByTzzQuestionName(luceneName);
		tzzQuestionListView.setRows(tzzQuestionLuceneList);
		tzzQuestionListView.setRecords(tzzQuestionLuceneList.size());
		writeJSON(response, tzzQuestionListView);
	}

	// 保存问题发布的实体Bean

	@RequestMapping(value = "/saveQuestion", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzQuestion entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
	 	ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
	 	if (CMD_EDIT.equals(parameter.getCmd())) {
	 		TzzQuestion tzzQuestion=tzzQuestionService.get(entity.getId());
	 		entity.setBrowseNum(tzzQuestion.getBrowseNum());
	 		entity.setContent(tzzQuestion.getContent());
	 		entity.setCreateTime(tzzQuestion.getCreateTime()); 
	 		entity.setFamilyId(tzzQuestion.getFamilyId());
	 		entity.setState(tzzQuestion.getState());
	 		entity.setTitle(tzzQuestion.getTitle());
	 		entity.setUserId(tzzQuestion.getUserId()); 
	 		tzzQuestionService.merge(entity);
	 	} else if (CMD_NEW.equals(parameter.getCmd())) {
	 		tzzQuestionService.persist(entity);
	 	}
	 	parameter.setSuccess(true);
	 	writeJSON(response, parameter);
	}

	// 操作问题的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateQuestion", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateQuestion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteTzzQuestion(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			TzzQuestion entity = new TzzQuestion();
			entity.setId(Integer.parseInt(id));
			entity.setEssence(request.getParameter("essencecn"));
			entity.setTop(request.getParameter("topcn"));  
			if (StringUtils.isNotBlank(id)) { 
				entity.setCmd("edit");
				doSave(entity, request, response);
			 
			}
		}
	}

	// 删除问题发布
	@RequestMapping("/deleteQuestion")
	public void deleteTzzQuestion(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzQuestionService.deleteByPK(ids);
		if (flag) {
			for (int i = 0; i < ids.length; i++) {
				List<TzzAnswer> list = tzzAnswerService.doQueryAll();
				for (int j = 0; j < list.size(); j++) {
					if(list.get(j).getQuestionId()==ids[i]){
						tzzAnswerService.delete(list.get(j));
					}
				}
			}
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
