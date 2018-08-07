package com.jeefw.controller.top;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Association;
import com.jeefw.model.sys.CooperationColleges;
import com.jeefw.model.sys.Emailphone;
import com.jeefw.model.sys.IndividualUser;
import com.jeefw.model.sys.Information;
import com.jeefw.model.sys.PersonalMessage;
import com.jeefw.model.sys.TzzActivityArea;
import com.jeefw.model.sys.TzzAnswer;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzIndexMenu;
import com.jeefw.model.sys.TzzIndexShuffing;
import com.jeefw.model.sys.TzzJianjie;
import com.jeefw.model.sys.TzzQuestion;
import com.jeefw.model.sys.TzzSafetyNews;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.model.sys.Zizeng;
import com.jeefw.service.sys.AssociationService;
import com.jeefw.service.sys.CooperationCollegesService;
import com.jeefw.service.sys.EmailphoneService;
import com.jeefw.service.sys.IndividualUserService;
import com.jeefw.service.sys.InformationService;
import com.jeefw.service.sys.PersonalMessageService;
import com.jeefw.service.sys.TzzActivityAreaService;
import com.jeefw.service.sys.TzzAnswerService;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzIndexMenuService;
import com.jeefw.service.sys.TzzIndexShuffingService;
import com.jeefw.service.sys.TzzJianjieService;
import com.jeefw.service.sys.TzzNewsService;
import com.jeefw.service.sys.TzzQuestionService;
import com.jeefw.service.sys.TzzSafetyNewsService;
import com.jeefw.service.sys.TzzUserService;
import com.jeefw.service.sys.TzzUserSetvipService;
import com.jeefw.service.sys.ZizengService;
import com.sun.jmx.snmp.Timestamp;

import cn.jiguang.commom.utils.StringUtils;
import core.support.JqGridPageView;
import core.util.HtmlUtils;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.PTUtils;
import core.util.Sms;
import core.util.StringHelper;
import net.sf.json.JSONObject;
import sms.SingleSendSms;

/**
 * 用户的控制层 @
 */
@Controller
@RequestMapping("/jsp")
public class TopIndexController extends JavaEEFrameworkBaseController<TzzUser> implements Constant {

	@Resource
	private TzzQuestionService tzzQuestionService;
	@Resource
	private TzzUserService tzzUserService;
	@Resource
	private TzzDictionaryService tzzDictionaryService;
	@Resource
	private InformationService informationService;
	@Resource
	private TzzJianjieService tzzJianjieService;
	@Resource
	private TzzAnswerService tzzAnswerService;
	@Resource
	private EmailphoneService emailphoneService;
	@Resource
	private TzzUserSetvipService tzzUserSetvipService;
	@Resource
	private ZizengService zizengService;
	@Resource
	private TzzNewsService tzzNewsService;
	@Resource
	TzzSafetyNewsService safetyNewsService;
	@Resource
	TzzActivityAreaService activityAreaService;
	@Resource
	private TzzIndexShuffingService tzzIndexShuffingService;
	@Resource
	private PersonalMessageService personalMessageService;
	@Resource
	private TzzIndexMenuService tzzIndexMenuService;
	@Resource
	private AssociationService associationService;
	@Resource
	private CooperationCollegesService cooperationCollegesService;
	@Resource
	private IndividualUserService individualUserService;


