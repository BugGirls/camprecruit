package com.jeefw.controller.top;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.model.sys.TzzUserAddress;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzUserAddressService;
import com.jeefw.service.sys.TzzUserEvaluationService;
import com.jeefw.service.sys.TzzUserService;
import com.jeefw.service.sys.TzzUserSetvipService;

import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 个人中心。
 */
@Controller
@RequestMapping("/top/center") 
public class TopUserController extends JavaEEFrameworkBaseController<TzzUser> implements Constant {

	
	
	@Resource
	private TzzUserService tzzUserService;
	
	@Resource
	private TzzUserAddressService tzzUserAddressService;
	
	
	@Resource
	private TzzUserSetvipService tzzUserSetvipService;
	
	@Resource
	private TzzUserEvaluationService tzzUserEvaluationService;
	
	@Resource
	private TzzDictionaryService tzzDictionaryService;
	
	
			//查询注册用户信息
			@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
			public void  getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
				String userid= request.getParameter("userid");
				String userphone= request.getParameter("userphone");
				TzzUser tzzUser = null;
				if(StringHelper.isNotEmpty(userid)){
					tzzUser = tzzUserService.get(NumberHelper.string2Int(userid));
				}else{
					tzzUser = tzzUserService.getByProerties("phone", userphone);
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("success", true);
				if(tzzUser!=null){
					jsonObject.put("realname", tzzUser.getRealname());
					jsonObject.put("username", tzzUser.getUsername());
					jsonObject.put("phone", tzzUser.getPhone());
					if(tzzUser.getSchoolid()!=null&&tzzUser.getSchoolid()>0){
						jsonObject.put("schoolid", tzzUser.getSchoolid());
					}
				}
				writeJSON(response, jsonObject);
			}
			
		//查询注册用户信息
		@RequestMapping(value = "/getuserinfo", method = { RequestMethod.POST, RequestMethod.GET })
		public void  getuserinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String userid= request.getParameter("userid");
			String schoolid= request.getParameter("schoolid");
//			String userphone= request.getParameter("userphone");
//			TzzUser tzzUser = tzzUserService.getByProerties("phone", userphone);
			TzzUser tzzUser = tzzUserService.get(NumberHelper.string2Int(userid));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", false);
			
