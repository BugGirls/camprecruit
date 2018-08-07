package com.jeefw.controller.top;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.CompanyInfo;
import com.jeefw.model.sys.CompanyNews;
import com.jeefw.model.sys.CompanyShenhe;
import com.jeefw.model.sys.CompanyUser;
import com.jeefw.model.sys.IndividualUser;
import com.jeefw.model.sys.Mail;
import com.jeefw.service.sys.CompanyInfoService;
import com.jeefw.service.sys.CompanyNewsService;
import com.jeefw.service.sys.CompanyShenheService;
import com.jeefw.service.sys.CompanyUserService;
import com.jeefw.service.sys.TzzDictionaryService;

import cn.jiguang.commom.utils.StringUtils;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import email.SendMail;
import net.sf.json.JSONObject;

/**
 * 个人中心。
 */
@Controller
@RequestMapping("/top/companyuser")
public class TopCompanyUserController extends JavaEEFrameworkBaseController<CompanyUser> implements Constant {

	@Resource
	private CompanyUserService companyUserService;

	@Resource
	private CompanyShenheService companyShenheService;
	
	@Resource
	private CompanyInfoService companyInfoService;
	
	@Resource
	private CompanyNewsService companyNewsService;
	
	@Resource
	private TzzDictionaryService tzzDictionaryService;
	