	/**
	 * 登录 /jsp/login
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String msg = "";
		TzzUser tzzUser = null;

		if (tzzUserService.getByProerties("username", name) != null) {
			tzzUser = tzzUserService.getByProerties("username", name);
		}
		else {
			msg = "用户不存在";
		}
		JSONObject json = new JSONObject();
		if (tzzUser != null) {
			String npwd = new Sha256Hash(pwd).toHex();
			if (npwd.equals(tzzUser.getPassword())) {
				request.setAttribute("msg", msg);
				request.getSession().setAttribute("tzzuser", tzzUser);
				int num = tzzNewsService.getnewsWDnum(tzzUser.getId(), tzzUser.getCreateTime());
				request.getSession().setAttribute("msgnum", num);
				request.getSession().setAttribute("userid", tzzUser.getId());
				request.getSession().setAttribute("userphone", tzzUser.getPhone());
				request.getSession().setAttribute("username", tzzUser.getUsername());
				request.getSession().setAttribute("utype", tzzUser.getUType());
				if(tzzUser.getSchoolid()!=null){
					request.getSession().setAttribute("schoolid", tzzUser.getSchoolid().toString());
				}
				if(tzzUser.getCampsiteid()!=null){
					request.getSession().setAttribute("campsiteid", tzzUser.getCampsiteid().toString());
				}
				String usertype = tzzUser.getuType();
				if(usertype.equals("5")){
					String [] keys = new String [2];
					keys[0] = "phone";
					keys[1] = "uType";
					Object [] values = new Object[2];
					values[0] = tzzUser.getUsername();
					values[1] = "1";
//						TzzUser tzzUserSon = tzzUserService.getByProerties(keys, values);
//						if(tzzUserSon.getSchoolid()!=null){
//							request.getSession().setAttribute("schoolid", tzzUserSon.getSchoolid().toString());
//						}
					List<TzzUser> userSons = tzzUserService.queryByProerties(keys, values);
					if(userSons!=null){
						if(userSons.size()==1){
							TzzUser tzzUserSon = userSons.get(0);
								if(tzzUserSon.getSchoolid()!=null){
									request.getSession().setAttribute("schoolid", tzzUserSon.getSchoolid().toString());
									request.getSession().setAttribute("studentid", tzzUserSon.getId());
								}
						}else if(userSons.size()>1){
							json.put("studentnum", userSons.size());
						}
					}
				}
				Cookie userCookie=new Cookie("userinfo",name+"_"+pwd); 

	             userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
	             userCookie.setPath("/");
	             response.addCookie(userCookie); 
				json.put("usertype", usertype);
				//request.getRequestDispatcher("/jsp/parent/parent_index.jsp").forward(request, response);
				//return;
			} else {
				msg = "密码错误";
			}
		}
//		}
//		request.setAttribute("msg", msg);
//		request.setAttribute("name", name);
//		request.getRequestDispatcher("/jsp/loginpage.jsp").forward(request, response);
		
		json.put("success", true);
		json.put("msg", msg);
		writeJSON(response, json);
	}
	
	/**
	 * 登录 /jsp/phonelogin
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/phonelogin", method = { RequestMethod.POST })
	public void phonelogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long validateCodeDate = (Long) request.getSession().getAttribute("validateCodeDate");
		Integer validateCode = (Integer) request.getSession().getAttribute("validateCode");
		String validatePhone = (String) request.getSession().getAttribute("phone");
		// System.out.println("oldcode="+oldcode);
		String phone = request.getParameter("name");
		String newcode = request.getParameter("code");
		
		String ref = request.getParameter("ref");
		
		JSONObject json = new JSONObject();
		
		String msg = "验证码验证失败！";
		Boolean isok = false;
		if (null == phone || "".equals(phone)) {
			msg = "请输入手机号！";
//			msg = "获取验证码失败！";
		} else if (!StringUtils.isMobileNumber(phone)) {
			msg = "请正确输入手机号！";
		} else if (!validatePhone.equals(phone)) {
			msg = "验证码同验证手机号码不一致！";
		} else {
			long currenttime = (new Date()).getTime();
			if (null!=validateCodeDate) {
				
				if(currenttime - validateCodeDate>120*1000){
					msg = "验证码已过期！";
				}else{
					if(newcode.equals(validateCode.toString())){
						isok = true;
						msg = "验证码验证通过！";
						TzzUser tzzUser = tzzUserService.getByProerties("phone", phone);
						if ( null == tzzUser) {
							msg = "用户不存在";
							//response.sendRedirect("home/wx_register_base.jsp");
							json.put("isExsit", false);
						} else {
							json.put("isExsit", true);
							json.put("tzzuser", tzzUser.getBaseInfo());
							request.getSession().setAttribute("tzzuser", tzzUser);
							if(StringHelper.isNotEmpty(ref)){
								json.put("ref", ref);
							}
						}
						
					}
				}
			} 
			
		}
		
		json.put("success", isok);
		json.put("msg", msg);
		writeJSON(response, json);
	}
	
	/**
	 * 登录 /jsp/phonelogin
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/phonevolidate", method = { RequestMethod.POST })
	public void phonevolidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long validateCodeDate = (Long) request.getSession().getAttribute("validateCodeDate");
		// System.out.println("oldcode="+oldcode);
		String phone = request.getParameter("phone");
		
		String msg = "";
		Boolean isok = false;
		if (null == phone || "".equals(phone)) {
			msg = "请输入手机号！";
//			msg = "获取验证码失败！";
		} else if (!StringUtils.isMobileNumber(phone)) {
			msg = "请正确输入手机号！";
		} else {
			long currenttime = (new Date()).getTime();
			if (null!=validateCodeDate) {
				
				if(currenttime - validateCodeDate>120*1000){
					int newcode = (int)(Math.random()*(9999-1000+1))+1000;
					request.getSession().setAttribute("phone", phone);
					request.getSession().setAttribute("validateCode", newcode);
					request.getSession().setAttribute("validateCodeDate", currenttime);
					//SingleSendSms.smsSend(phone, "SMS_56665274","{\"code\":\""+newcode+"\",\"product\":\"[营才网]\"}");
					isok = true;
					msg = "验证码发送成功！";
					System.out.println("newcode========"+ newcode);
				}else{
					long time = 120 - (currenttime - validateCodeDate)/1000;
					msg = "请等待"+time+"秒后再试！";
				}
			} else {
				int newcode = (int)(Math.random()*(9999-1000+1))+1000;
				request.getSession().setAttribute("phone", phone);
				request.getSession().setAttribute("validateCode", newcode);
				request.getSession().setAttribute("validateCodeDate", currenttime);
				//SingleSendSms.smsSend(phone, "SMS_56665274","{\"code\":\""+newcode+"\",\"product\":\"[营才网]\"}");
				isok = true;
				msg = "验证码发送成功！";
				System.out.println("newcode========"+ newcode);
			}
			
		}
		JSONObject json = new JSONObject();
		json.put("success", isok);
		json.put("msg", msg);
		writeJSON(response, json);
	}
	
	/**
	 * 检查验证码 /jsp/phonecheck
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/phonecheck", method = { RequestMethod.POST })
	public void phonecheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long validateCodeDate = (Long) request.getSession().getAttribute("validateCodeDate");
		Integer validateCode = (Integer) request.getSession().getAttribute("validateCode");
		String validatePhone = (String) request.getSession().getAttribute("phone");
		// System.out.println("oldcode="+oldcode);
		String phone = request.getParameter("phone");
		String newcode = request.getParameter("code");
		
		String msg = "验证码验证失败！";
		Boolean isok = false;
		if (null == phone || "".equals(phone)) {
			msg = "请输入手机号！";
//			msg = "获取验证码失败！";
		} else if (!StringUtils.isMobileNumber(phone)) {
			msg = "请正确输入手机号！";
		} else if (!validatePhone.equals(phone)) {
			msg = "验证码同验证手机号码不一致！";
		} else {
			long currenttime = (new Date()).getTime();
			if (null!=validateCodeDate) {
				
				if(currenttime - validateCodeDate>120*1000){
					msg = "验证码已过期！";
				}else{
					if(newcode.equals(validateCode.toString())){
						isok = true;
						msg = "验证码验证通过！";
					}
				}
			} 
			
		}
		JSONObject json = new JSONObject();
		json.put("success", isok);
		json.put("msg", msg);
		writeJSON(response, json);
	}

	/**
	 * 微信登录验证 /jsp/login
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixin/volidate", method = { RequestMethod.GET })
	public void weixinVolidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		String accessToken = request.getParameter("accessToken");
		String refreshToken = request.getParameter("refreshToken");
		int expiresIn = Integer.parseInt(request.getParameter("expiresIn"));
		String msg = "";
		if (openid == null || openid.equals("")) {
			msg = "微信登录验证失败！";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		} else {
			// openid是否保存
			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				request.setAttribute("openid", openid);
				request.setAttribute("accessToken", accessToken);
				request.setAttribute("refreshToken", refreshToken);
				request.setAttribute("expiresIn", expiresIn);
				request.getRequestDispatcher("/jsp/weixin_login_bind.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("tzzuser", tzzUser);
				request.getRequestDispatcher("/jsp/cards_weixin.jsp").forward(request, response);
				// if(refreshToken.equals(tzzUser.getRefreshToken())){
				//// 刷新token
				// if(!accessToken.equals(tzzUser.getAccessToken())){
				// UserAccessToken userAccessToken =
				// WeixinUtil.refreshAccessToken(Constants.APPID, refreshToken);
				// tzzUser.setAccessToken(userAccessToken.getAccessToken());
				// tzzUserService.persist(tzzUser);
				// }
				// }else{
				//// 重新授权
				// request.getRequestDispatcher("/jsp/author").forward(request,response);
				// }

			}

		}
	}
	
	/**
	 * 微信登录验证 /jsp/login
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/home/userVolidate", method = { RequestMethod.GET })
	public void userVolidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		String redrictUrl = request.getParameter("redricturl");
		String msg = "";
		if (openid == null || openid.equals("")) {
			msg = "微信登录验证失败！";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/jsp/home/wx_login_w.jsp").forward(request, response);
		} else {
			// openid是否保存
			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				request.setAttribute("openid", openid);
				request.getRequestDispatcher("/jsp/home/wx_mobile_bind.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("tzzuser", tzzUser);
				if(StringHelper.isEmpty(redrictUrl))
					request.getRequestDispatcher("/jsp/home/wx_user.jsp").forward(request, response);
				else
					request.getRequestDispatcher(redrictUrl).forward(request, response);
			}

		}
	}
	
	/**
	 * 微信用户登录验证 
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/home/wxUserVolidate", method = { RequestMethod.POST })
	public void wxUserVolidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		JSONObject jsonObj = new JSONObject();
		if (openid == null || openid.equals("")) {
			jsonObj.put("success", false);
			jsonObj.put("resultCode", 1);
			jsonObj.put("msg", "微信登录验证失败！");
			request.getRequestDispatcher("/jsp/home/wx_login_w.jsp").forward(request, response);
		} else {
			// openid是否保存
			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				jsonObj.put("success", false);
				jsonObj.put("resultCode", 2);
			} else {
				request.getSession().setAttribute("tzzuser", tzzUser);
				jsonObj.put("success", true);
				jsonObj.put("userphone", tzzUser.getPhone());
				jsonObj.put("resultCode", 3);
			}

		}
		writeJSON(response, jsonObj);
	}


	/**
	 * 退出登录 /jsp/logout
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.POST, RequestMethod.GET })
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("tzzuser");
		index(request, response);
	}

	/*
	 * 更换头像。更换昵称， 密码等。
	 */
	@RequestMapping(value = "/ModifyPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public void ModifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			TzzUser tzzUser = (TzzUser) request.getSession().getAttribute("tzzuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else {
				String currentPwd = request.getParameter("currentPwd");
				String newPwd = request.getParameter("newPwd");
				String newPwdConfirm = request.getParameter("newPwdConfirm");
				if (currentPwd != null) {
					String pwdDB = tzzUser.getPassword();
					currentPwd = new Sha256Hash(currentPwd).toHex();
					if (!pwdDB.equals(currentPwd)) {
						jsonObj.put("err", 2);
						jsonObj.put("msg", "原密码不正确！");
					}
				} else {
					jsonObj.put("err", 1);
					jsonObj.put("msg", "原密码不能为空！");
				}
				if (newPwd != null && newPwdConfirm != null && newPwdConfirm.equals(newPwd)) {
					TzzUser tzzUserTmp = tzzUserService.get(tzzUser.getId());
					tzzUserTmp.setPassword(new Sha256Hash(newPwd).toHex());
					tzzUserService.merge(tzzUserTmp);
					jsonObj.put("err", 0);
					jsonObj.put("msg", "密码修改成功！");
				} else {
					jsonObj.put("err", 3);
					jsonObj.put("msg", "两次输入的新密码不一致！");
				}
			}
			writeJSON(response, jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("err", 4);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
	/*
	 * 修改密码
	 */
	@RequestMapping(value = "/modifyUserPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public void modifyUserPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		String confirmpwd = request.getParameter("confirmpwd");
		String userid = request.getParameter("userid");
		try {
			if(StringHelper.isNotEmpty(userid)){
				TzzUser tzzUser =  tzzUserService.get(NumberHelper.string2Int(userid));
				if (tzzUser == null) {
					request.getRequestDispatcher("/jsp/loginpage.jsp").forward(request, response);
				} else {
					if (StringHelper.isNotEmpty(oldpwd)) {
						String pwdDB = tzzUser.getPassword();
						oldpwd = new Sha256Hash(oldpwd).toHex();
						if (!pwdDB.equals(oldpwd)) {
							jsonObj.put("err", 2);
							jsonObj.put("msg", "当前密码不正确！");
						}else if (newpwd != null && confirmpwd != null && newpwd.equals(confirmpwd)){
							tzzUser.setPassword(new Sha256Hash(newpwd).toHex());
							tzzUserService.merge(tzzUser);
							jsonObj.put("err", 0);
							jsonObj.put("msg", "密码修改成功！");
						}else {
							jsonObj.put("err", 3);
							jsonObj.put("msg", "两次输入的新密码不一致！");
						}
					} else{
						jsonObj.put("err", 1);
						jsonObj.put("msg", "原密码不能为空！");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("err", 4);
			jsonObj.put("msg", "操作失败！");
		}
		writeJSON(response, jsonObj);
	}

	/**
	 * 注册验证码 /jsp/yanzhengma
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/yanzhengma", method = { RequestMethod.POST, RequestMethod.GET })
	public void yanzhengma(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oyzm = request.getParameter("yzm");
		JSONObject jo = new JSONObject();
		if (oyzm.equals((String) request.getSession().getAttribute("validateCode"))) {
			jo.put("err", 0);
		} else {
			jo.put("err", 1);
		}
		writeJSON(response, jo);
	}

	/**
	 * 动态验证码（短信）注册 /jsp/dongtaima
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/dongtaima", method = { RequestMethod.POST, RequestMethod.GET })
	public void dongtaima(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		Emailphone ep = emailphoneService.getByProerties("pstate", "1");
		JSONObject jsonObj = new JSONObject();
		int random = (int) (Math.random() * 1000000);
		for (int i = 0; i != 1;) {
			if (random < 100000) {
				random = (int) (Math.random() * 1000000);
			} else {
				i = 1;
			}
		}

		System.out.println(random);
		request.getSession().setAttribute("dongtaima", random + "");
		request.getSession().setAttribute("dongtaimatime", new Date());
		String content = "【冒险家】验证码" + random + "（有效期是2分钟）。您正在使用手机号对您的冒险家账户进行操作，如非本人操作，请忽略本短信。";
		String msg = Sms.sendsms(ep.getPhone(), ep.getSmspwd(), ep.getChannel(), content, phone);// 发短信
		// String msg="0:12";
		String[] msgs = msg.split(":");
		System.out.println(msgs[0] + "***" + msgs[1]);
		if (msgs[0] == "error") {
			System.out.println(msgs[1]);
			jsonObj.put("err", msgs[1]);
			writeJSON(response, jsonObj);
		} else {
			jsonObj.put("err", 0);
			writeJSON(response, jsonObj);
		}
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/zhuce", method = { RequestMethod.POST, RequestMethod.GET })
	public void zhuce(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String phone = request.getParameter("phone");
		String uType = request.getParameter("uType");
		String realname = request.getParameter("realname");  
		String school = request.getParameter("school");  
		String class_grade = request.getParameter("class_grade");  
		String campsite = request.getParameter("campsite");  
//		String yanzhengma = request.getParameter("yanzhengma");
//		String oldyzm = (String) request.getSession().getAttribute("validateCode");
//
//		String openid = request.getParameter("openid");
//		String accessToken = request.getParameter("accessToken");
//		String refreshToken = request.getParameter("refreshToken");
//		int expiresIn = Integer
//				.parseInt(request.getParameter("expiresIn") == null ? "0" : request.getParameter("expiresIn"));

//		if (!yanzhengma.equals(oldyzm)) {
//			jsonObject.put("err", 2);
//			jsonObject.put("msg", "验证码错误");
//		} else
//			if (tzzUserService.getByProerties("phone", phone) != null) {
//			jsonObject.put("err", 4);
//			jsonObject.put("msg", "该手机号已注册");
//			// request.getSession().removeAttribute("dongtaimatime");
//			// request.getSession().removeAttribute("dongtaima");
//		} else 
			if (tzzUserService.getByProerties("username", username) != null) {
			jsonObject.put("err", 4);
			jsonObject.put("msg", "该账号已注册");
		} else {
			TzzUser tzzUser = new TzzUser();
			tzzUser.setRealname(realname);
			tzzUser.setUsername(username);
			tzzUser.setPassword(new Sha256Hash(pwd).toHex());
//			if(!uType.equals("1")){
//				tzzUser.setPhone(phone);
//			}
			tzzUser.setPhone(phone);
			tzzUser.setLevel("0");
			tzzUser.setImage("/static/dist/img/photo.png");
			tzzUser.setSex("0");
			tzzUser.setViptype("1");
			tzzUser.setUType(uType);
			Zizeng zizeng = new Zizeng();
			zizengService.persist(zizeng);
			tzzUser.setNickname("tzz" + zizeng.getId() * 12 + phone.substring(8, 11));
			tzzUser.setTotalAmout(0);
			tzzUser.setCreateTime(new Date());
			tzzUser.setSchoolid(NumberHelper.string2Int(school));
			tzzUser.setClass_grade(NumberHelper.string2Int(class_grade));
			if(!StringHelper.isEmpty(uType)&&(uType.equals("2")||uType.equals("3"))&&!StringHelper.isEmpty(campsite)){
				tzzUser.setCampsiteid(NumberHelper.string2Int(campsite));
			}
			
			if(!StringHelper.isEmpty(uType)&&uType.equals("1")&&!StringHelper.isEmpty(phone)){
				if (tzzUserService.getByProerties("username", phone) == null) {
					TzzUser tzzUser_p = new TzzUser();
					tzzUser_p.setUsername(phone);
					tzzUser_p.setRealname(phone);
					tzzUser_p.setPassword(new Sha256Hash(phone.substring(5, 11)).toHex());
					tzzUser_p.setPhone(phone);
					tzzUser_p.setLevel("0");
					tzzUser_p.setImage("/static/dist/img/photo.png");
					tzzUser_p.setSex("0");
					tzzUser_p.setViptype("1");
					tzzUser_p.setUType("5");
					Zizeng zizeng_p = new Zizeng();
					zizengService.persist(zizeng_p);
					tzzUser_p.setNickname("tzz" + zizeng_p.getId() * 12 + phone.substring(8, 11));
					tzzUser_p.setTotalAmout(0);
					tzzUser_p.setCreateTime(new Date());
					tzzUserService.persist(tzzUser_p);
				}
			}
			
//			tzzUser.setAccessToken(accessToken);
//			tzzUser.setRefreshToken(refreshToken);
//			tzzUser.setExpiresIn(expiresIn);
//			tzzUser.setOpenid(openid);

			tzzUserService.persist(tzzUser);
			request.getSession().setAttribute("tzzuser", tzzUser);
			jsonObject.put("err", 0);
			//request.getRequestDispatcher("/jsp/loginpage.jsp").forward(request, response);
		}
//		request.getRequestDispatcher("/jsp/registerpage.jsp").forward(request, response);
		writeJSON(response, jsonObject);
	}
	/**
	 * 微信用户注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String image = request.getParameter("image");
		String sex = request.getParameter("sex");
		String pwd = request.getParameter("pwd");
		String yanzhengma = request.getParameter("yanzhengma");
		String oldyzm = (String) request.getSession().getAttribute("validateCode");

		String babyName = request.getParameter("babyName");
		Integer babySex = Integer.parseInt(request.getParameter("babySex"));
		String s = request.getParameter("babyBirthday");
		String s1 = request.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date babyBirthday = sdf.parse(s);

		String openid = request.getParameter("openid");
		String accessToken = request.getParameter("accessToken");
		String refreshToken = request.getParameter("refreshToken");
		int expiresIn = Integer
				.parseInt(request.getParameter("expiresIn") == null ? "0" : request.getParameter("expiresIn"));

		String puserphone =  request.getParameter("puserphone");
		
		if (!yanzhengma.equals(oldyzm)) {
			jsonObject.put("err", 2);
			jsonObject.put("msg", "验证码错误");
		} else if (tzzUserService.getByProerties("phone", phone) != null) {
			jsonObject.put("err", 4);
			jsonObject.put("msg", "该手机号已注册");
			// request.getSession().removeAttribute("dongtaimatime");
			// request.getSession().removeAttribute("dongtaima");
		} else {
			TzzUser tzzUser = new TzzUser();
			tzzUser.setNickname(name);
			tzzUser.setPassword(new Sha256Hash(pwd).toHex());
			tzzUser.setPhone(phone);
			tzzUser.setLevel("0");
			tzzUser.setImage(image);
			tzzUser.setSex(sex);
			if(StringHelper.isNotEmpty(s1))
				tzzUser.setBirthday(sdf.parse(s1));
			tzzUser.setViptype("1");
			tzzUser.setUType("0");
			Zizeng zizeng = new Zizeng();
			zizengService.persist(zizeng);
			//tzzUser.setNickname("tzz" + zizeng.getId() * 12 + phone.substring(8, 11));
			tzzUser.setTotalAmout(0);
			tzzUser.setCreateTime(new Date());
			tzzUser.setBabyName(babyName);
			tzzUser.setBabySex(babySex);
			tzzUser.setBabyBirthday(babyBirthday);
			tzzUser.setAccessToken(accessToken);
			tzzUser.setRefreshToken(refreshToken);
			tzzUser.setExpiresIn(expiresIn);
			tzzUser.setOpenid(openid);
			if(StringHelper.isNotEmpty(puserphone)){
				TzzUser puser =  tzzUserService.getByProerties("phone", puserphone);
				tzzUser.setPuserid(puser.getId());
			}

			tzzUserService.persist(tzzUser);
//			给上线添加积分
//			UserFans userFans = userFansService.getByProerties("openid", openid);
//			if(null!=userFans)
//				tzzUserCreditRecordService.updateUserCredit(userFans.getPopenid(), "ZHUCE");
			
			//用户注册欢迎通知
			PersonalMessage registermsg = new PersonalMessage();
			registermsg.setUserid(tzzUser.getId());
			registermsg.setType(1);
			registermsg.setStatus(0);
			registermsg.setSendtime(new Date());
			registermsg.setContent("恭喜你注册成为亲子淘会员，我们会定期推出精彩活动推荐给您，给您的孩子一个参加勇敢冒险和探索乐趣的平台。");
			personalMessageService.persist(registermsg);
			
			request.getSession().setAttribute("tzzuser", tzzUser);
			jsonObject.put("err", 0);
		}

		writeJSON(response, jsonObject);
	}
	
	/**
	 * 个人用户注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/individualRegister", method = { RequestMethod.POST })
	public void individualRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String phone = request.getParameter("phone");
		String pwd = request.getParameter("pwd");
		String yanzhengma = request.getParameter("yanzhengma");
		String oldyzm = (String) request.getSession().getAttribute("validateCode");
		
		if (!yanzhengma.equals(oldyzm)) {
			jsonObject.put("err", 2);
			jsonObject.put("msg", "验证码错误");
		} else if (individualUserService.getByProerties("phone", phone) != null) {
			jsonObject.put("err", 4);
			jsonObject.put("msg", "该手机号已注册");
		} else {
			IndividualUser individualUser = new IndividualUser();
			individualUser.setPhone(phone);
			individualUser.setPassword(new Sha256Hash(pwd).toHex());
			Zizeng zizeng = new Zizeng();
			zizengService.persist(zizeng);
			individualUserService.persist(individualUser);
			
			request.getSession().setAttribute("user", individualUser);
			jsonObject.put("err", 0);
		}
		
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 用户个人信息修改
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST })
	public void modifyUserInfo(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> list = multipartRequest.getFiles("file");
		String userid = request.getParameter("userid");
		String phone = request.getParameter("phone");
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		TzzUser tzzuser =null;
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(userid)){
			tzzuser = tzzUserService.get(NumberHelper.string2Int(userid));
			if(tzzuser!=null){
				if (!phone.equals(tzzuser.getPhone())&&tzzUserService.getByProerties("phone", phone) != null) {
					jsonObject.put("err", 4);
					jsonObject.put("msg", "该手机号已注册");
					// request.getSession().removeAttribute("dongtaimatime");
					// request.getSession().removeAttribute("dongtaima");
				}else{
					String imgpath = "";
					for(int i=0;i<list.size();i++){
						// 得到上传的文件
						MultipartFile mFile = list.get(i);
						// 得到上传服务器的路径
						String path = request.getSession().getServletContext()
								.getRealPath("/WEB-INF/upload/");
						// 得到上传的文件的文件名
						String originalFilename = mFile.getOriginalFilename();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
						String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
						+ originalFilename.substring(originalFilename.lastIndexOf("."));
						File filePath = new File(
								getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ")
								.replace("/WEB-INF/classes/", "/static/upload/img/headimg/" + sdf.format(new Date())));
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						//file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
						String destinationFilePath = "/static/upload/img/headimg/" + sdf.format(new Date()) + "/" + fileName;
						imgpath +=destinationFilePath;
						if(i<list.size()-1){
							imgpath +=",";
						}
						InputStream inputStream = mFile.getInputStream();
						byte[] b = new byte[1048576];
						int length = inputStream.read(b);
						//		  path += "\\" + fileName;
						//		  // 文件流写到服务器端
						FileOutputStream outputStream = new FileOutputStream(filePath.getAbsolutePath() + "\\" + fileName);
						outputStream.write(b, 0, length);
						inputStream.close();
						outputStream.close();
					}
					tzzuser.setRealname(realname);
					tzzuser.setImage(imgpath);
					tzzuser.setPhone(phone);
					tzzuser.setSex(sex);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(StringHelper.isNotEmpty(birthday)){
						tzzuser.setBirthday(sdf.parse(birthday));
					}
					tzzUserService.merge(tzzuser);
					request.getSession().setAttribute("tzzuser", tzzuser);
					jsonObject.put("err", 0);
				}
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}

	/**
	 * 微信用户注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateuserinfo", method = { RequestMethod.POST })
	public void updateuserinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String id = request.getParameter("id");
		String openid = request.getParameter("openid");
		String phone = request.getParameter("phone");
		TzzUser tzzUser = (TzzUser)request.getSession().getAttribute("tzzuser");
		if(null==tzzUser){
			if(null != id)
				tzzUser = tzzUserService.getByProerties("id", Integer.parseInt(id));
			else if(null != phone)
				tzzUser = tzzUserService.getByProerties("phone", phone);
			else if(null != openid&&!"null".equals(openid))
				tzzUser = tzzUserService.getByProerties("openid", openid);
		}
		
		String name = request.getParameter("name");
//		String phone = request.getParameter("phone");
//		String image = request.getParameter("image");
		String sex = request.getParameter("sex");

//		String babyName = request.getParameter("babyName");
//		Integer babySex = Integer.parseInt(request.getParameter("babySex"));
//		String s = request.getParameter("babyBirthday");
		String s1 = request.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date babyBirthday = sdf.parse(s);
		
		String children = request.getParameter("children");

		if (!phone.equals(tzzUser.getPhone())&&tzzUserService.getByProerties("phone", phone) != null) {
			jsonObject.put("err", 4);
			jsonObject.put("msg", "该手机号已注册");
			// request.getSession().removeAttribute("dongtaimatime");
			// request.getSession().removeAttribute("dongtaima");
		} else {
			tzzUser.setNickname(name);
			tzzUser.setPhone(phone);
//			tzzUser.setImage(image);
			tzzUser.setSex(sex);
			if(StringHelper.isNotEmpty(s1))
				tzzUser.setBirthday(sdf.parse(s1));
			tzzUser.setCreateTime(new Date());
//			tzzUser.setBabyName(babyName);
//			tzzUser.setBabySex(babySex);
//			tzzUser.setBabyBirthday(babyBirthday);
			tzzUser.setChildren(children);
			
			tzzUserService.merge(tzzUser);
			request.getSession().setAttribute("tzzuser", tzzUser);
			jsonObject.put("err", 0);
		}

		writeJSON(response, jsonObject);
	}
	
	/**
	 * 手机用户注册
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/registernew", method = { RequestMethod.POST })
	public void registernew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String image = request.getParameter("image");
		String sex = request.getParameter("sex");
		String pwd = request.getParameter("pwd");
		String yanzhengma = request.getParameter("yanzhengma");
		String oldyzm = (String) request.getSession().getAttribute("validateCode");

		String babyName = request.getParameter("babyName");
		Integer babySex = Integer.parseInt(request.getParameter("babySex"));
		String s = request.getParameter("babyBirthday");
		String s1 = request.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date babyBirthday = sdf.parse(s);

		String openid = request.getParameter("openid");
		String accessToken = request.getParameter("accessToken");
		String refreshToken = request.getParameter("refreshToken");
		int expiresIn = Integer
				.parseInt(request.getParameter("expiresIn") == null ? "0" : request.getParameter("expiresIn"));

		if (!yanzhengma.equals(oldyzm)) {
			jsonObject.put("err", 2);
			jsonObject.put("msg", "验证码错误");
		} else if (tzzUserService.getByProerties("phone", phone) != null) {
			jsonObject.put("err", 4);
			jsonObject.put("msg", "该手机号已注册");
			// request.getSession().removeAttribute("dongtaimatime");
			// request.getSession().removeAttribute("dongtaima");
		} else {
			TzzUser tzzUser = new TzzUser();
			tzzUser.setNickname(name);
			tzzUser.setPassword(new Sha256Hash(pwd).toHex());
			tzzUser.setPhone(phone);
			tzzUser.setLevel("0");
			tzzUser.setImage(image);
			tzzUser.setSex(sex);
			if(StringHelper.isNotEmpty(s1))
				tzzUser.setBirthday(sdf.parse(s1));
			tzzUser.setViptype("1");
			tzzUser.setUType("0");
			Zizeng zizeng = new Zizeng();
			zizengService.persist(zizeng);
			//tzzUser.setNickname("tzz" + zizeng.getId() * 12 + phone.substring(8, 11));
			tzzUser.setTotalAmout(0);
			tzzUser.setCreateTime(new Date());
			tzzUser.setBabyName(babyName);
			tzzUser.setBabySex(babySex);
			tzzUser.setBabyBirthday(babyBirthday);
			tzzUser.setAccessToken(accessToken);
			tzzUser.setRefreshToken(refreshToken);
			tzzUser.setExpiresIn(expiresIn);
			tzzUser.setOpenid(openid);

			tzzUserService.persist(tzzUser);
//			给上线添加积分
//			UserFans userFans = userFansService.getByProerties("openid", openid);
//			if(null!=userFans)
//			tzzUserCreditRecordService.updateUserCredit(userFans.getPopenid(), "ZHUCE");
			
			if(tzzUser.getFromUser()!=null &&tzzUser.getFromUser()>0){
				TzzUser tzzuserp = tzzUserService.get(tzzUser.getFromUser());
			}
			
			request.getSession().setAttribute("tzzuser", tzzUser);
			jsonObject.put("err", 0);
		}

		writeJSON(response, jsonObject);
	}
	
	
	/**
	 * 微信轮播图
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIndexShuffingList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getIndexShuffingList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("sort", "asc");
		List<TzzIndexShuffing> shuffings = tzzIndexShuffingService.queryByProerties("state", "1",sortedCondition);
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("result", reservations.get(0));
//		writeJSON(response, jsonObject);
		JqGridPageView<TzzIndexShuffing> shuffingListView = new JqGridPageView<TzzIndexShuffing>();
		shuffingListView.setRows(shuffings);
		shuffingListView.setRecords(shuffings.size());
		writeJSON(response, shuffingListView);
	}
	
	
	/**
	 * 协会链接
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAssociationList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAssociationList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("id", "asc");
		List<Association> shuffings = associationService.queryByProerties("isdelete", 0,sortedCondition);
		JqGridPageView<Association> shuffingListView = new JqGridPageView<Association>();
		shuffingListView.setRows(shuffings);
		shuffingListView.setRecords(shuffings.size());
		writeJSON(response, shuffingListView);
	}
	
	/**
	 * 合作院校
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCooperationCollegesList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCooperationCollegesList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("id", "asc");
		List<CooperationColleges> shuffings = cooperationCollegesService.queryByProerties("isdelete", 0,sortedCondition);
		JqGridPageView<CooperationColleges> shuffingListView = new JqGridPageView<CooperationColleges>();
		shuffingListView.setRows(shuffings);
		shuffingListView.setRecords(shuffings.size());
		writeJSON(response, shuffingListView);
	}
	
	// =============================忘记密码====================================================================
	/**
	 * 短信验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/duanxin", method = { RequestMethod.POST, RequestMethod.GET })
	public void duanxin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = request.getParameter("phone");
		JSONObject jsonObj = new JSONObject();
		if (tzzUserService.getByProerties("phone", phone) == null) {
			jsonObj.put("err", "a");
			jsonObj.put("msg", "用户不存在");
			writeJSON(response, jsonObj);

		} else {
			Emailphone ep = emailphoneService.getByProerties("pstate", "1");
			int random = (int) (Math.random() * 1000000);
			for (int i = 0; i != 1;) {
				if (random < 100000) {
					random = (int) (Math.random() * 1000000);
				} else {
					i = 1;
				}
			}
			System.out.println(random);
			request.getSession().setAttribute("dongtaima", random + "");
			request.getSession().setAttribute("dongtaimatime", new Date());
			String content = "【冒险家】验证码" + random + "（有效期是2分钟）。您正在使用手机号对您的冒险家账户进行操作，如非本人操作，请忽略本短信。";
			String msg = Sms.sendsms(ep.getPhone(), ep.getSmspwd(), ep.getChannel(), content, phone);// 发短信
			// String msg="0:12";
			String[] msgs = msg.split(":");
			System.out.println(msgs[0] + "***" + msgs[1]);
			if (msgs[0] == "error") {
				System.out.println(msgs[1]);
				jsonObj.put("err", msgs[1]);
				writeJSON(response, jsonObj);
			} else {
				jsonObj.put("err", 0);
				writeJSON(response, jsonObj);
			}
		}
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/wangjimima", method = { RequestMethod.POST, RequestMethod.GET })
	public void wangjimima(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String phone = request.getParameter("phone");
		String yanzhengma = request.getParameter("yanzhengma");
		String indongtaima = request.getParameter("indongtaima");
		String oldyzm = (String) request.getSession().getAttribute("validateCode");
		String olddtm = (String) request.getSession().getAttribute("dongtaima");
		if (request.getSession().getAttribute("dongtaimatime") != null
				&& !request.getSession().getAttribute("dongtaimatime").equals("")) {
			Date time = (Date) request.getSession().getAttribute("dongtaimatime");
			if (!PTUtils.dongtaimatimeX(time)) {
				jsonObject.put("err", 1);
				jsonObject.put("msg", "动态码超时，请重新发送");
				// request.getSession().removeAttribute("dongtaimatime");
				request.getSession().removeAttribute("dongtaima");
			} else if (!yanzhengma.equals(oldyzm)) {
				jsonObject.put("err", 2);
				jsonObject.put("msg", "验证码错误");
			} else if (!indongtaima.equals(olddtm)) {
				jsonObject.put("err", 3);
				jsonObject.put("msg", "动态码错误");
			} else {
				String pwd = request.getParameter("pwd");
				if (phone != null && !phone.equals("")) {
					TzzUser tzzUser = new TzzUser();
					tzzUser = tzzUserService.getByProerties("phone", phone);
					tzzUser.setPassword(new Sha256Hash(pwd).toHex());
					tzzUserService.merge(tzzUser);
					jsonObject.put("err", 0);
				} else {
					jsonObject.put("err", 1);
				}

				jsonObject.put("err", 0);
				request.getSession().removeAttribute("dongtaimatime");
				request.getSession().removeAttribute("dongtaima");
			}

		}
		writeJSON(response, jsonObject);
	}

	/**
	 * 设置新密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/newpwd", method = { RequestMethod.POST, RequestMethod.GET })
	public void newpwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String pwd = request.getParameter("pwd");
		String phone = (String) request.getSession().getAttribute("wjphone");
		if (phone != null && !phone.equals("")) {
			TzzUser tzzUser = new TzzUser();
			tzzUser = tzzUserService.getByProerties("phone", phone);
			tzzUser.setPassword(new Sha256Hash(pwd).toHex());
			tzzUserService.merge(tzzUser);
			jsonObject.put("err", 0);
		} else {
			jsonObject.put("err", 1);
		}
		writeJSON(response, jsonObject);
	}

	/**
	 * 跳转到主页 /jsp/index
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/index")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// 分类列表
		List<TzzDictionary> dList = tzzDictionaryService.queryByProerties("type", "1");
		List<TzzDictionary> qqList = tzzDictionaryService.queryByProerties("type", "7");
		request.setAttribute("fllist", dList);
		request.setAttribute("qqList", qqList);
		// 获取用户
		TzzUser tzzUser = new TzzUser();
		int userType = 0;
		if (request.getSession().getAttribute("tzzuser") != null) {
			tzzUser = (TzzUser) request.getSession().getAttribute("tzzuser");
			if (tzzUser.getViptype() != null && !tzzUser.getViptype().equals("")) {
				userType = Integer.parseInt(tzzUser.getViptype());
			}

			Map<String, String> sortedCondition = new HashMap<>();
			sortedCondition.put("endTime", "desc");


		} else {
			userType = 0;
		}

		// 首页轮播
		// State=1 carousel_type=n sort= order style=0
		String[] names = new String[3];
		Object[] vals = new Object[3];
		names[0] = "state";
		names[1] = "carouselType";
		names[2] = "style";
		vals[0] = "1";
		vals[1] = userType;
		vals[2] = "0";
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("sort", "asc");



		// 体系库结束========

		// response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}



	/**
	 * 成功案例 /top/user/zhihuifenxiang
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/zhihuifenxiang", method = { RequestMethod.POST, RequestMethod.GET })
	public void zhihuifenxiang(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		List<Information> informations = informationService.getindexlistInformations();
		StringBuffer html = new StringBuffer();
		for (int i = 0; i < informations.size(); i++) {
			if (i > 3) {
				break;
			}
			Information information = new Information();
			information = informations.get(i);
			String content = HtmlUtils.htmltoText(information.getContent());
			html.append("<li>");
			html.append("<a href='top/successfulCase/getixq?id=" + information.getId() + "'>");
			html.append("<div class='zhihui_box'>");
			html.append("<div class='zhihui_img'><p></p>" + information.getImage() + "</div>");
			if (information.getTitle().length() > 13) {
				html.append("<h3>" + information.getTitle().substring(0, 13) + "...</h3>");
			} else {
				html.append("<h3>" + information.getTitle() + "</h3>");
			}
			if (content.length() > 45) {
				html.append("<span>" + content.substring(0, 45) + "</span>");
			} else {
				html.append("<span>" + content + "</span>");
			}

			html.append("<button class='zhihui_btn' style='position: absolute;right: 54px;bottom: 0px;'>查看详情</button>");
			html.append("</div>");
			html.append("</a>");
			html.append("</li>");
		}
		jsonObject.put("html", html.toString());
		writeJSON(response, jsonObject);
	}


	/**
	 * 简介 /top/user/jianjie
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/jianjie", method = { RequestMethod.POST, RequestMethod.GET })
	public void jianjie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		TzzJianjie tzzJianjie = tzzJianjieService.get(1);
		StringBuffer html = new StringBuffer();
		String contentString = HtmlUtils.htmltoText(tzzJianjie.getContent());
		if (contentString.length() > 165) {
			contentString = contentString.substring(0, 165) + "...";
		}
		html.append(tzzJianjie.getImage());
		html.append("<p>" + contentString + "</p>");
		html.append("<p><a href='top/jianjie/gettzz'>查看详情>></a></p>");
		jsonObject.put("html", html.toString());
		writeJSON(response, jsonObject);
	}

	/**
	 * 问答列表 /top/user/wenda
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/wenda", method = { RequestMethod.POST, RequestMethod.GET })
	public void wenda(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("createTime", "desc");
		List<TzzQuestion> tzzQuestions = tzzQuestionService.queryByProerties("state", "2", sortedCondition, 8);
		StringBuffer html = new StringBuffer();
		html.append("<div class='wenda_nr'>");
		for (int i = 0; i < tzzQuestions.size(); i++) {
			TzzAnswer tzzAnswer = new TzzAnswer();
			tzzAnswer = null;
			sortedCondition = new HashMap<>();
			sortedCondition.put("createTime", "asc");
			tzzAnswer = tzzAnswerService.getByProerties("questionId", tzzQuestions.get(i).getId(), sortedCondition);
			String con = "";
			if (tzzAnswer != null) {
				if (HtmlUtils.htmltoText(tzzAnswer.getContent()).length() > 50) {
					con = HtmlUtils.htmltoText(tzzAnswer.getContent()).substring(0, 50) + "...";
				} else {
					con = HtmlUtils.htmltoText(tzzAnswer.getContent());
				}
			}
			if (i == 4) {
				html.append("</div><div class='wenda_nr'>");
			}
			String title = tzzQuestions.get(i).getTitle();
			if (title.length() > 19) {
				title = title.substring(0, 18) + "...";
			}
			html.append("<div class='wen_01'>");
			html.append("<p class='p1'><a href='top/wenda/qxq?qid=" + tzzQuestions.get(i).getId() + "'>" + title
					+ "</a></p>");
			html.append("<p class='p2'>" + con + "</p>");
			html.append("</div>");
		}
		html.append("</div>");
		jsonObject.put("html", html.toString());
		writeJSON(response, jsonObject);
	}



	/**
	 * /jsp/guanggao 客服
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/qqList", method = { RequestMethod.POST, RequestMethod.GET })
	public void qqList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<TzzDictionary> qqList = new ArrayList<TzzDictionary>();
		qqList = tzzDictionaryService.queryByProerties("type", "7");
		String html = "";
		if (qqList != null) {
			for (int i = 0; i < qqList.size(); i++) {
				String a = new String();
				a = qqList.get(i).getTitle();
				html += "<li class=\"kefu_0" + (i + 1) + "\"><a href=\"tencent://message/?uin=" + a
						+ "&Site=qq&Menu=yes\"><img src=\"jsp/images/kefu_0" + (i + 1) + ".png\" /></a></li>";
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("html", html);
		writeJSON(response, jo);

	}

	/**
	 * /jsp/guanggao 检测用户是否登陆。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkUserLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public void checkUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TzzUser tzzUser = (TzzUser) request.getSession().getAttribute("tzzuser");
		JSONObject json = new JSONObject();
		if (tzzUser == null) {
			json.put("err", 1);
			json.put("msg", "请用户登陆！");
		} else {
			json.put("err", 0);
			json.put("msg", "用户已经登陆！");
		}
		writeJSON(response, json);
	}

	/**
	 * 获取新闻
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/newsList", method = { RequestMethod.POST, RequestMethod.GET })
	public void tixikujiatime(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("type") Integer type, @RequestParam("size") Integer size) throws IOException {
		JSONObject jsonObject = new JSONObject();
		Map<String, String> sortedCondition = new HashMap<>();
		sortedCondition.put("creattime", "desc");
		Integer top = request.getParameter("top") == null ? 0 : 1;
		if (null != top && top == 1) {
			String[] names = new String[2];
			Integer[] vals = new Integer[2];
			names[0] = "type";
			names[1] = "istop";
			vals[0] = type;
			vals[1] = top;
			List<TzzSafetyNews> list = safetyNewsService.queryByProerties(names, vals, sortedCondition, size);
			jsonObject.put("data", list);

		} else {
			List<TzzSafetyNews> list = safetyNewsService.queryByProerties("type", type, sortedCondition, size);
			jsonObject.put("data", list);

		}
		writeJSON(response, jsonObject);
	}

	/**
	 * 获取安全新闻
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/gettopnews", method = { RequestMethod.POST, RequestMethod.GET })
	public void gettopnews(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		Map<String, String> sortedCondition = new HashMap<>();
		sortedCondition.put("creattime", "desc");
		String[] names = new String[3];
		Integer[] vals = new Integer[3];
		names[0] = "type";
		names[1] = "istop";
		names[2] = "ispublish";
		vals[0] = 140;
		vals[1] = 1;
		vals[2] = 1;
		List<TzzSafetyNews> toplist = safetyNewsService.queryByProerties(names, vals, sortedCondition, 1);
		TzzSafetyNews topNews = toplist.get(0);
		String topname = topNews.getName();
		if (topname.length() > 25)
			topname = topname.substring(0, 25);
		String toptitle = "<a href='top/safetynews/getixq?id=" + topNews.getId() + "' title='" + topNews.getName()
				+ "' target='_blank'>" + topname + " </a>";
		String topcontent = "<a href='top/safetynews/getixq?id=" + topNews.getId() + "' target='_blank'>" + "<img src='"
				+ topNews.getImg() + "'> </a><h4>" + "<a href='top/safetynews/getixq?id=" + topNews.getId()
				+ "' target='_blank'>" + topNews.getBrief() + " </a></h4>";
		jsonObject.put("toptitle", toptitle);
		jsonObject.put("topcontent", topcontent);

		String[] pnames = new String[2];
		Integer[] pvals = new Integer[2];
		pnames[0] = "type";
		pnames[1] = "ispublish";
		pvals[0] = 140;
		pvals[1] = 1;
		List<TzzSafetyNews> list = safetyNewsService.queryByProerties(pnames, pvals, sortedCondition, 6);
		StringBuilder sb = new StringBuilder();
		for (TzzSafetyNews n : list) {
			String name = n.getName();
			if (name.length() > 25)
				name = name.substring(0, 25);
			sb.append("<li><div class='linr'><a href='top/safetynews/getixq?id=").append(n.getId()).append("' title='")
					.append(n.getName()).append("' target='_blank'>").append(name).append(" </a></div><span>(")
					.append((n.getCreattime().getMonth() + 1) + "-" + (n.getCreattime().getDate()) + ")</span></li>");
		}
		jsonObject.put("newslist", sb.toString());

		writeJSON(response, jsonObject);
	}

	/**
	 * 获取安全新闻
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/gettopothernews", method = { RequestMethod.POST, RequestMethod.GET })
	public void gettopothernews(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		Map<String, String> sortedCondition = new HashMap<>();
		sortedCondition.put("creattime", "desc");
		String[] names = new String[2];
		Integer[] vals = new Integer[2];
		names[0] = "type";
		names[1] = "ispublish";
		vals[0] = 141;
		vals[1] = 1;
		List<TzzSafetyNews> noticelist = safetyNewsService.queryByProerties(names, vals, sortedCondition, 7);
		StringBuilder sb = new StringBuilder();
		for (TzzSafetyNews n : noticelist) {
			String name = n.getName();
			if (name.length() > 25)
				name = name.substring(0, 25);
			sb.append("<li><a href='top/safetynews/getixq?id=" + n.getId() + "' title='" + n.getName()
					+ "' target='_blank'>" + name + " </a></li>");
		}
		jsonObject.put("noticelist", sb.toString());

		vals[0] = 142;
		List<TzzSafetyNews> lawslist = safetyNewsService.queryByProerties(names, vals, sortedCondition, 7);
		StringBuilder sb1 = new StringBuilder();
		for (TzzSafetyNews n : lawslist) {
			String name = n.getName();
			if (name.length() > 25)
				name = name.substring(0, 25);
			sb1.append("<li><a href='top/safetynews/getixq?id=" + n.getId() + "' title='" + n.getName()
					+ "' target='_blank'>" + name + " </a></li>");
		}
		jsonObject.put("lawslist", sb1.toString());

		writeJSON(response, jsonObject);
	}

	/**
	 * 获取活动专区
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getactivityarea", method = { RequestMethod.POST, RequestMethod.GET })
	public void getactivityarea(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		Map<String, String> sortedCondition = new HashMap<>();
		sortedCondition.put("creattime", "desc");
		List<TzzActivityArea> activityarealist = activityAreaService.queryByProerties("ispublish", 1, sortedCondition,
				4);
		StringBuilder sb = new StringBuilder();
		for (TzzActivityArea n : activityarealist) {
			String name = n.getName();
			if (name.length() > 25)
				name = name.substring(0, 25);

			// sb.append("<li><a href='"+n.getBrief()+"' title='"+n.getName()+"'
			// target='_blank'><img src='"+n.getImg()+"'> </a>")
			// .append("<h4><a href='"+n.getBrief()+"' title='"+n.getName()+"'
			// target='_blank'>"+name+" </a></h4></li>");
			sb.append("<li><a href='top/successfulCase/activityarea/getixq?id=" + n.getId() + "' title='" + n.getName()
					+ "' target='_blank'><img src='" + n.getImg().substring(1) + "'> </a>")
					.append("<h4><a href='top/successfulCase/activityarea/getixq?id=" + n.getId() + "' title='"
							+ n.getName() + "' target='_blank'>" + name + " </a></h4></li>");
		}
		jsonObject.put("activityarealist", sb.toString());

		writeJSON(response, jsonObject);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.GET })
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String openid = request.getParameter("openid");

		JSONObject jsonObject = new JSONObject();
		String msg = "";
		if (openid == null || openid.equals("")) {
			msg = "微信登录验证失败！";
			jsonObject.put("success", false);
			jsonObject.put("msg", msg);
			// request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
		} else {
			// openid是否保存
			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				jsonObject.put("success", false);
				msg = "未注册！";
				jsonObject.put("msg", msg);
			} else {
				//request.getSession().setAttribute("tzzuser", tzzUser);				
				String json = "{id:"+tzzUser.getId()+",realname:'"+tzzUser.getRealname()+"',username:'"+tzzUser.getUsername()+"',img:'"+tzzUser.getImage()+"',sex:"+tzzUser.getSex()+",phone:'"+tzzUser.getPhone()+"',openid:'"+tzzUser.getOpenid()+"',babyName:'"+tzzUser.getBabyName()+"',babyBirthday:'"+tzzUser.getBabyBirthday()+"',babySex:"+tzzUser.getBabySex()+",birthday:'"+tzzUser.getBirthday()+"'}";
				
				jsonObject.put("success", true);
				jsonObject.put("tzzUser", json);
			}
		}
		writeJSON(response, jsonObject);

	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserBaseInfo", method = { RequestMethod.POST })
	public void getUserBaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String openid = request.getParameter("openid");
		String userid = request.getParameter("userid");

		JSONObject jsonObject = new JSONObject();
		String msg = "";
		if (StringHelper.isNotEmpty(userid)) {
			TzzUser tzzUser = tzzUserService.get(NumberHelper.string2Int(userid));
			if (null == tzzUser) {
				jsonObject.put("success", false);
				msg = "未注册！";
				jsonObject.put("msg", msg);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//request.getSession().setAttribute("tzzuser", tzzUser);		
				String birthday_s = "";
				if(tzzUser.getBirthday()!=null){
					birthday_s = sdf.format(tzzUser.getBirthday());
				}
				String json = "{id:"+tzzUser.getId()+",realname:'"+tzzUser.getRealname()+"',username:'"+tzzUser.getUsername()+"',img:'"+tzzUser.getImage()+"',sex:"+tzzUser.getSex()+",phone:'"+tzzUser.getPhone()+"',birthday:'"+birthday_s+"'}";
				
				jsonObject.put("success", true);
				jsonObject.put("tzzUser", json);
			}
		}else{
			msg = "登录信息验证失败！";
			jsonObject.put("success", false);
			jsonObject.put("msg", msg);
		}
		writeJSON(response, jsonObject);

	}
	
	/**
	 * 微信获取我的卡 /getMyCards
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyCards", method = { RequestMethod.GET })
	public void getMyCards(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		String msg = "";
		Timestamp s = new Timestamp();
		if (openid == null || openid.equals("")) {
			msg = "获取用户标示失败！";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/jsp/wx/wx_index.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
		} else {

			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				request.getRequestDispatcher("/jsp/weixin_login_bind_copy.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
			} else {
				request.getSession().setAttribute("tzzuser", tzzUser);
				request.getRequestDispatcher("/jsp/cards_weixin_copy.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
			}
		}
	}
	
	
	/**
	 * 微信获取我的卡 /getMyFavors
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyFavors", method = { RequestMethod.GET })
	public void getMyFavors(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openid = request.getParameter("openid");
		String msg = "";
		Timestamp s = new Timestamp();
		if (openid == null || openid.equals("")) {
			msg = "获取用户标示失败！";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/jsp/wx/wx_index.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
		} else {

			TzzUser tzzUser = tzzUserService.getByProerties("openid", openid);
			if (null == tzzUser) {
				request.getRequestDispatcher("/jsp/weixin_login_bind_copy.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
			} else {
				request.getSession().setAttribute("tzzuser", tzzUser);
				request.getRequestDispatcher("/jsp/cards_weixin_copy.jsp?TIMELINE="+s.getDateTime()).forward(request, response);
			}
		}
	}
	

	/**
	 * 取得家长所有学生的信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFamilyStudent", method = { RequestMethod.POST, RequestMethod.GET })
	public void getFamilyStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userphone = request.getParameter("userphone");
		
		String [] keys = new String [2];
		keys[0] = "phone";
		keys[1] = "uType";
		Object [] values = new Object[2];
		values[0] = userphone;
		values[1] = "1";
		List<TzzUser> userSons = tzzUserService.queryByProerties(keys, values);
		JqGridPageView<TzzUser> shuffingListView = new JqGridPageView<TzzUser>();
		shuffingListView.setRows(userSons);
		shuffingListView.setRecords(userSons.size());
		writeJSON(response, shuffingListView);
	}
	
	
	/**
	 * 取得首页功能列表信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIndexMenu", method = { RequestMethod.POST, RequestMethod.GET })
	public void getIndexMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String utype = request.getParameter("utype");
		if(StringHelper.isEmpty(utype)){
			utype = "0";
		}
		String[] names = new String[2];
		Object[] vals = new Object[2];
		names[0] = "state";
		names[1] = "roletype";
		vals[0] = "1";
		vals[1] = utype;
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("sort", "asc");
		List<TzzIndexMenu> iList = tzzIndexMenuService.queryByProerties(names, vals, sortedCondition);
		JqGridPageView<TzzIndexMenu> shuffingListView = new JqGridPageView<TzzIndexMenu>();
		shuffingListView.setRows(iList);
		shuffingListView.setRecords(iList.size());
		writeJSON(response, shuffingListView);
	}
	

}
