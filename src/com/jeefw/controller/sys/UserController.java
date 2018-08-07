package com.jeefw.controller.sys;
 
import java.io.IOException;
import java.io.OutputStream; 
import java.net.URLDecoder;
import java.text.SimpleDateFormat;  
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
import org.apache.shiro.crypto.hash.Sha256Hash;  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam; 

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.IndividualUser;
import com.jeefw.service.sys.AttachmentService;
import com.jeefw.service.sys.AuthorityService;
import com.jeefw.service.sys.IndividualUserService;
import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult; 

/**
 * 用户的控制层
 * @ 
 */
@Controller
@RequestMapping("/sys/usermanage")
public class UserController extends JavaEEFrameworkBaseController<IndividualUser> implements Constant {

	  
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private AuthorityService authorityService; 
	@Resource
	private IndividualUserService individualUserService;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	
	// 查询用户的表格，包括分页、搜索和排序 
	@RequestMapping(value = "/getIndividualUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void getIndividualUser(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		IndividualUser individualUser = new IndividualUser();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("email") && result.getString("op").equals("eq")) {
					individualUser.set$eq_email(result.getString("data"));
				}
				if (result.getString("field").equals("userName") && result.getString("op").equals("cn")) {
					individualUser.set$like_userName(result.getString("data"));
				} 
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				individualUser.setFlag("OR");
			} else {
				individualUser.setFlag("AND");
			}
		}
		individualUser.setFirstResult((firstResult - 1) * maxResults);
		individualUser.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		individualUser.setSortedConditions(sortedCondition);
		QueryResult<IndividualUser> queryResult = individualUserService.doPaginationQuery(individualUser);
		JqGridPageView<IndividualUser> individualUserListView = new JqGridPageView<IndividualUser>();
		individualUserListView.setMaxResults(maxResults);
		List<IndividualUser> individualUserWithSubList = queryResult.getResultList();
//		List<IndividualUser> individualUserWithSubList = individualUserService.queryIndividualUserCnList(queryResult.getResultList());
		individualUserListView.setRows(individualUserWithSubList);
		individualUserListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, individualUserListView);
		 
	}
	
   
	// 保存用户的实体Bean
	@RequestMapping(value = "/saveIndividualUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(IndividualUser entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			IndividualUser  individualUser = individualUserService.get(entity.getId()); 
			entity.setPassword(individualUser.getPassword()); 
			individualUserService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			//entity.setPassword(new MD5.crypt("123456")); // 初始化密码为123456
			entity.setPassword(new Sha256Hash("123456").toHex()); // 初始化密码为123456
			individualUserService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作用户的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateIndividualUser", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id"); 
		if (oper.equals("del")) {
			String[] ids = id.split(",");  
			deleteTzzUser(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String name = request.getParameter("name");
			String nickname = request.getParameter("username");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String idcardno = request.getParameter("idcardno");
			String birthday = request.getParameter("birthday");
			IndividualUser individualUser = null;
			if (oper.equals("edit")) {
				individualUser = individualUserService.get(Integer.valueOf(id));
			} 
			IndividualUser nicknameTzzUser = individualUserService.getByProerties("username", nickname);
			IndividualUser phoneTzzUser = individualUserService.getByProerties("phone", phone);
			IndividualUser emailTzzUser = individualUserService.getByProerties("email", email);
			if (StringUtils.isBlank(nickname) || StringUtils.isBlank(email)|| StringUtils.isBlank(phone)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写昵称和邮箱和手机号");
				writeJSON(response, result);
			} else if (null != emailTzzUser && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此邮箱已存在，请重新输入");
 				writeJSON(response, result);// /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]+$/
			}else if (null != emailTzzUser && !individualUser.getEmail().equalsIgnoreCase(email) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此邮箱已存在，请重新输入");
				writeJSON(response, result); 
			}else if (!Pattern.matches("^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\\.)+[A-Za-z0-9]+$", email) ) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此邮箱格式错误，请重新输入");
				writeJSON(response, result); 
			}else if (null != nicknameTzzUser && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此昵称已存在，请重新输入");
				writeJSON(response, result);
			} 
			else if (null != nicknameTzzUser && !individualUser.getUsername().equalsIgnoreCase(nickname) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此昵称已存在，请重新输入");
				writeJSON(response, result);
			} 
			else if (null != phoneTzzUser && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此手机号已存在，请重新输入");
				writeJSON(response, result);
			} else if (null != phoneTzzUser && !individualUser.getPhone().equalsIgnoreCase(phone) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此手机号已存在，请重新输入");
				writeJSON(response, result);
			} else if (!Pattern.matches("^(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$", phone)) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此手机号错误，请重新输入");
				writeJSON(response, result);
			}else {
				IndividualUser entity = new IndividualUser();
				entity.setUsername(nickname);
				entity.setName(name);
				entity.setGender(gender);
				entity.setEmail(email);
				entity.setPhone(phone);
				entity.setIdcardno(idcardno);
//				String aString=request.getParameter("totalAmout");
				
//				if(request.getParameter("totalAmout")==null)
//					entity.setTotalAmout(0);
//				else entity.setTotalAmout(Float.parseFloat(request.getParameter("totalAmout")));
				if (StringUtils.isNotBlank(birthday)) {
					 entity.setBirthday(dateFormat.parse(birthday));
				} 
			
				if (oper.equals("edit")) { 
					entity.setCreatetime(individualUser.getCreatetime());
					entity.setImage(individualUser.getImage());
					entity.setPassword(individualUser.getPassword()); 
					entity.setId(Integer.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					
					entity.setImage("/static/dist/img/photo.png");
					entity.setCreatetime(new Date());
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}
 
	// 删除用户
	@RequestMapping("/deleteIndividualUser")
	public void deleteTzzUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		 
		boolean flag = individualUserService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		} 
	}

	// 即时更新个人资料的字段
	@RequestMapping(value = "/updateIndividualUserField", method = { RequestMethod.POST, RequestMethod.GET })
	public void updateTzzUserField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.valueOf(request.getParameter("pk"));
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		if (name.equals("userName")) {
			individualUserService.updateByProperties("id", id, "userName", value);
		} else if (name.equals("sex")) {
			individualUserService.updateByProperties("id", id, "sex", Short.valueOf(value));
		} else if (name.equals("email")) {
			individualUserService.updateByProperties("id", id, "email", value);
		} else if (name.equals("phone")) {
			individualUserService.updateByProperties("id", id, "phone", value);
		} else if (name.equals("birthday")) {
			if (null != value) {
				individualUserService.updateByProperties("id", id, "birthday", dateFormat.parse(value));
			}
		}
	}
 
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
 
}
