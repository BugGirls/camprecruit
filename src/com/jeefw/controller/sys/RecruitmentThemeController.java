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
import com.jeefw.model.sys.RecruitmentTheme;
import com.jeefw.service.sys.RecruitmentThemeService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 招聘主题的控制层 
 */
@Controller
@RequestMapping("/sys/recruitmenttheme")
public class RecruitmentThemeController extends JavaEEFrameworkBaseController<RecruitmentTheme> implements Constant {

	@Resource
	private RecruitmentThemeService recruitmentThemeService;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value = "/getSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		List<RecruitmentTheme> tzzdicList = recruitmentThemeService.queryByProerties("state", "1");
		StringBuilder builder = new StringBuilder();
//			builder.append("<select>");
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getTitle()+  "</option>");
			}
//			builder.append("</select>");
//			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("d", builder.toString());
		writeJSON(response, builder.toString() ); 
	}
	
	@RequestMapping(value = "/getTypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTypeSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		List<RecruitmentTheme> tzzdicList = recruitmentThemeService.doQueryAll();
		StringBuilder builder = new StringBuilder();
			builder.append("<select>");
			for (int i = 0; i < tzzdicList.size(); i++) {
				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getTitle()+  "</option>");
			}
			builder.append("</select>");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("d", builder.toString());
		writeJSON(response, jsonObject); 
	}
	
	// 查询招聘主题的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getRecruitmentTheme", method = { RequestMethod.POST, RequestMethod.GET })
	public void getRecruitmentTheme(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		RecruitmentTheme recruitmentTheme = new RecruitmentTheme();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				recruitmentTheme.setFlag("OR");
			} else {
				recruitmentTheme.setFlag("AND");
			}
		}
		recruitmentTheme.setFirstResult((firstResult - 1) * maxResults);
		recruitmentTheme.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		recruitmentTheme.setSortedConditions(sortedCondition);
		recruitmentTheme.setState("1");
		QueryResult<RecruitmentTheme> queryResult = recruitmentThemeService.doPaginationQuery(recruitmentTheme);
		JqGridPageView<RecruitmentTheme>  tzzPromotionTypeListView = new JqGridPageView<RecruitmentTheme>();
		tzzPromotionTypeListView.setMaxResults(maxResults);
		List<RecruitmentTheme> tzzPromotionTypeList = queryResult.getResultList();
		tzzPromotionTypeListView.setRows(tzzPromotionTypeList);
		tzzPromotionTypeListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzPromotionTypeListView);
	}

	// 保存活动类型的实体Bean
	@RequestMapping(value = "/saveTzzIndexCarouse", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(RecruitmentTheme entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			RecruitmentTheme recruitmentTheme =recruitmentThemeService.get(entity.getId());
			entity.setCreatetime(recruitmentTheme.getCreatetime());
			recruitmentThemeService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			recruitmentThemeService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作活动类型的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateRecruitmentTheme", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateRecruitmentTheme(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteTzzPromotionType(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String title = request.getParameter("title");
			String sort = request.getParameter("sort");
			RecruitmentTheme entity = new RecruitmentTheme();
			 
			String state = request.getParameter("statecn");
			
			 
			if (StringUtils.isBlank(image)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请选择图片");
				writeJSON(response, result);
			}else {
				
				if(state.equals("显示"))
					entity.setState("1");
				else  entity.setState("0");
				entity.setImage(image); 
				entity.setTitle(title);
				entity.setSort(Integer.parseInt(sort));
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

	// 删除活动类型
	@RequestMapping("/deleteTzzPromotionType")
	public void deleteTzzPromotionType(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = recruitmentThemeService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	// 上传图片/sys/shuffing/fileUpload
		@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
		public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
			RequestContext requestContext = new RequestContext(request);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String id= request.getParameter("id");  
			String title=request.getParameter("title");
			String sort=request.getParameter("sort");
			String state=request.getParameter("state"); 
			JSONObject json = new JSONObject();
			String image ="";
			if (!file.isEmpty()) {
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
			
			RecruitmentTheme recruitmentTheme=new RecruitmentTheme();
			
			recruitmentTheme.setTitle(title);
			recruitmentTheme.setSort(Integer.parseInt(sort));
			recruitmentTheme.setState(state); 
			if(image.equals("")){
				recruitmentTheme.setImage(recruitmentThemeService.get(Integer.parseInt(id)).getImage());
			}else {
				recruitmentTheme.setImage(image);
			}  
			
			if(id!=null){ 
				RecruitmentTheme entity=recruitmentThemeService.get(Integer.parseInt(id));
				recruitmentTheme.setId(Integer.parseInt(id));
				recruitmentTheme.setCreatetime(entity.getCreatetime());
				recruitmentThemeService.merge(recruitmentTheme);
			}else {
				recruitmentTheme.setCreatetime(new Date());
				recruitmentThemeService.persist(recruitmentTheme);
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
} 


 
