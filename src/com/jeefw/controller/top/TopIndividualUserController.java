package com.jeefw.controller.top;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.IndividualUser;
import com.jeefw.model.sys.ResumeBase;
import com.jeefw.service.sys.IndividualUserService;
import com.jeefw.service.sys.ResumeBaseService;
import com.jeefw.service.sys.TzzDictionaryService;

import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 个人中心。
 */
@Controller
@RequestMapping("/top/individualuser")
public class TopIndividualUserController extends JavaEEFrameworkBaseController<IndividualUser> implements Constant {

	@Resource
	private IndividualUserService individualUserService;
	
	@Resource
	private ResumeBaseService resumeBaseService;

	@Resource
	private TzzDictionaryService tzzDictionaryService;
	
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
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		if (individualUserService.getByProerties("phone", phone) != null) {
			jsonObject.put("success", false);
			jsonObject.put("err", 1);
			jsonObject.put("msg", "该手机号已注册");
		} else {
			IndividualUser tzzUser = new IndividualUser();
			tzzUser.setPhone(phone);
			tzzUser.setPassword(new Sha256Hash(password).toHex());
			tzzUser.setImage("/static/dist/img/photo.png");
			tzzUser.setCreatetime(new Date());
			tzzUser.setLastloginTime(new Date());

			individualUserService.persist(tzzUser);
			request.getSession().setAttribute("individualUser", tzzUser);
			jsonObject.put("success", true);
			jsonObject.put("err", 0);
			jsonObject.put("userid", tzzUser.getId());
		}
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 个人手机登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String isremember = request.getParameter("isremember");
		String msg = "";
		int err = 0;
		IndividualUser individualUser = individualUserService.getByProerties("phone", phone);
		if (individualUser != null) {
			String npwd = new Sha256Hash(password).toHex();
			if (npwd.equals(individualUser.getPassword())) {
				jsonObject.put("userid", individualUser.getId());
				//记住密码
				if(StringHelper.isNotEmpty(isremember)){
					if(isremember.equals("1")){
						Cookie userCookie=new Cookie("userinfo",phone+"_"+password); 
						Cookie isrememberCookie=new Cookie("isremember",isremember); 
						
						userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
						isrememberCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
						userCookie.setPath("/");
						isrememberCookie.setPath("/");
						response.addCookie(userCookie); 
						response.addCookie(isrememberCookie); 
					}else{
						 Cookie[] cookies = request.getCookies();  
				            if (null==cookies) {  
				                System.out.println("没有cookie==============");  
				            } else {  
				                for(Cookie cookie : cookies){  
				                    if(cookie.getName().equals("userinfo")||cookie.getName().equals("isremember")){  
				                        cookie.setValue(null);  
				                        cookie.setMaxAge(0);// 立即销毁cookie  
				                        cookie.setPath("/");  
				                        System.out.println("被删除的cookie名字为:"+cookie.getName());  
				                        response.addCookie(cookie);  
				                        //break;  
				                    }  
				                }  
				            }  
					}
				}
				request.setAttribute("msg", msg);
				request.getSession().setAttribute("individualuser", individualUser);
				request.getSession().setAttribute("userid", individualUser.getId());
				String [] keys = new String [2];
				keys[0] = "userid";
				keys[1] = "isdefault";
				Object [] values = new Object[2];
				values[0] = individualUser.getId();
				values[1] = 1;
				ResumeBase resumeBase = resumeBaseService.getByProerties(keys, values);
				request.getSession().setAttribute("defaultResume", resumeBase);
				individualUser.setLastloginTime(new Date());
				individualUserService.merge(individualUser);
			} else {
				err = 1;
				msg = "密码错误";
			}
		}else{
			err = 2;
			msg = "用户不存在";
		}
		
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("err", err);
		
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 验证注册手机号
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkResetPhone", method = { RequestMethod.POST, RequestMethod.GET })
	public void checkResetPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String phone = request.getParameter("phone");
		String msg = "";
		int err = 0;
		IndividualUser individualUser = individualUserService.getByProerties("phone", phone);
		if (individualUser == null) {
			err = 2;
			msg = "手机号未注册，请先注册";
		}
		
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("err", err);
		
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String msg = "";
		int err = 0;
		IndividualUser individualUser = individualUserService.getByProerties("phone", phone);
		if (individualUser != null) {
			String npwd = new Sha256Hash(password).toHex();
			individualUser.setPassword(npwd);
			individualUserService.merge(individualUser);
		}else{
			err = 1;
			msg = "用户不存在";
		}
		
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("err", err);
		
