package com.jeefw.controller.top;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeefw.model.sys.ResumeEducation;
import com.jeefw.model.sys.ResumeExpectation;
import com.jeefw.model.sys.ResumeProduction;
import com.jeefw.model.sys.ResumeProjectExperience;
import com.jeefw.model.sys.ResumeSkill;
import com.jeefw.model.sys.ResumeWorkExperience;
import com.jeefw.service.sys.IndividualUserService;
import com.jeefw.service.sys.ResumeBaseService;
import com.jeefw.service.sys.ResumeEducationService;
import com.jeefw.service.sys.ResumeExpectationService;
import com.jeefw.service.sys.ResumeProductionService;
import com.jeefw.service.sys.ResumeProjectExperienceService;
import com.jeefw.service.sys.ResumeSkillService;
import com.jeefw.service.sys.ResumeWorkExperienceService;
import com.jeefw.service.sys.TzzDictionaryService;

import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 简历管理。
 */
@Controller
@RequestMapping("/top/resume")
public class TopResumeController extends JavaEEFrameworkBaseController<IndividualUser> implements Constant {

	@Resource
	private IndividualUserService individualUserService;
	
	@Resource
	private ResumeBaseService resumeBaseService;
	
	@Resource
	private ResumeExpectationService resumeExpectationService;
	
	@Resource
	private ResumeWorkExperienceService workExperienceService;
	
	@Resource
	private ResumeProjectExperienceService projectExperienceService;
	
	@Resource
	private ResumeEducationService educationsService;
	
	@Resource
	private ResumeProductionService productionService;
	
	@Resource
	private ResumeSkillService skillService;

	@Resource
	private TzzDictionaryService tzzDictionaryService;
	