			long tzzUserbirthday = 0;
			if (null != tzzUser){
				if(null!=tzzUser.getBirthday())
					tzzUserbirthday = tzzUser.getBirthday().getTime();
				if(tzzUser.getuType()!=null){
					if(tzzUser.getuType().equals("1")||tzzUser.getuType().equals("5")){
						jsonObject.put("schoolid", tzzUser.getSchoolid());
//						Float avgmark = stuCourComtService.totalMark(tzzUser.getId());
//						jsonObject.put("useravgmark",avgmark);
//					}else if(tzzUser.getuType().equals("2")){
//						Float avgmark = coachCommentService.getAverageMark(tzzUser.getId());
//						jsonObject.put("useravgmark",avgmark);
					}else if(tzzUser.getuType().equals("2")){
						jsonObject.put("campsiteid", tzzUser.getCampsiteid());
					}
				}
				jsonObject.put("success", true);
				jsonObject.put("userinfo", tzzUser.getBaseInfo());
				jsonObject.put("birthday",tzzUserbirthday);
				
				
			}
			writeJSON(response, jsonObject);
		}
	

	
	
	
	@RequestMapping(value = "/showUserDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public void  showUserDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
			try{
				TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
				if(tzzUser == null ){
					request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
				}else{
					/*
					 * 查询所有的地址信息。按照state。
					 */
					Map <String ,String >sortedCondition = new HashMap<String ,String>() ;
					sortedCondition.put("state", "desc");
					List <TzzUserAddress>  list = tzzUserAddressService.queryByProerties("userId", tzzUser.getId(), sortedCondition);
					request.setAttribute("tzzUser", tzzUser);
					request.setAttribute("userAddressList",list);
					String defaultPage = request.getParameter("defaultPage");
					request.setAttribute("defaultPage",defaultPage);
					request.getRequestDispatcher("/jsp/center_zhanghu.jsp").forward(request,response); 
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	
	}
	
	
	
	
	@RequestMapping(value = "/modifyDefaultAddress", method = { RequestMethod.POST, RequestMethod.GET })
	public void  modifyDefaultAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				/*
				 * 查询所有的地址信息。按照state。
				 */
				String addressIdStr = request.getParameter("addressId");
				int addressId = 0 ;
				if (addressIdStr != null && addressIdStr.length() > 0 && addressIdStr.matches("[\\d]+")){
					addressId = Integer.valueOf(addressIdStr);
					jsonObj.put("err", 1);
					jsonObj.put("msg", "要修改的默认地址不存在，请确定！");
				}
				if (addressId > 0 ){
					String [] conditionName = new String[2];
					Object [] conditionValue = new Object[2];
					conditionName[0] = "state";
					conditionValue[0] = "1";
					conditionName[1] = "userId";
					conditionValue[1] = tzzUser.getId();
					tzzUserAddressService.updateByProperties(conditionName, conditionValue, "state", "0");
					TzzUserAddress userAddress = tzzUserAddressService.get(addressId);
					userAddress.setState("1");
					tzzUserAddressService.merge(userAddress);
					Map <String ,String >sortedCondition = new HashMap<String ,String>() ;
					sortedCondition.put("state", "desc");
					List <TzzUserAddress>  list = tzzUserAddressService.queryByProerties("userId", tzzUser.getId(), sortedCondition);
					jsonObj.put("userAddressList",list);
					jsonObj.put("err", 0);
					jsonObj.put("msg", "修改成功！");
					
				}
				writeJSON(response, jsonObj);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err", 1);
			jsonObj.put("msg", "程序运行错误！");
			writeJSON(response, jsonObj);
		}

	}
	
	@RequestMapping(value = "/addUserAddress", method = { RequestMethod.POST, RequestMethod.GET })
	public void  addUserAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				/*
				 * 查询所有的地址信息。按照state。
				 */
				String userName = request.getParameter("userName");
				userName = new String(userName.getBytes("iso-8859-1"),"utf-8");
				String phone = request.getParameter("phone");
				String postCode = request.getParameter("postCode");
				String province = request.getParameter("province");
				province = new String(province.getBytes("iso-8859-1"),"utf-8");
				String city = request.getParameter("city");
				city = new String(city.getBytes("iso-8859-1"),"utf-8");
				String county = request.getParameter("county");
				county = new String(county.getBytes("iso-8859-1"),"utf-8");
				String address = request.getParameter("address");
				address = new String(address.getBytes("iso-8859-1"),"utf-8");
				String state = request.getParameter("state");
				if (state == null || !state.equals("1") ) {
					state = "0";
				}
				TzzUserAddress  userAddress = new TzzUserAddress();
				userAddress.setUserId(tzzUser.getId());
				userAddress.setUserName(userName);
				userAddress.setPhone(phone);
				userAddress.setPostcode(postCode);
				userAddress.setProvince(province);
				userAddress.setCity(city);
				userAddress.setCounty(county);
				userAddress.setAddress(address);
				userAddress.setState(state);
				if (state.equals("1")){
					String [] conditionName = new String[2];
					Object [] conditionValue = new Object[2];
					conditionName[0] = "state";
					conditionValue[0] = "1";
					conditionName[1] = "userId";
					conditionValue[1] = tzzUser.getId();
					tzzUserAddressService.updateByProperties(conditionName, conditionValue, "state", "0");
				}
				tzzUserAddressService.persist(userAddress);
				Map <String ,String >sortedCondition = new HashMap<String ,String>() ;
				sortedCondition.put("state", "desc");
				List <TzzUserAddress>  list = tzzUserAddressService.queryByProerties("userId", tzzUser.getId(), sortedCondition);
				jsonObj.put("userAddressList",list);
				jsonObj.put("err", 0);
				jsonObj.put("msg", "添加成功！");
				writeJSON(response, jsonObj);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err", 1);
			jsonObj.put("msg", "程序运行错误！");
			writeJSON(response, jsonObj);
		}
	}

	@RequestMapping(value = "/modifyUserAddress", method = { RequestMethod.POST, RequestMethod.GET })
	public void  modifyUserAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				/*
				 * 查询所有的地址信息。按照state。
				 */
				int addressId = 0 ;
				String addressIdStr = request.getParameter("addressId");
				if (addressIdStr != null && addressIdStr.length() > 0 && addressIdStr.matches("[\\d]+")){
					addressId = Integer.valueOf(addressIdStr);
					jsonObj.put("err", 1);
					jsonObj.put("msg", "要修改的默认地址不存在，请确定！");
				}
				if (addressId > 0 ){
					String userName = request.getParameter("userName");
					userName = new String(userName.getBytes("iso-8859-1"),"utf-8");
					String phone = request.getParameter("phone");
					String postCode = request.getParameter("postCode");
					String province = request.getParameter("province");
					province = new String(province.getBytes("iso-8859-1"),"utf-8");
					String city = request.getParameter("city");
					city = new String(city.getBytes("iso-8859-1"),"utf-8");
					String county = request.getParameter("county");
					county = new String(county.getBytes("iso-8859-1"),"utf-8");
					String address = request.getParameter("address");
					address = new String(address.getBytes("iso-8859-1"),"utf-8");
					String state = request.getParameter("state");
					if (state == null || !state.equals("1") ) {
						state = "0";
					}
					if(state != null && state.equals("1")){
						String [] conditionName = new String[2];
						Object [] conditionValue = new Object[2];
						conditionName[0] = "state";
						conditionValue[0] = "1";
						conditionName[1] = "userId";
						conditionValue[1] = tzzUser.getId();
						tzzUserAddressService.updateByProperties(conditionName, conditionValue, "state", "0");
					}
					
					TzzUserAddress  userAddress = tzzUserAddressService.get(addressId);
					if (userName != null && userName.length() > 0 ){
						userAddress.setUserName(userName);
					}
					if(phone != null && phone.length() > 0 ){
						userAddress.setPhone(phone);
					}
					if(postCode != null && postCode.length() > 0 ){
						userAddress.setPostcode(postCode);
					}
					if(province != null && province.length() > 0 ){
						userAddress.setProvince(province);
					}
					if(city != null && city.length() > 0 ){
						userAddress.setCity(city);
					}
					if (county != null && county.length() > 0 ){
						userAddress.setCounty(county);
					}
					if (address != null && address.length() > 0){
						userAddress.setAddress(address);
					}
					if (state != null && state.length() > 0){
						userAddress.setState(state);
					}
					
					tzzUserAddressService.merge(userAddress);
					Map <String ,String >sortedCondition = new HashMap<String ,String>() ;
					sortedCondition.put("state", "desc");
					List <TzzUserAddress>  list = tzzUserAddressService.queryByProerties("userId", tzzUser.getId(), sortedCondition);
					jsonObj.put("userAddressList",list);
					
					jsonObj.put("err", 0);
					jsonObj.put("msg", "修改成功！");
					writeJSON(response, jsonObj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err", 1);
			jsonObj.put("msg", "程序运行错误！");
			writeJSON(response, jsonObj);
		}

	}
	
	
	
	
	@RequestMapping(value = "/getAddressById", method = { RequestMethod.POST, RequestMethod.GET })
	public void  getAddressById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				/*
				 * 查询所有的地址信息。按照state。
				 */
				String addressIdStr = request.getParameter("addressId");
				int addressId = 0 ;
				if (addressIdStr != null && addressIdStr.length() > 0 && addressIdStr.matches("[\\d]+")){
					addressId = Integer.valueOf(addressIdStr);
					jsonObj.put("err", 1);
					jsonObj.put("msg", "要修改的默认地址不存在，请确定！");
				}
				TzzUserAddress userAddress = null;
				if (addressId > 0 ){
					userAddress = tzzUserAddressService.get(addressId);
					
				}
				if (userAddress != null){
					jsonObj.put("err", 0);
					jsonObj.put("msg", "获取成功");
					JSONObject  json = JSONObject.fromObject(userAddress);
					jsonObj.put("info", json);
				}else{
					jsonObj.put("err", 1);
					jsonObj.put("msg", "获取失败");
					jsonObj.put("info", null);
				}
				writeJSON(response, jsonObj);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err", 1);
			jsonObj.put("msg", "程序运行错误！");
			writeJSON(response, jsonObj);
		}

	}
	
	
	@RequestMapping(value = "/delUserAddress", method = { RequestMethod.POST, RequestMethod.GET })
	public void  delUserAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				/*
				 * 查询所有的地址信息。按照state。
				 */
				String addressIdStr = request.getParameter("addressId");
				int addressId = 0 ;
				if (addressIdStr != null && addressIdStr.length() > 0 && addressIdStr.matches("[\\d]+")){
					addressId = Integer.valueOf(addressIdStr);
					jsonObj.put("err", 1);
					jsonObj.put("msg", "要修改的默认地址不存在，请确定！");
				}
				
				if (addressId > 0 ){
					tzzUserAddressService.deleteByPK(addressId);
					jsonObj.put("err", 0);
					jsonObj.put("msg", "删除成功！");
				}
				
				Map <String ,String >sortedCondition = new HashMap<String ,String>() ;
				sortedCondition.put("state", "desc");
				List <TzzUserAddress>  list = tzzUserAddressService.queryByProerties("userId", tzzUser.getId(), sortedCondition);
				jsonObj.put("userAddressList",list);
				writeJSON(response, jsonObj);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err", 1);
			jsonObj.put("msg", "程序运行错误！");
			writeJSON(response, jsonObj);
		}

	}
	
	@RequestMapping(value = "/showAddShare", method = { RequestMethod.POST, RequestMethod.GET })
	public void  showAddShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
			try{
				TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
				if (tzzUser == null ){
					request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
				}
				request.setAttribute("tzzUser", tzzUser);
				request.getRequestDispatcher("/jsp/center_fx_tj.jsp").forward(request,response); 
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value={"/fileUpload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public void fileUpload(@RequestParam(value="file", required=false) MultipartFile file,HttpServletRequest request, HttpServletResponse response)throws Exception  {
	    RequestContext requestContext = new RequestContext(request);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
	    JSONObject json = new JSONObject();
	    String image = "";
	    if (!file.isEmpty()) {
	      if (file.getSize() > 2097152L)
	        json.put("message", requestContext.getMessage("g_fileTooLarge"));
	      else
	        try {
	          String originalFilename = file.getOriginalFilename();
	          String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
	          File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
	          if (!filePath.exists()) {
	            filePath.mkdirs();
	          }
	          file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
	          String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;

	          image = destinationFilePath;
	          json.put("err", 0);
	          json.put("url", destinationFilePath);
	          json.put("message", requestContext.getMessage("g_uploadSuccess"));
	        } catch (Exception e) {
	          e.printStackTrace();
	          json.put("message", requestContext.getMessage("g_uploadFailure"));
	        }
	    }
	    else {
	      json.put("message", requestContext.getMessage("g_uploadNotExists"));
	      json.put("err", 1);
	    }
	    
	    TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
	    tzzUser.setImage(image);
		if(tzzUser == null ){
			request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
		}else{
			int userId = tzzUser.getId();
			TzzUser tzzUserTmp = tzzUserService.get(userId);
			tzzUserTmp.setImage(image);
			tzzUserService.merge(tzzUserTmp);
		}
		showUserDetail(request, response);
	}
	
	
	@RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void  modifyUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		try{
			TzzUser tzzUser=(TzzUser)request.getSession().getAttribute("tzzuser");
			if(tzzUser == null ){
				request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
			}else{
				String nickname = request.getParameter("nickname");
				String name = request.getParameter("name");
				name = new String(name.getBytes("iso-8859-1"),"utf-8");
				String email = request.getParameter("email");
				String phone = request.getParameter("phone");
				String qq = request.getParameter("qq");
				String sex = request.getParameter("sex");
				
				jsonObj.put("err", 0);
				jsonObj.put("msg", "修改成功！");
			
				TzzUser tzzUserTmp = tzzUserService.get(tzzUser.getId());
				if (nickname != null && nickname.length() > 0 ){
					tzzUserTmp.setRealname(nickname);
					tzzUser.setRealname(nickname);
				}
				if(name != null && name.length() > 0 ){
					tzzUserTmp.setUsername(name);
					tzzUser.setUsername(name);
				}
				if (email != null && email.length() > 0 ){
					tzzUserTmp.setEmail(email);
					tzzUser.setEmail(email);
				}
				if (phone != null && phone.length() > 0 ){
					tzzUserTmp.setPhone(phone);
					if (phone != null && phone.length() > 7 ){
						tzzUser.setPhone(phone );
					}
					tzzUser.setPhoneNick(phone);
				}
				if (qq != null && qq.length() > 0 ){
					tzzUserTmp.setQq(qq);
					tzzUser.setQq(qq);
				}
				if (sex != null && sex.length() > 0 ){
					tzzUserTmp.setSex(sex);
					tzzUser.setSex(sex);
				}
				tzzUserService.merge(tzzUserTmp);
				writeJSON(response, jsonObj);
			}
//			showUserDetail(request, response);
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("err",4);
			jsonObj.put("msg", "操作失败！");
			writeJSON(response, jsonObj);
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public Date datejia(Date date ,int jia ){
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
