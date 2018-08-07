package com.jeefw.controller.top;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.PersonalMessage;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.PersonalMessageService;
import com.jeefw.service.sys.TzzUserService;

import core.support.JqGridPageView;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;


/**
 * 个人消息的控制层
 */
@Controller
@RequestMapping("/top/personalmessage")
public class TopPersonalMessageController extends JavaEEFrameworkBaseController<PersonalMessage> implements Constant {
	@Resource
	PersonalMessageService personalMessageService;
	
	@Resource
	private TzzUserService tzzUserService;
	
	/**
	 * 查询用户的所有消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonalMessages", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPromotionParticipant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userphone = StringHelper.null2String(request.getParameter("userphone"));
		String openid = StringHelper.null2String(request.getParameter("openid"));
		String userid = StringHelper.null2String(request.getParameter("userid"));
		TzzUser tzzUser = null;
		if(StringHelper.isNotEmpty(userid)){
			tzzUser = tzzUserService.get(NumberHelper.string2Int(userid));
		}else{
			tzzUser = tzzUserService.getByProerties("phone", userphone);
		}
		//String userid = StringHelper.null2String(request.getParameter("userid"));
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("status,sendtime", "desc");
		sortedCondition.put("status", "asc");
		List<PersonalMessage> messagelist = personalMessageService.queryByProerties("userid", tzzUser.getId(),sortedCondition);
		JqGridPageView<PersonalMessage> authorityListView = new JqGridPageView<PersonalMessage>();
		authorityListView.setRows(messagelist);
		writeJSON(response, authorityListView);
	}
	
	/**
	 * 根据id查询消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonalMessageByid", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPersonalMessageByid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = StringHelper.null2String(request.getParameter("msgid"));
		PersonalMessage message = personalMessageService.get(NumberHelper.string2Int(id));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("message",  message);
		jsonObject.put("success",  true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 更新消息状态
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void updatePersonalMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = StringHelper.null2String(request.getParameter("id"));
		personalMessageService.updateByProperties("id", NumberHelper.string2Int(id), "status", 1);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result",  "success");
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 删除消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delPersonalMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public void delPersonalMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = StringHelper.null2String(request.getParameter("ids"));
		
		JSONObject jsonObject=new JSONObject();
		String[] idarr = ids.split(",");
		for(int i=0;i<idarr.length;i++){
			int messageid = NumberHelper.string2Int(idarr[i]);
			personalMessageService.deleteByPK(messageid);
			
		}
		jsonObject.put("result",  "success");
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 用户未读消息数量
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/unreadmsgnum")
	public void unreadmsgnum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userphone = StringHelper.null2String(request.getParameter("userphone"));
		String userid = StringHelper.null2String(request.getParameter("userid"));
		TzzUser tzzUser = null;
		if(StringHelper.isNotEmpty(userid)){
			tzzUser = tzzUserService.get(NumberHelper.string2Int(userid));
		}else{
			tzzUser = tzzUserService.getByProerties("phone", userphone);
			
		}
		JSONObject json = new JSONObject();
		if(tzzUser!=null){
			String [] keys = new String [2];
			keys[0] = "userid";
			keys[1] = "status";
			Object [] values = new Object[2];
			values[0] = tzzUser.getId();
			values[1] = 0;
			List<PersonalMessage> msgs = personalMessageService.queryByProerties(keys, values);
			
			json.put("unreadmsgnum", msgs.size());
		}else{
			json.put("unreadmsgnum", 0);
		}
		json.put("success", true);
		writeJSON(response, json);
	}
			
	/**
	 * 查看消息详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewMsgDetail")
	public void viewMsgDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = StringHelper.null2String(request.getParameter("msgid"));
		PersonalMessage msg = personalMessageService.get(NumberHelper.string2Int(id));
		JSONObject jsonObject=new JSONObject();
		if(msg!=null && msg.getStatus()==0){
			msg.setStatus(1);
			personalMessageService.merge(msg);
			jsonObject.put("result",  1);
		}
		jsonObject.put("success",  true);
		writeJSON(response, jsonObject);
	}
	
}