	// 查询简历基本
		@RequestMapping(value = "/getResumeInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public void getResumeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String resumeid = request.getParameter("resumeid");
			ResumeBase resume = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringHelper.isNotEmpty(resumeid)) {
				resume = resumeBaseService.get(NumberHelper.string2Int(resumeid));
			} 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", true);
			if (resume != null) {
				jsonObject.put("resume",resume);
				jsonObject.put("modifytime",sdf.format(resume.getModifytime()));
				
			}
			writeJSON(response, jsonObject);
		}

	@RequestMapping(value = "/modifyBaseInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void modifyBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			JSONObject jsonObject = new JSONObject();
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
			String imgpath = request.getParameter("imgpath");
			if(resumeid!=null){
				ResumeBase resumeBase = resumeBaseService.get(NumberHelper.string2Int(resumeid));
				if(resumeBase!=null){
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
			}
			
			jsonObject.put("success", true);
			writeJSON(response, jsonObject);
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
	public void  fileupload(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {  
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> list = multipartRequest.getFiles("file");
		String userid = request.getParameter("userid");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String topdegree = request.getParameter("topdegree");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String workyear = request.getParameter("workyear");
		String currentstate = request.getParameter("currentstate");
		String residence = request.getParameter("residence");
		IndividualUser tzzuser =null;
		if(StringHelper.isNotEmpty(userid)){
			tzzuser = individualUserService.get(NumberHelper.string2Int(userid));
		}
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
		if(tzzuser!=null){
			ResumeBase resumeBase = new ResumeBase();
			resumeBase.setUserid(NumberHelper.string2Int(userid));
			resumeBase.setAge(NumberHelper.string2Int(age));
			resumeBase.setName(name);
			resumeBase.setGender(NumberHelper.string2Int(gender));
			resumeBase.setPhone(phone);
			resumeBase.setEmail(email);
			resumeBase.setExperience(workyear);
			resumeBase.setCurrentstate(currentstate);
			resumeBase.setResidence(residence);
			resumeBase.setEdubackground(topdegree);
			resumeBase.setImage(imgpath);
			resumeBaseService.persist(resumeBase);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("userid", userid);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 上传头像
	 * /jsp/login
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/uploadHeadImg", method = { RequestMethod.POST})
	public void  uploadHeadImg(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {  
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> list = multipartRequest.getFiles("file");
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
		jsonObject.put("success", true);
		jsonObject.put("imgpath", imgpath);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 修改简历名称
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/renameResume", method = { RequestMethod.POST})
	public void  renameResume(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String resumeName = request.getParameter("resumeName");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			ResumeBase resume = resumeBaseService.get(NumberHelper.string2Int(resumeid));
			if(resumeName!=null && !resumeName.equals(resume.getResumename())){
				resume.setResumename(resumeName);
				Date modifytime = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				jsonObject.put("modifytime", sdf.format(resume.getModifytime()));
			}
		}
		
		jsonObject.put("success", true);
		jsonObject.put("resumeName", resumeName);
		
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查询期望工作信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getResumeExpectation", method = { RequestMethod.POST})
	public void  getResumeExpectation(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			ResumeExpectation expectation = resumeExpectationService.getByProerties("resumeid", NumberHelper.string2Int(resumeid));
			if(expectation!=null){
				jsonObject.put("expectation", expectation);
			}
		}
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 保存期望工作信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveExpectationInfo", method = { RequestMethod.POST})
	public void  saveExpectationInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String city = request.getParameter("city");
		String positionType = request.getParameter("positionType");
		String positionName = request.getParameter("positionName");
		String salarys = request.getParameter("salarys");
		ResumeExpectation expectation = null;
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			expectation = resumeExpectationService.getByProerties("resumeid", resumeid_int);
			if(expectation!=null){
				expectation.setCity(city);
				expectation.setCareer(positionName);
				expectation.setNature(positionType);
				expectation.setSalary(salarys);
				resumeExpectationService.merge(expectation);
			}else{
				expectation = new ResumeExpectation();
				expectation.setResumeid(resumeid_int);
				expectation.setCity(city);
				expectation.setCareer(positionName);
				expectation.setNature(positionType);
				expectation.setSalary(salarys);
				resumeExpectationService.persist(expectation);
			}
			Date modifytime = new Date();
			resume.setModifytime(modifytime);
			resumeBaseService.merge(resume);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
		}
		
		
		jsonObject.put("success", true);
		jsonObject.put("expectation", expectation);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 保存工作经历
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveWorkExperience", method = { RequestMethod.POST})
	public void  saveWorkExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String company = request.getParameter("companyName");
		String positionName = request.getParameter("positionName");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String expid = request.getParameter("expid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			ResumeWorkExperience experience = null;
			if(StringHelper.isNotEmpty(expid)){
				experience = workExperienceService.get(NumberHelper.string2Int(expid));
				experience.setCompany(company);
				experience.setJobname(positionName);
				experience.setFromyear(startYear);
				experience.setFrommonth(startMonth);
				experience.setToyear(endYear);
				experience.setTomonth(endMonth);
				workExperienceService.merge(experience);
			}else{
				experience = new ResumeWorkExperience();
				experience.setResumeid(resumeid_int);
				experience.setCompany(company);
				experience.setJobname(positionName);
				experience.setFromyear(startYear);
				experience.setFrommonth(startMonth);
				experience.setToyear(endYear);
				experience.setTomonth(endMonth);
				workExperienceService.persist(experience);
			}
			
			Date modifytime = new Date();
			resume.setModifytime(modifytime);
			resumeBaseService.merge(resume);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
			List<ResumeWorkExperience> list = workExperienceService.queryByProerties("resumeid", resumeid_int);
			jsonObject.put("workExperiences", list);
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查看工作经历
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getWorkExperience", method = { RequestMethod.POST})
	public void  getWorkExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		List<ResumeWorkExperience> list = workExperienceService.queryByProerties("resumeid", NumberHelper.string2Int(resumeid));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("workExperiences", list);
		writeJSON(response, jsonObject);
	}
	/**
	 * 删除工作经历
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delWorkExperience", method = { RequestMethod.POST})
	public void  delWorkExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String expid = request.getParameter("expid");
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(expid)){
			workExperienceService.deleteByPK(NumberHelper.string2Int(expid));
			if(StringHelper.isNotEmpty(resumeid)){
				int resumeid_int = NumberHelper.string2Int(resumeid);
				ResumeBase resume = resumeBaseService.get(resumeid_int);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeWorkExperience> list = workExperienceService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("workExperiences", list);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 保存项目经验
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveProjectExperience", method = { RequestMethod.POST})
	public void  saveProjectExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String projectName = request.getParameter("projectName");
		String positionName = request.getParameter("positionName");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String projectDesc = request.getParameter("projectRemark");
		String projectId = request.getParameter("projectid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			ResumeProjectExperience experience = null;
			if(StringHelper.isNotEmpty(projectId)){
				experience = projectExperienceService.get(NumberHelper.string2Int(projectId));
				experience.setProject(projectName);
				experience.setDuty(positionName);
				experience.setFromyear(startYear);
				experience.setFrommonth(startMonth);
				experience.setToyear(endYear);
				experience.setTomonth(endMonth);
				experience.setProjectdesc(projectDesc);
				projectExperienceService.merge(experience);
			}else{
				experience = new ResumeProjectExperience();
				experience.setResumeid(resumeid_int);
				experience.setProject(projectName);
				experience.setDuty(positionName);
				experience.setFromyear(startYear);
				experience.setFrommonth(startMonth);
				experience.setToyear(endYear);
				experience.setTomonth(endMonth);
				experience.setProjectdesc(projectDesc);
				projectExperienceService.persist(experience);
			}
			jsonObject.put("projectExperience", experience);
			Date modifytime = new Date();
			resume.setModifytime(modifytime);
			resumeBaseService.merge(resume);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
			List<ResumeProjectExperience> list = projectExperienceService.queryByProerties("resumeid", resumeid_int);
			jsonObject.put("projectExperiences", list);
			jsonObject.put("code", 3);
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 删除项目经验
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delProjectExperience", method = { RequestMethod.POST})
	public void  delProjectExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String projectid = request.getParameter("id");
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(projectid)){
			projectExperienceService.deleteByPK(NumberHelper.string2Int(projectid));
			if(StringHelper.isNotEmpty(resumeid)){
				int resumeid_int = NumberHelper.string2Int(resumeid);
				ResumeBase resume = resumeBaseService.get(resumeid_int);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeProjectExperience> list = projectExperienceService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("projectExperiences", list);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查看项目经验
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getProjectExperience", method = { RequestMethod.POST})
	public void  getProjectExperience(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		List<ResumeProjectExperience> list = projectExperienceService.queryByProerties("resumeid", NumberHelper.string2Int(resumeid));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("projectExperiences", list);
		writeJSON(response, jsonObject);
	}
	
	
	/**
	 * 保存教育经历
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveEducation", method = { RequestMethod.POST})
	public void  saveEducation(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String schoolName = request.getParameter("schoolName");
		String professional = request.getParameter("professional");
		String degree = request.getParameter("education");
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String eduid = request.getParameter("eduid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			ResumeEducation education = null;
			if(StringHelper.isNotEmpty(eduid)){
				education = educationsService.get(NumberHelper.string2Int(eduid));
				education.setCollege(schoolName);
				education.setMajor(professional);
				education.setFromyear(startYear);
				education.setToyear(endYear);
				education.setDegree(degree);
				educationsService.merge(education);
			}else{
				education = new ResumeEducation();
				education.setResumeid(resumeid_int);
				education.setCollege(schoolName);
				education.setMajor(professional);
				education.setFromyear(startYear);
				education.setToyear(endYear);
				education.setDegree(degree);
				educationsService.persist(education);
			}
			jsonObject.put("education", education);
			Date modifytime = new Date();
			resume.setModifytime(modifytime);
			resumeBaseService.merge(resume);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
			List<ResumeEducation> list = educationsService.queryByProerties("resumeid", resumeid_int);
			jsonObject.put("educationExperiences", list);
			jsonObject.put("code", 3);
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 删除教育背景
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delEducation", method = { RequestMethod.POST})
	public void  delEducation(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String eduid = request.getParameter("id");
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(eduid)){
			educationsService.deleteByPK(NumberHelper.string2Int(eduid));
			if(StringHelper.isNotEmpty(resumeid)){
				int resumeid_int = NumberHelper.string2Int(resumeid);
				ResumeBase resume = resumeBaseService.get(resumeid_int);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeEducation> list = educationsService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("educationExperiences", list);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查看教育背景
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getEducations", method = { RequestMethod.POST})
	public void  getEducations(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		List<ResumeEducation> list = educationsService.queryByProerties("resumeid", NumberHelper.string2Int(resumeid));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("educations", list);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 保存自我描述
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveSelfAssessment", method = { RequestMethod.POST})
	public void  saveSelfAssessment(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String myRemark = request.getParameter("myRemark");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			if(resume!=null){
				resume.setSelfassessment(myRemark);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				jsonObject.put("myRemark", myRemark);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 保存作品
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveProduction", method = { RequestMethod.POST})
	public void  saveProduction(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String productionid = request.getParameter("wsid");
		String url = request.getParameter("url");
		String introduction = request.getParameter("workName");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			ResumeProduction production = null;
			if(StringHelper.isNotEmpty(productionid)){
				production = productionService.get(NumberHelper.string2Int(productionid));
				production.setHref(url);
				production.setIntroduction(introduction);
				productionService.merge(production);
			}else{
				production = new ResumeProduction();
				production.setResumeid(resumeid_int);
				production.setHref(url);
				production.setIntroduction(introduction);
				production.setName("");
				productionService.persist(production);
			}
			jsonObject.put("production", production);
			if(resume!=null){
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeProduction> list = productionService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("productions", list);
				jsonObject.put("code", 3);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查看作品展示
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getProductions", method = { RequestMethod.POST})
	public void  getProductions(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		List<ResumeProduction> list = productionService.queryByProerties("resumeid", NumberHelper.string2Int(resumeid));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("productions", list);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 删除个人作品
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delProduction", method = { RequestMethod.POST})
	public void  delProduction(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String productionid = request.getParameter("id");
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(productionid)){
			productionService.deleteByPK(NumberHelper.string2Int(productionid));
			if(StringHelper.isNotEmpty(resumeid)){
				int resumeid_int = NumberHelper.string2Int(resumeid);
				ResumeBase resume = resumeBaseService.get(resumeid_int);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeProduction> list = productionService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("productions", list);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	
	/**
	 * 保存技能特长
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveSkill", method = { RequestMethod.POST})
	public void  saveSkill(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("id");
		String skillName = request.getParameter("skillName");
		String degree = request.getParameter("skilldegree");
		String desc = request.getParameter("skillDescription");
		String skillid = request.getParameter("skillid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(resumeid)){
			int resumeid_int = NumberHelper.string2Int(resumeid);
			ResumeBase resume = resumeBaseService.get(resumeid_int);
			ResumeSkill skill = null;
			if(StringHelper.isNotEmpty(skillid)){
				skill = skillService.get(NumberHelper.string2Int(skillid));
				skill.setName(skillName);
				skill.setDegree(degree);
				skill.setIntroduction(desc);
				skillService.merge(skill);
			}else{
				skill = new ResumeSkill();
				skill.setResumeid(resumeid_int);
				skill.setName(skillName);
				skill.setDegree(degree);
				skill.setIntroduction(desc);
				skillService.persist(skill);
			}
			jsonObject.put("skill", skill);
			Date modifytime = new Date();
			resume.setModifytime(modifytime);
			resumeBaseService.merge(resume);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jsonObject.put("modifytime", sdf.format(modifytime));
			List<ResumeSkill> list = skillService.queryByProerties("resumeid", resumeid_int);
			jsonObject.put("skills", list);
			jsonObject.put("code", 3);
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 删除技能特长
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delSkill", method = { RequestMethod.POST})
	public void  delSkill(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String skillid = request.getParameter("id");
		String resumeid = request.getParameter("resumeid");
		JSONObject jsonObject=new JSONObject();
		if(StringHelper.isNotEmpty(skillid)){
			skillService.deleteByPK(NumberHelper.string2Int(skillid));
			if(StringHelper.isNotEmpty(resumeid)){
				int resumeid_int = NumberHelper.string2Int(resumeid);
				ResumeBase resume = resumeBaseService.get(resumeid_int);
				Date modifytime = new Date();
				resume.setModifytime(modifytime);
				resumeBaseService.merge(resume);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.put("modifytime", sdf.format(modifytime));
				List<ResumeSkill> list = skillService.queryByProerties("resumeid", resumeid_int);
				jsonObject.put("skills", list);
			}
		}
		
		jsonObject.put("success", true);
		writeJSON(response, jsonObject);
	}
	
	/**
	 * 查看技能特长
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getSkills", method = { RequestMethod.POST})
	public void  getSkills(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String resumeid = request.getParameter("resumeid");
		List<ResumeSkill> list = skillService.queryByProerties("resumeid", NumberHelper.string2Int(resumeid));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("skills", list);
		writeJSON(response, jsonObject);
	}
}