		writeJSON(response, jsonObject);
	}

	// 查询注册用户信息
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String userphone = request.getParameter("userphone");
		IndividualUser tzzUser = null;
		if (StringHelper.isNotEmpty(userid)) {
			tzzUser = individualUserService.get(NumberHelper.string2Int(userid));
		} else {
			tzzUser = individualUserService.getByProerties("phone", userphone);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		if (tzzUser != null) {
			jsonObject.put("phone", tzzUser.getPhone());
		}
		writeJSON(response, jsonObject);
	}


	@RequestMapping(value = "/showUserDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public void showUserDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			IndividualUser tzzUser = (IndividualUser) request.getSession().getAttribute("tzzuser");
			if (tzzUser == null) {
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			} else {
				/*
				 * 查询所有的地址信息。按照state。
				 */
				Map<String, String> sortedCondition = new HashMap<String, String>();
				sortedCondition.put("state", "desc");
				request.setAttribute("tzzUser", tzzUser);
				String defaultPage = request.getParameter("defaultPage");
				request.setAttribute("defaultPage", defaultPage);
				request.getRequestDispatcher("/jsp/center_zhanghu.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	@RequestMapping(value = { "/fileUpload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
//	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		RequestContext requestContext = new RequestContext(request);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//		SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//
//		JSONObject json = new JSONObject();
//		String image = "";
//		if (!file.isEmpty()) {
//			if (file.getSize() > 2097152L)
//				json.put("message", requestContext.getMessage("g_fileTooLarge"));
//			else
//				try {
//					String originalFilename = file.getOriginalFilename();
//					String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3)
//							+ originalFilename.substring(originalFilename.lastIndexOf("."));
//					File filePath = new File(getClass().getClassLoader().getResource("/").getPath()
//							.replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
//					if (!filePath.exists()) {
//						filePath.mkdirs();
//					}
//					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
//					String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
//
//					image = destinationFilePath;
//					json.put("err", 0);
//					json.put("url", destinationFilePath);
//					json.put("message", requestContext.getMessage("g_uploadSuccess"));
//				} catch (Exception e) {
//					e.printStackTrace();
//					json.put("message", requestContext.getMessage("g_uploadFailure"));
//				}
//		} else {
//			json.put("message", requestContext.getMessage("g_uploadNotExists"));
//			json.put("err", 1);
//		}
//
//		IndividualUser tzzUser = (IndividualUser) request.getSession().getAttribute("tzzuser");
//		tzzUser.setImage(image);
//		if (tzzUser == null) {
//			request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
//		} else {
//			int userId = tzzUser.getId();
//			IndividualUser tzzUserTmp = individualUserService.get(userId);
//			tzzUserTmp.setImage(image);
//			individualUserService.merge(tzzUserTmp);
//		}
//		showUserDetail(request, response);
//	}

	@RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void modifyUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			IndividualUser tzzUser = (IndividualUser) request.getSession().getAttribute("tzzuser");
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

				IndividualUser tzzUserTmp = individualUserService.get(tzzUser.getId());
				if (email != null && email.length() > 0) {
					tzzUserTmp.setEmail(email);
					tzzUser.setEmail(email);
				}
				if (phone != null && phone.length() > 0) {
					tzzUserTmp.setPhone(phone);
					if (phone != null && phone.length() > 7) {
						tzzUser.setPhone(phone);
					}
				}
				individualUserService.merge(tzzUserTmp);
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
	
	/**
	 * 上传头像
	 * /jsp/login
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/fileupload", method = { RequestMethod.POST})
	public void  fileupload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {  
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> list = multipartRequest.getFiles("file");
		String userid = request.getParameter("userid");
		String resumeid = request.getParameter("id");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String topdegree = request.getParameter("topdegree");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String workyear = request.getParameter("workyear");
		String currentstate = request.getParameter("currentstate");
		String residence = request.getParameter("residence");
//		IndividualUser tzzuser =null;
//		if(StringHelper.isNotEmpty(userid)){
//			tzzuser = individualUserService.get(NumberHelper.string2Int(userid));
//		}
		String imgpath = "";
		if(list!=null && list.size()>0){
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
						.replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				//file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
				String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
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
		}
		JSONObject jsonObject=new JSONObject();
//		if(tzzuser!=null){
		if(resumeid!=null){
			ResumeBase resumeBase = resumeBaseService.get(NumberHelper.string2Int(resumeid));
			resumeBase.setAge(NumberHelper.string2Int(age));
			resumeBase.setName(name);
			resumeBase.setGender(NumberHelper.string2Int(gender));
			resumeBase.setPhone(phone);
			resumeBase.setEmail(email);
			resumeBase.setExperience(workyear);
			resumeBase.setCurrentstate(currentstate);
			resumeBase.setResidence(residence);
			resumeBase.setEdubackground(topdegree);
			if(StringHelper.isNotEmpty(imgpath)){
				resumeBase.setImage(imgpath);
			}
			Date modifytime = new Date();
			resumeBase.setModifytime(modifytime);
			resumeBaseService.merge(resumeBase);
			jsonObject.put("resume", resumeBase);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
		}
		
		jsonObject.put("success", true);
		jsonObject.put("userid", userid);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 完善个人基本信息
	 * /jsp/login
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/completeUserInfo", method = { RequestMethod.POST})
	public void  completeUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String topdegree = request.getParameter("topdegree");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String workyear = request.getParameter("workyear");
		String currentstate = StringHelper.null2String(request.getParameter("currentstate")).trim();
		String residence = request.getParameter("residence");
		if(currentstate.equals("我目前已离职，可快速到岗")){
			currentstate="1";
		}else if(currentstate.equals("我目前正在职，正考虑换个新环境")){
			currentstate="2";
		}else if(currentstate.equals("我暂时不想找工作")){
			currentstate="3";
		}else if(currentstate.equals("我是应届毕业生")){
			currentstate="4";
		}
		IndividualUser tzzuser =null;
		if(StringHelper.isNotEmpty(userid)){
			tzzuser = individualUserService.get(NumberHelper.string2Int(userid));
		}
		if(tzzuser!=null){
			tzzuser.setAge(NumberHelper.string2Int(age));
			tzzuser.setName(name);
			tzzuser.setGender(gender);
			tzzuser.setEmail(email);
			tzzuser.setWorkyears(workyear);
			tzzuser.setCurrentstate(currentstate);
			tzzuser.setResidence(residence);
			tzzuser.setEdubackground(topdegree);
			individualUserService.merge(tzzuser);
			//完善个人信息默认建立一个简历
			ResumeBase resumeBase = new ResumeBase();
			resumeBase.setUserid(NumberHelper.string2Int(userid));
			resumeBase.setAge(NumberHelper.string2Int(age));
			resumeBase.setName(name);
			resumeBase.setGender(NumberHelper.string2Int(gender));
			resumeBase.setPhone(tzzuser.getPhone());
			resumeBase.setEmail(email);
			resumeBase.setExperience(workyear);
			resumeBase.setCurrentstate(currentstate);
			resumeBase.setResidence(residence);
			resumeBase.setEdubackground(topdegree);
			resumeBase.setIsdefault(1);
			resumeBase.setResumename(name+"的简历");
			resumeBase.setCreatetime(new Date());
			resumeBase.setModifytime(new Date());
			resumeBase.setIntegritydegree(10);
			new SimpleDateFormat();
			resumeBaseService.persist(resumeBase);
		}
		request.getSession().setAttribute("individualuser", tzzuser);
		ResumeBase resumeBase = resumeBaseService.getByProerties("userid", tzzuser.getId());
		request.getSession().setAttribute("defaultResume", resumeBase);
		JSONObject jsonObject=new JSONObject();
		
		jsonObject.put("success", true);
		jsonObject.put("userid", userid);
		writeJSON(response, jsonObject);
	}
	

}