	/**
	 * 登录 /jsp/login
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		String pwd = request.getParameter("password");
		String autoLogin = request.getParameter("autoLogin");
		String msg = "";
		CompanyUser companyUser = companyUserService.getByProerties("email", email);

		JSONObject json = new JSONObject();
		if (companyUser != null) {
			String npwd = new Sha256Hash(pwd).toHex();
			if (npwd.equals(companyUser.getPassword())) {
				request.setAttribute("msg", msg);
				request.getSession().setAttribute("companyuser", companyUser);
				
				companyUser.setLastloginTime(new Date());
				companyUser.setIp(getRemoteHost(request));
				
				companyUserService.merge(companyUser);
				
				CompanyInfo companyInfo = companyInfoService.get(companyUser.getId());
				if(null==companyInfo){
					companyInfo = new CompanyInfo();
				}
				request.getSession().setAttribute("companyinfo", companyInfo);
				
				Cookie userCookie=new Cookie("userinfo",email+"_"+pwd); 

	             userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
	             userCookie.setPath("/");
	             response.addCookie(userCookie); 
	             json.put("state", companyUser.getState());
	             json.put("stage", companyUser.getStage());
	             json.put("userid", companyUser.getId());
	             json.put("success", true);
	             
			} else {
				msg = "密码错误";
				json.put("success", false);
			}
		}else{
			msg = "用户不存在";
			json.put("success", false);
		}
//		}
//		request.setAttribute("msg", msg);
//		request.setAttribute("name", name);
//		request.getRequestDispatcher("/jsp/loginpage.jsp").forward(request, response);
		
		
		json.put("msg", msg);
		writeJSON(response, json);
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// String phone = request.getParameter("phone");
		// String uType = request.getParameter("uType");
		if (companyUserService.getByProerties("email", email) != null) {
			jsonObject.put("success", false);
			jsonObject.put("err", 1);
			jsonObject.put("msg", "该邮箱已注册");
		} else {
			CompanyUser tzzUser = new CompanyUser();
			tzzUser.setEmail(email);
			tzzUser.setPassword(new Sha256Hash(password).toHex());
			// if(!uType.equals("1")){
			// tzzUser.setPhone(phone);
			// }
			// tzzUser.setPhone(phone);
			tzzUser.setUType("0");
			tzzUser.setImage("/static/dist/img/photo.png");
			tzzUser.setStage(0);
			tzzUser.setViptype("1");
			tzzUser.setCreateTime(new Date());
			tzzUser.setLastloginTime(new Date());

			companyUserService.persist(tzzUser);
			request.getSession().setAttribute("companyuser", tzzUser);
			jsonObject.put("success", true);
			jsonObject.put("userid", tzzUser.getId());
			// request.getRequestDispatcher("/jsp/loginpage.jsp").forward(request,
			// response);
		}
		// request.getRequestDispatcher("/jsp/registerpage.jsp").forward(request,
		// response);
		writeJSON(response, jsonObject);
	}

	// 查询注册用户信息
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String userphone = request.getParameter("userphone");
		CompanyUser tzzUser = null;
		if (StringHelper.isNotEmpty(userid)) {
			tzzUser = companyUserService.get(NumberHelper.string2Int(userid));
		} else {
			tzzUser = companyUserService.getByProerties("phone", userphone);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		if (tzzUser != null) {
			jsonObject.put("phone", tzzUser.getPhone());
		}
		writeJSON(response, jsonObject);
	}

	// 查询注册用户信息
	@RequestMapping(value = "/getuserinfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getuserinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String schoolid = request.getParameter("schoolid");
		// String userphone= request.getParameter("userphone");
		// CompanyUser tzzUser = tzzUserService.getByProerties("phone",
		// userphone);
		CompanyUser tzzUser = companyUserService.get(NumberHelper.string2Int(userid));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);

		long tzzUserbirthday = 0;
		if (null != tzzUser) {
			if (tzzUser.getuType() != null) {
				if (tzzUser.getuType().equals("1") || tzzUser.getuType().equals("5")) {
					// Float avgmark =
					// stuCourComtService.totalMark(tzzUser.getId());
					// jsonObject.put("useravgmark",avgmark);
					// }else if(tzzUser.getuType().equals("2")){
					// Float avgmark =
					// coachCommentService.getAverageMark(tzzUser.getId());
					// jsonObject.put("useravgmark",avgmark);
				} else if (tzzUser.getuType().equals("2")) {
				}
			}
			jsonObject.put("success", true);
			jsonObject.put("userinfo", tzzUser.getBaseInfo());
			jsonObject.put("birthday", tzzUserbirthday);

		}
		writeJSON(response, jsonObject);
	}

	@RequestMapping(value = "/showUserDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public void showUserDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else {
				/*
				 * 查询所有的地址信息。按照state。
				 */
//				Map<String, String> sortedCondition = new HashMap<String, String>();
//				sortedCondition.put("state", "desc");
//				request.setAttribute("tzzUser", tzzUser);
//				String defaultPage = request.getParameter("defaultPage");
//				request.setAttribute("defaultPage", defaultPage);
				CompanyInfo companyInfo = companyInfoService.get(tzzUser.getId());
				if(null==companyInfo)
					companyInfo =  new CompanyInfo();
				request.getSession().setAttribute("companyinfo", companyInfo);
				request.getRequestDispatcher("/jsp/company_wanshan1.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/showAddShare", method = { RequestMethod.POST, RequestMethod.GET })
	public void showAddShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("tzzuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			}
			request.setAttribute("tzzUser", tzzUser);
			request.getRequestDispatcher("/jsp/center_fx_tj.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/fileUpload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");

		if (tzzUser == null) {
			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		} else {
			RequestContext requestContext = new RequestContext(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");

			JSONObject json = new JSONObject();
			String image = "";
			String img = request.getParameter("image");
			if (!file.isEmpty()) {
				if (file.getSize() > 2097152L)
					json.put("message", requestContext.getMessage("g_fileTooLarge"));
				else
					try {
						String originalFilename = file.getOriginalFilename();
						String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
								+ originalFilename.substring(originalFilename.lastIndexOf("."));
						File filePath = new File(getClass().getClassLoader().getResource("/").getPath()
								.replace("/WEB-INF/classes/", "/static/upload/img/zhizhao/" + sdf.format(new Date())));
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
						String destinationFilePath = "/static/upload/img/zhizhao/" + sdf.format(new Date()) + "/"
								+ fileName;

						image = destinationFilePath;
						json.put("err", 0);
						json.put("url", destinationFilePath);
						json.put("message", requestContext.getMessage("g_uploadSuccess"));
					} catch (Exception e) {
						e.printStackTrace();
						json.put("message", requestContext.getMessage("g_uploadFailure"));
					}
			} else if(StringHelper.isNotEmpty(img)) {
				image = img;
			} else {
				json.put("message", requestContext.getMessage("g_uploadNotExists"));
				json.put("err", 1);
			}

			String name = request.getParameter("name");
			tzzUser.setName(name);
			tzzUser.setState(1);
			CompanyShenhe companyShenhe = new CompanyShenhe();
			companyShenhe.setUserid(tzzUser.getId());
			companyShenhe.setName(name);
			companyShenhe.setImage(image);
			companyShenhe.setCreateTime(new Date());
			companyShenhe.setDaima(request.getParameter("daima"));
			companyShenhe.setFaren(request.getParameter("faren"));
			companyShenhe.setStage(0);
			companyShenheService.merge(companyShenhe);
//			int userId = tzzUser.getId();
//			CompanyUser tzzUserTmp = companyUserService.get(userId);
//			tzzUserTmp.setImage(image);
			companyUserService.merge(tzzUser);
		}
		showUserDetail(request, response);
	}

	@RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void modifyUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("tzzuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else {
				String nickname = request.getParameter("nickname");
				String name = request.getParameter("name");
				name = new String(name.getBytes("iso-8859-1"), "utf-8");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String qq = request.getParameter("qq");
				String sex = request.getParameter("sex");

				jsonObj.put("err", 0);
				jsonObj.put("msg", "修改成功！");

				CompanyUser tzzUserTmp = companyUserService.get(tzzUser.getId());
				if (email != null && email.length() > 0) {
					tzzUserTmp.setEmail(email);
					tzzUser.setEmail(email);
				}
				if (phone != null && phone.length() > 0) {
					tzzUserTmp.setPhone(phone);
					if (phone != null && phone.length() > 7) {
						tzzUser.setPhone(phone);
					}
					tzzUser.setPhoneNick(phone);
				}
				companyUserService.merge(tzzUserTmp);
				writeJSON(response, jsonObj);
			}
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("err", 4);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
	@RequestMapping(value = { "/logoUpload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public void logoUpload(@RequestParam(value = "newResume", required = false) CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", false);
		CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");

		if (tzzUser == null) {
//			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			jsonObj.put("msg", "登录信息已过期，请重新登录！");
		} else {
			RequestContext requestContext = new RequestContext(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");

			JSONObject json = new JSONObject();
			String image = "";
			if (!file.isEmpty()) {
				if (file.getSize() > 2097152L)
					jsonObj.put("code", -2);
//					json.put("message", requestContext.getMessage("g_fileTooLarge"));
				else
					try {
						String originalFilename = file.getOriginalFilename();
						String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
								+ originalFilename.substring(originalFilename.lastIndexOf("."));
						File filePath = new File(getClass().getClassLoader().getResource("/").getPath()
								.replace("/WEB-INF/classes/", "/static/upload/img/logo/" + sdf.format(new Date())));
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
						String destinationFilePath = "/static/upload/img/logo/" + sdf.format(new Date()) + "/"
								+ fileName;

						image = destinationFilePath;
						json.put("err", 0);
						json.put("url", destinationFilePath);
						json.put("message", requestContext.getMessage("g_uploadSuccess"));
					} catch (Exception e) {
						e.printStackTrace();
						jsonObj.put("code", -1);
//						json.put("message", requestContext.getMessage("g_uploadFailure"));
					}
			} else {
//				json.put("message", requestContext.getMessage("g_uploadNotExists"));
//				json.put("err", 1);
				jsonObj.put("code", -1);
			}
			tzzUser.setImage(image);
			companyUserService.merge(tzzUser);
			jsonObj.put("success", true);
			jsonObj.put("msg", image);
		}
		writeJSON(response, jsonObj);
	}
	
	@RequestMapping(value = "/saveCompanyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveCompanyInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else {
				String fullname = request.getParameter("fullname");
				String name = request.getParameter("name");
//				name = new String(name.getBytes("iso-8859-1"), "utf-8");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String image = tzzUser.getImage();
				String website = request.getParameter("website");
				String shengshi = request.getParameter("shengshi");
				String sheng = request.getParameter("sheng");
				String shi = request.getParameter("shi");
				String hangye = request.getParameter("hangye");
				String hangyevalue = request.getParameter("hangyevalue");
				String guimo = request.getParameter("guimo");
				String guimovalue = request.getParameter("guimovalue");
				String jieduan = request.getParameter("jieduan");
				String jieduanvalue = request.getParameter("jieduanvalue");
				String brief = request.getParameter("brief");

				jsonObj.put("success", true);
				jsonObj.put("msg", "保存成功！");

				CompanyInfo	companyInfo = new CompanyInfo(tzzUser.getId(),fullname,name, image, phone,
							 website, shengshi, sheng, shi, hangye, hangyevalue,
							 guimo, guimovalue, jieduan, jieduanvalue, brief);
				companyInfoService.merge(companyInfo);
				tzzUser.setStage(1);
				companyUserService.merge(tzzUser);
				request.getSession().setAttribute("companyuser", tzzUser);
				request.getSession().setAttribute("companyinfo", companyInfo);
				writeJSON(response, jsonObj);
			}
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
//	保存企业简介和简称
	@RequestMapping(value = "/saveProfileShortNameAndFeatures", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveProfileShortNameAndFeatures(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				jsonObj.put("success", false);
				jsonObj.put("err", 1);
				jsonObj.put("msg", "用户登录信息认证失败");
			} else {
				String name = request.getParameter("companyShortName");
				String brief = request.getParameter("companyFeatures");


				CompanyInfo	companyInfo = (CompanyInfo) request.getSession().getAttribute("companyinfo");
				companyInfo.setName(name);
				companyInfo.setBrief(brief);
				companyInfoService.merge(companyInfo);
				request.getSession().setAttribute("companyinfo", companyInfo);
				jsonObj.put("success", true);
				jsonObj.put("content", companyInfo);
				jsonObj.put("msg", "保存成功！");
			}
			writeJSON(response, jsonObj);
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("err", 2);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}

//	保存企业标签
	@RequestMapping(value = "/pasteLabelToCompany", method = { RequestMethod.POST, RequestMethod.GET })
	public void pasteLabelToCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				jsonObj.put("success", false);
				jsonObj.put("err", 1);
				jsonObj.put("msg", "用户登录信息认证失败");
			} else {
				String labels = request.getParameter("labels");

				CompanyInfo	companyInfo = (CompanyInfo) request.getSession().getAttribute("companyinfo");
				companyInfo.setLabels(labels);
				companyInfoService.merge(companyInfo);
				request.getSession().setAttribute("companyinfo", companyInfo);
				jsonObj.put("success", true);
				jsonObj.put("content", companyInfo);
				jsonObj.put("msg", "保存成功！");
			}
			writeJSON(response, jsonObj);
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("err", 2);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
//	保存企业介绍
	@RequestMapping(value = "/saveCompanyProfile", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveCompanyProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				jsonObj.put("success", false);
				jsonObj.put("err", 1);
				jsonObj.put("msg", "用户登录信息认证失败");
			} else {
				String profile = request.getParameter("companyProfile");

				CompanyInfo	companyInfo = (CompanyInfo) request.getSession().getAttribute("companyinfo");
				companyInfo.setProfile(profile);
				companyInfoService.merge(companyInfo);
				request.getSession().setAttribute("companyinfo", companyInfo);
				jsonObj.put("success", true);
				jsonObj.put("content", profile);
				jsonObj.put("msg", "保存成功！");
			}
			writeJSON(response, jsonObj);
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("err", 2);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
//	保存企业基本信息
	@RequestMapping(value = "/saveProfileBaseInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveProfileBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				jsonObj.put("success", false);
				jsonObj.put("err", 1);
				jsonObj.put("msg", "用户登录信息认证失败");
			} else {
				String website = request.getParameter("website");
				String shengshi = request.getParameter("shengshi");
				String sheng = request.getParameter("sheng");
				String shi = request.getParameter("shi");
				String hangye = request.getParameter("hangye");
				String hangyevalue = request.getParameter("hangyevalue");
				String guimo = request.getParameter("guimo");
				String guimovalue = request.getParameter("guimovalue");
				String jieduan = request.getParameter("jieduan");
				String jieduanvalue = request.getParameter("jieduanvalue");

				CompanyInfo	companyInfo = (CompanyInfo) request.getSession().getAttribute("companyinfo");
				companyInfo.setWebsite(website);
				companyInfo.setShengshi(shengshi);
				companyInfo.setSheng(sheng);
				companyInfo.setShi(shi);
				companyInfo.setHangye(hangye);
				companyInfo.setHangyevalue(hangyevalue);
				companyInfo.setGuimo(guimo);
				companyInfo.setGuimovalue(guimovalue);
				companyInfo.setJieduan(jieduan);
				companyInfo.setJieduanvalue(jieduanvalue);
				
				companyInfoService.merge(companyInfo);
				request.getSession().setAttribute("companyinfo", companyInfo);
				jsonObj.put("success", true);
				jsonObj.put("content", companyInfo);
				jsonObj.put("msg", "保存成功！");
			}
			writeJSON(response, jsonObj);
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("err", 2);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
//	保存企业标签
	@RequestMapping(value = "/saveNews", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
			if (tzzUser == null) {
//				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				jsonObj.put("success", false);
				jsonObj.put("err", 1);
				jsonObj.put("msg", "用户登录信息认证失败");
			} else {
				
				String title = request.getParameter("title");
				String url = request.getParameter("url");
				String id = request.getParameter("id");

				CompanyInfo	companyInfo = (CompanyInfo) request.getSession().getAttribute("companyinfo");
				CompanyNews companyNews = new CompanyNews();
				if(!StringHelper.isEmpty(id)){
					CompanyNews news =  companyNewsService.get(Integer.parseInt(id));
					if(null!=news)
						companyNews = news;
				}
				companyNews.setTitle(title);
				companyNews.setUrl(url);
				companyNews.setUserid(companyInfo.getUserid());
				companyNews.setCreateTime(new Date());
				if(null==companyNews.getId())
					companyNewsService.persist(companyNews);
				else
					companyNewsService.update(companyNews);
				request.getSession().setAttribute("companyinfo", companyInfo);
				jsonObj.put("success", true);
				jsonObj.put("content", companyNews);
				jsonObj.put("msg", "保存成功！");
			}
			writeJSON(response, jsonObj);
			// showUserDetail(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("success", false);
			jsonObj.put("err", 2);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
	@RequestMapping(value = "/getNewsList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		CompanyUser tzzUser = (CompanyUser) request.getSession().getAttribute("companyuser");
		if (tzzUser == null) {
//			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			jsonObj.put("success", false);
			jsonObj.put("err", 1);
			jsonObj.put("msg", "用户登录信息认证失败");
			writeJSON(response, jsonObj);
		} else {
			CompanyNews companyNews = new CompanyNews();
			companyNews.set$eq_userid(tzzUser.getId());
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put("createTime", "desc");
			companyNews.setSortedConditions(sortedCondition);
			QueryResult<CompanyNews> queryResult = companyNewsService.doPaginationQuery(companyNews);
			JqGridPageView<CompanyNews> dictListView = new JqGridPageView<CompanyNews>();
			dictListView.setRows(queryResult.getResultList());
			dictListView.setRecords(queryResult.getTotalCount());
			writeJSON(response, dictListView);
			
		}
		
	}
	
	/**
	 * 邮箱验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/emailvolidate", method = { RequestMethod.POST })
	public void emailvolidate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long validateCodeDate = (Long) request.getSession().getAttribute("validateCodeDate");
		// System.out.println("oldcode="+oldcode);
		String email = request.getParameter("email");
		
		String msg = "";
		Boolean isok = false;
		if (null == email || "".equals(email)) {
			msg = "请输入邮箱地址！";
//			msg = "获取验证码失败！";
		} else {
			long currenttime = (new Date()).getTime();
			if (null!=validateCodeDate) {
				
				if(currenttime - validateCodeDate>120*1000){
					int newcode = (int)(Math.random()*(9999-1000+1))+1000;
					request.getSession().setAttribute("email", email);
					request.getSession().setAttribute("validateCode", newcode);
					request.getSession().setAttribute("validateCodeDate", currenttime);
					//SingleSendSms.smsSend(phone, "SMS_56665274","{\"code\":\""+newcode+"\",\"product\":\"[营才网]\"}");
					Mail mail=new Mail();
					 mail.setMessage("<h1>验证码:<span style='color:red;font-size: 22px;font-weight: bolder;'>"+newcode+"</span></h1>");
					 mail.setSubject("营才网 企业用户找回密码");
					 mail.setSender("yingcaiwangadmin@163.com");
					 mail.setRecipient(email);
					 
					 SendMail sendMail=new SendMail("smtp.163.com");
					 String a= sendMail.send(mail, "smtp.163.com", "tzz123456");
					 System.out.println(a);
					
					isok = true;
					msg = "验证码发送成功！";
					System.out.println("newcode========"+ newcode);
				}else{
					long time = 120 - (currenttime - validateCodeDate)/1000;
					msg = "请等待"+time+"秒后再试！";
				}
			} else {
				int newcode = (int)(Math.random()*(9999-1000+1))+1000;
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("validateCode", newcode);
				request.getSession().setAttribute("validateCodeDate", currenttime);
				//SingleSendSms.smsSend(phone, "SMS_56665274","{\"code\":\""+newcode+"\",\"product\":\"[营才网]\"}");
				Mail mail=new Mail();
				 mail.setMessage("<h1>验证码:<span style='color:red;font-size: 22px;font-weight: bolder;'>"+newcode+"</span></h1>");
				 mail.setSubject("营才网 企业用户找回密码");
				 mail.setSender("yingcaiwangadmin@163.com");
				 mail.setRecipient(email);
				 
				 SendMail sendMail=new SendMail("smtp.163.com");
				 String a= sendMail.send(mail, "smtp.163.com", "tzz123456");
				 System.out.println(a);
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
	@RequestMapping(value = "/emailcheck", method = { RequestMethod.POST })
	public void emailcheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long validateCodeDate = (Long) request.getSession().getAttribute("validateCodeDate");
		Integer validateCode = (Integer) request.getSession().getAttribute("validateCode");
		String validateEmail = (String) request.getSession().getAttribute("email");
		// System.out.println("oldcode="+oldcode);
		String email = request.getParameter("email");
		String newcode = request.getParameter("code");
		
		String msg = "验证码验证失败！";
		Boolean isok = false;
		if (null == email || "".equals(email)) {
			msg = "请输入邮箱地址！";
//			msg = "获取验证码失败！";
		} else if (!validateEmail.equals(email)) {
			msg = "验证码同验证邮箱地址不一致！";
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
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST })
	public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String msg = "";
		int err = 0;
		CompanyUser companyUser = companyUserService.getByProerties("email", email);
		if (companyUser != null) {
			String npwd = new Sha256Hash(password).toHex();
			companyUser.setPassword(npwd);
			companyUserService.merge(companyUser);
		}else{
			err = 1;
			msg = "用户不存在";
		}
		
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("err", err);
		
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 验证注册邮箱
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkResetPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public void checkResetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		String email = request.getParameter("email");
		String msg = "";
		int err = 0;
		CompanyUser individualUser = companyUserService.getByProerties("email", email);
		if (individualUser == null) {
			msg = "该邮箱地址未注册，请先注册";
			jsonObject.put("msg", msg);
			jsonObject.put("err", -1);
		}else{
			jsonObject.put("success", true);
			jsonObject.put("msg", msg);
			jsonObject.put("err", err);
		}
		
		
		writeJSON(response, jsonObject);
	}
	
	// 获取单条记录
	@RequestMapping("/getCompanyShenheDetailInfo")
	public void getCompanyShenheDetailInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") Integer id) throws IOException {
		CompanyShenhe company = companyShenheService.get(id);
		JSONObject json = new JSONObject();
		if (null != company && 0 != company.getUserid()) {
			json.put("obj", company);
			json.put("success", true);
			writeJSON(response, json);
		} else {
			json.put("success", false);
			writeJSON(response, json);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Date datejia(Date date, int jia) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, jia);
		date = calendar.getTime();
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
	}

}
