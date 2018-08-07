package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.jeefw.model.sys.PersonalMessage;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.PersonalMessageService;

import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.NumberHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 个人消息通知的控制层
 */
@Controller
@RequestMapping("/sys/personalmessage")
public class PersonalMessageController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	PersonalMessageService personalMessageService;


	@RequestMapping(value = "/getPromotionComboList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPromotionComboList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		PersonalMessage combo = new PersonalMessage();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				combo.setFlag("OR");
			} else {
				combo.setFlag("AND");
			}
		}
		combo.setFirstResult((firstResult - 1) * maxResults);
		combo.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		combo.setSortedConditions(sortedCondition);
		QueryResult<PersonalMessage> queryResult = personalMessageService.doPaginationQuery(combo);
		List<PersonalMessage> queryList = queryResult.getResultList();
		JqGridPageView<PersonalMessage> authorityListView = new JqGridPageView<PersonalMessage>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	

	// 删除菜单
	@RequestMapping("/deletePromotionCombo")
	public void deletePromotionCombo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = personalMessageService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	@RequestMapping(value = "/saveOrUpdateCombo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveOrUpdateCombo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		String price = request.getParameter("price");
		String startdates = request.getParameter("startdate");
		String enddates = request.getParameter("enddate");
		String startdate = startdates.replace("/", "-");
		String enddate = enddates.replace("/", "-");
		Integer disable = request.getParameter("disable") != null ? 1 : 0;
		String instruction = request.getParameter("addContent");
		
		if(id != null){
			PersonalMessage updatecombo = personalMessageService.get(NumberHelper.string2Int(id));
//			updatecombo.setName(name);
//			updatecombo.setDescription(desc);
//			updatecombo.setInstruction(instruction);
//			updatecombo.setPrice(NumberHelper.string2Float(price,0));
//			if(StringHelper.isNotEmpty(startdate)){
//				updatecombo.setStartdate(sdf.parse(startdate));
//			}
//			if(StringHelper.isNotEmpty(enddate)){
//				updatecombo.setEnddate(sdf.parse(enddate));
//			}
//			updatecombo.setDisable(disable);
//			personalMessageService.merge(updatecombo);
		}else{
			PersonalMessage combo = new PersonalMessage();
//			combo.setName(name);
//			combo.setDescription(desc);
//			combo.setPrice(NumberHelper.string2Float(price,0));
//			combo.setInstruction(instruction);
//			if(StringHelper.isNotEmpty(startdate)){
//				combo.setStartdate(sdf.parse(startdate));
//			}
//			if(StringHelper.isNotEmpty(enddate)){
//				combo.setEnddate(sdf.parse(enddate));
//			}
//			combo.setDisable(disable);
//			personalMessageService.persist(combo);
		}
		String htmlString = "<script language=javascript> history.go(-1);</script>";
		writeJSON(response, htmlString);
	}

	@RequestMapping(value = "/operatePromotionCombo", method = { RequestMethod.POST, RequestMethod.GET })
	public void operatePromotionCombo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deletePromotionCombo(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
	}

	// 获取单条记录
	@RequestMapping(value = "/getPromotionCombo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPromotionCombo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		PersonalMessage promotionCombo = personalMessageService.get(NumberHelper.string2Int(id));
		JSONObject json = new JSONObject();
		if (null != promotionCombo && 0 != promotionCombo.getId()) {
			json.put("obj", promotionCombo);
			json.put("success", true);
			writeJSON(response, json);
		} else {
			json.put("success", false);
			writeJSON(response, json);
		}
	}
	
		// 给指定用户发系统通知
		@RequestMapping(value = "/sendmsgtouser")
		public void sendmsgtouser(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String userid = request.getParameter("userid");
			String msgcontent = request.getParameter("msgcontent");
			PersonalMessage personalmsg = new PersonalMessage();
			personalmsg.setContent(msgcontent);
			personalmsg.setUserid(NumberHelper.string2Int(userid));
			personalmsg.setType(3);
			personalmsg.setStatus(0);
			personalmsg.setSendtime(new Date());
			personalMessageService.persist(personalmsg);
			
			writeJSON(response, "{success:true}");
		}
		
}
