package com.jeefw.controller.sys;
 
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Company;
import com.jeefw.model.sys.CompanyShenhe;
import com.jeefw.model.sys.CompanyUser;
import com.jeefw.service.sys.CompanyShenheService;
import com.jeefw.service.sys.CompanyUserService;

import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 企业信息的控制层
 */
@Controller
@RequestMapping("/sys/companyuser")
public class CompanyUserController extends JavaEEFrameworkBaseController<Company> implements Constant {

	@Resource
	private CompanyUserService companyUserService;
	
	@Resource
	private CompanyShenheService companyShenheService;
	
	// 查询招聘主题包含的岗位的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getCompanyList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCompanyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		CompanyUser company = new CompanyUser();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				company.setFlag("OR");
			} else {
				company.setFlag("AND");
			}
		}
		company.setFirstResult((firstResult - 1) * maxResults);
		company.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		company.setSortedConditions(sortedCondition);
		QueryResult<CompanyUser> queryResult = companyUserService.doPaginationQuery(company);
		JqGridPageView<CompanyUser>  tzzPromotionTypeListView = new JqGridPageView<CompanyUser>();
		tzzPromotionTypeListView.setMaxResults(maxResults);
		List<CompanyUser> tzzPromotionTypeList = queryResult.getResultList();
		tzzPromotionTypeListView.setRows(tzzPromotionTypeList);
		tzzPromotionTypeListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzPromotionTypeListView);
	}
	
	// 获取单条记录
	@RequestMapping("/getCompany")
	public void getCompany(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") Integer id) throws IOException {
		CompanyUser company = companyUserService.get(id);
		JSONObject json = new JSONObject();
		if (null != company && 0 != company.getId()) {
			json.put("obj", company);
			json.put("success", true);
			writeJSON(response, json);
		} else {
			json.put("success", false);
			writeJSON(response, json);
		}
	}

	// 操作公司信息的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateCompany", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteCompany(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String image = request.getParameter("image");
			Company entity = new Company();
			 
			
			 
			if (StringUtils.isBlank(image)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请选择图片");
				writeJSON(response, result);
			}else {
				
				if (oper.equals("edit")) {
					entity.setId(Integer.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCreatetime(new Date() );
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除
	@RequestMapping("/deleteCompany")
	public void deleteCompany(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = companyUserService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
		@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
		public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
			RequestContext requestContext = new RequestContext(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String id= request.getParameter("id");  
			JSONObject json = new JSONObject();
			String image ="";
			if (file != null && !file.isEmpty()) {
				if (file.getSize() > 2097152) {//============最大两兆
					json.put("message", requestContext.getMessage("g_fileTooLarge"));
				} else {
					try {
						String originalFilename = file.getOriginalFilename();
						//System.out.println(originalFilename);
						String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
						String imagePath = URLDecoder.decode(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())),"utf-8");
						File filePath = new File(imagePath);
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						file.transferTo(new File(filePath.getAbsolutePath() + "/" + fileName));
						String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
		 
						image=destinationFilePath;
						json.put("status", "OK"); 
						json.put("url", destinationFilePath);
						json.put("message", requestContext.getMessage("g_uploadSuccess"));
					} catch (Exception e) {
						e.printStackTrace();
						json.put("message", requestContext.getMessage("g_uploadFailure"));
					}
				}
			} else {
				json.put("message", requestContext.getMessage("g_uploadNotExists"));
			}
			
			String name = StringHelper.null2String(request.getParameter("name"));
			CompanyUser company=new CompanyUser();
			
			if(StringHelper.isNotEmpty(id)){ 
				CompanyUser entity=companyUserService.get(NumberHelper.string2Int(id));
				company.setId(NumberHelper.string2Int(id));
				company.setCreateTime(entity.getCreateTime());
				companyUserService.merge(company);
			}else {
				company.setCreateTime(new Date());
				companyUserService.persist(company);
			}  
			
	//		String msg=json.getString("message");
	//		if(msg.equals("文件不存在！")){
	//			String htmlString="<script language=javascript>alert('<p1 style='color:red;' >文件不存在！请返回！</p1>');history.go(-1);</script>";
	//			writeJSON(response, htmlString);
	//		}else{  
 			String htmlString="<script language=javascript> history.go(-1);</script>";
 			writeJSON(response, htmlString);
	//		} 
		}
		
		// 查询企业用户审核的表格，包括分页、搜索和排序
		@RequestMapping(value = "/getCompanyShenheList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getCompanyShenheList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Integer firstResult = Integer.valueOf(request.getParameter("page"));
			Integer maxResults = Integer.valueOf(request.getParameter("rows"));
			String sortedObject = request.getParameter("sidx");
			String sortedValue = request.getParameter("sord");
			String filters = request.getParameter("filters");
			CompanyShenhe company = new CompanyShenhe();
			if (StringUtils.isNotBlank(filters)) {
				JSONObject jsonObject = JSONObject.fromObject(filters);
				if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
					company.setFlag("OR");
				} else {
					company.setFlag("AND");
				}
			}
			company.setFirstResult((firstResult - 1) * maxResults);
			company.setMaxResults(maxResults);
			Map<String, String> sortedCondition = new HashMap<String, String>();
			sortedCondition.put(sortedObject, sortedValue);
			company.setSortedConditions(sortedCondition);
			QueryResult<CompanyShenhe> queryResult = companyShenheService.doPaginationQuery(company);
			JqGridPageView<CompanyShenhe>  tzzPromotionTypeListView = new JqGridPageView<CompanyShenhe>();
			tzzPromotionTypeListView.setMaxResults(maxResults);
			List<CompanyShenhe> tzzPromotionTypeList = queryResult.getResultList();
			tzzPromotionTypeListView.setRows(tzzPromotionTypeList);
			tzzPromotionTypeListView.setRecords(queryResult.getTotalCount());
			writeJSON(response, tzzPromotionTypeListView);
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

		// 获取单条记录
		@RequestMapping("/companyShenheOperation")
		public void companyShenheOperation(HttpServletRequest request, HttpServletResponse response,
				@RequestParam("shenheid") Integer id,@RequestParam("stage") Integer stage,@RequestParam("shenheyijian") String shenheyijian) throws IOException {
			CompanyShenhe company = companyShenheService.getByProerties("userid", id);
			CompanyUser companyUser = companyUserService.get(id);
			JSONObject json = new JSONObject();
			if (null != company && 0 != company.getUserid()) {
				company.setStage(stage);
				company.setShenheyijian(shenheyijian);
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				
				company.setShenheTime(sdf.format(new Date()));
				companyShenheService.merge(company);
				if(stage==1){
					companyUser.setState(2);
				}else if(stage==2){
					companyUser.setState(3);
				}else
					companyUser.setState(1);
				companyUserService.merge(companyUser);
				json.put("obj", company);
				json.put("success", true);
				writeJSON(response, json);
			} else {
				json.put("success", false);
				writeJSON(response, json);
			}
		}
} 


 
