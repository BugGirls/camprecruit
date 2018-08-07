package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzSms;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.EmailphoneService;
import com.jeefw.service.sys.TzzSmsService;
import com.jeefw.service.sys.TzzUserService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.Sms;

/**
 * 短信的控制层 
 */
@Controller
@RequestMapping("/sys/sms")
public class TzzSmsController extends JavaEEFrameworkBaseController<TzzSms> implements Constant {

	@Resource
	private TzzSmsService tzzSmsService;
	@Resource
	private EmailphoneService emailphoneService;
	@Resource
	private TzzUserService tzzUserService;
	
	
	/**
	 * 获取用户手机号下拉菜单
	 * /sys/sms/getPhone
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping(value = "/getPhone", method = { RequestMethod.POST, RequestMethod.GET })
		public void getPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
			List<TzzUser> tzzUserList = tzzUserService.doQueryAll();
			List<String> phones = new ArrayList<String>();
			for (int i = 0; i < tzzUserList.size(); i++) {
				if(tzzUserList.get(i).getPhone()!=null&&!tzzUserList.get(i).getPhone().equals("")){
					phones.add(tzzUserList.get(i).getPhone());
				}
			}
			StringBuilder builder = new StringBuilder();
			builder.append("<select>"); 
			for (int i = 0; i < phones.size(); i++) {
				builder.append("<option value='" + phones.get(i) + "'>" +phones.get(i) +  "</option>");
			}
			builder.append("</select>"); 
			writeJSON(response, builder.toString());
		}
	
	 
	
	// 查询 的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getsms", method = { RequestMethod.POST, RequestMethod.GET })
	public void getsms(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzSms tzzSms = new TzzSms();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("content") && result.getString("op").equals("cn")) {
				 	tzzSms.set$like_content(result.getString("data"));
				} 
				if (result.getString("field").equals("phone") && result.getString("op").equals("eq")) {
				 	tzzSms.set$eq_phone(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				tzzSms.setFlag("OR");
			} else {
				tzzSms.setFlag("AND");
			}
		}
		tzzSms.setFirstResult((firstResult - 1) * maxResults);
		tzzSms.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzSms.setSortedConditions(sortedCondition);
		QueryResult<TzzSms> queryResult = tzzSmsService.doPaginationQuery(tzzSms);
		JqGridPageView<TzzSms> tzzSmsView = new JqGridPageView<TzzSms>();
		tzzSmsView.setMaxResults(maxResults);
		List<TzzSms> tzzSmsWithSubList = tzzSmsService.queryTzzSmsWithSubList(queryResult.getResultList());
		tzzSmsView.setRows(tzzSmsWithSubList);
		tzzSmsView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzSmsView);
	}

	// 保存 的实体Bean
	@RequestMapping(value = "/savesms", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzSms entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) { 
			tzzSmsService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzSmsService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作字典的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operatesms", method = { RequestMethod.POST, RequestMethod.GET })
	public void operatesms(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deletesms(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String phone =request.getParameter("phone");
			if(Pattern.matches("^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$", phone)){
				String content=request.getParameter("content");
				Emailphone ep= emailphoneService.getByProerties("pstate", "1");  
				if(ep!=null){
					String msg = Sms.sendsms(ep.getPhone(), ep.getSmspwd(), ep.getChannel(), content,phone);//发短信
					String[]msgs =msg.split(":");
					System.out.println(msgs[0]+"***"+msgs[1]);
					if( msgs[0].equals("error")){
						response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
						result.put("message", "发送失败！请核对手机号");
						writeJSON(response, result);
						//System.out.println(msgs[1]);
					}else {
						TzzSms entity = new TzzSms();
						entity.setPhone(phone);
						entity.setContent(content); 
						entity.setCreateTime(new Date());
						entity.setCmd("new");
						doSave(entity, request, response);
					} 
				
				}else{
					response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
					result.put("message", "账号不可用");
					writeJSON(response, result);
				} 
			}else {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "手机号不正确");
				writeJSON(response, result);
			}
				
		}
	}

	// 删除字典
	@RequestMapping("/deletesms")
	public void deletesms(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzSmsService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
