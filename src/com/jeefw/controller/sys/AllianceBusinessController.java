package com.jeefw.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.AllianceBusiness;
import com.jeefw.model.sys.Dict;
import com.jeefw.service.sys.AllianceBusinessService;
import com.jeefw.service.sys.DictService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 加盟商管理的控制层 
 */
@Controller
@RequestMapping("/sys/alliancebusiness")
public class AllianceBusinessController extends JavaEEFrameworkBaseController<Dict> implements Constant {
	@Resource
	AllianceBusinessService allianceBusinessService;
	
	@Resource
	DictService dictService;
	
		
	@RequestMapping(value = "/getAllianceSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		Map<Integer,String> map = allianceBusinessService.getAllianceBusinessMap();
		StringBuilder builder = new StringBuilder();
		builder.append("<select>");
		builder.append("<option value=''></option>");
		for (Integer in : map.keySet()) {
			String str = map.get(in);
			builder.append("<option value='" + in + "'>" +str+  "</option>");
		}
		builder.append("</select>");
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("d", builder.toString());
		writeJSON(response, builder.toString() ); 
	}

	@RequestMapping(value = "/getAllianceBusinessList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAllianceBusinessList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		//Integer allianceId = getCurrentAllianceId();
		AllianceBusiness allianceBusiness = new AllianceBusiness();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_name(result.getString("data"));
				}
				if (result.getString("field").equals("typeValue") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_typeValue(result.getString("data"));
				}
				if (result.getString("field").equals("brief") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_brief(result.getString("data"));
				}
				if (result.getString("field").equals("industryValue") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_industryValue(result.getString("data"));
				}
				if (result.getString("field").equals("address") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_address(result.getString("data"));
				}
				if (result.getString("field").equals("contacts") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_contacts(result.getString("data"));
				}
				if (result.getString("field").equals("contacts_phone") && result.getString("op").equals("cn")) {
					allianceBusiness.set$like_contacts_phone(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				allianceBusiness.setFlag("OR");
			} else {
				allianceBusiness.setFlag("AND");
			}
		}
		//allianceBusiness.set$eq_id(allianceId);
		allianceBusiness.setFirstResult((firstResult - 1) * maxResults);
		allianceBusiness.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		allianceBusiness.setSortedConditions(sortedCondition);
		QueryResult<AllianceBusiness> queryResult = allianceBusinessService.doPaginationQuery(allianceBusiness);
		List <AllianceBusiness> queryList = queryResult.getResultList();
		JqGridPageView<AllianceBusiness> authorityListView = new JqGridPageView<AllianceBusiness>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteAllianceBusiness")
		public void deleteAllianceBusiness(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = allianceBusinessService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveAllianceBusiness", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(AllianceBusiness allianceBusiness, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) allianceBusiness);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				allianceBusinessService.merge(allianceBusiness);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				allianceBusinessService.persist(allianceBusiness);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateAllianceBusiness", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateAllianceBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteAllianceBusiness(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String allianceBusinessName = request.getParameter("name");
			String allianceBusinessBrief = request.getParameter("brief");
			String allianceBusinessContent = request.getParameter("content");
			String allianceBusinessImage = request.getParameter("image");			
			String categoryName = request.getParameter("categoryName");
			String allianceBusinessRexiao = "0";
			Dict dict =  dictService.getByProerties("name",categoryName);
			int categoryId = 0; 
			if (dict != null && dict.getId() > 0 ){
				categoryId = dict.getId();
			} 	
			AllianceBusiness allianceBusiness = null;
			if (oper.equals("edit")) {
				allianceBusiness = allianceBusinessService.get(Integer.valueOf(id));
			}			
			AllianceBusiness sameNameAllianceBusiness = allianceBusinessService.getByProerties("name", allianceBusinessName);
			
			if (StringUtils.isBlank(allianceBusinessName) ) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写加盟商名称");
				writeJSON(response, result);
			} else if (null != sameNameAllianceBusiness && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "加盟商名称已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != allianceBusiness && (allianceBusiness.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "要编辑的记录有问题，请重新尝试！");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (allianceBusinessName != null &&!"".equals(allianceBusinessName)){
						allianceBusiness.setName(allianceBusinessName);
					}
					if (allianceBusinessBrief != null &&!"".equals(allianceBusinessBrief)){
						allianceBusiness.setBrief(allianceBusinessBrief);
					}
					if (allianceBusinessContent != null && !"".equals(allianceBusinessContent)){
						allianceBusiness.setContent(allianceBusinessContent);
					}
					allianceBusiness.setCmd("edit");
					doSave(allianceBusiness, request, response);
				} else if (oper.equals("add")) {
					AllianceBusiness allianceBusiness1 = new AllianceBusiness();					
					allianceBusiness1.setName(allianceBusinessName);
					allianceBusiness1.setBrief(allianceBusinessBrief);
					allianceBusiness1.setContent(allianceBusinessContent);
					allianceBusiness1.setCmd("new");
					doSave(allianceBusiness1, request, response);
				}
			}
		}
	}
	
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 *  上传图片/sys/AllianceBusiness/fileUpload
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response,
			AllianceBusiness allianceBusiness) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String id= request.getParameter("id");
		String fimage=request.getParameter("image");
		
		List<MultipartFile> list = new ArrayList<MultipartFile>();
    	list.add(file);
	    
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
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ").replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
					image = destinationFilePath;
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
			if(null!=fimage&&!"".equals(fimage))
				image = fimage;
		}
//		String name=request.getParameter("name"); 
//		String brief=request.getParameter("brief");
//		String industry=request.getParameter("industry");
//		String content=request.getParameter("addContent");
//		String type= request.getParameter("type");
//		String province= request.getParameter("province");
//		String city= request.getParameter("city");
//		String address= request.getParameter("address");
//		String contacts= request.getParameter("contacts");
//		String contacts_phone= request.getParameter("contacts_phone");
//		Integer isdelete= request.getParameter("isdelete")!=null?1:0;
//		
//		AllianceBusiness allianceBusiness = new AllianceBusiness(name,brief,content,type,isdelete,publishtime,image);
		allianceBusiness.setLogo(image);
		//行业Value
		Dict dictHY = dictService.getByProerties("dictKey", allianceBusiness.getIndustry());
		if(null != dictHY){
			allianceBusiness.setIndustryValue(dictHY.getDictValue());
		}
		//类型Value
		Dict dictType = dictService.getByProerties("dictKey", allianceBusiness.getType());
		if(null != dictType){
			allianceBusiness.setTypeValue(dictType.getDictValue());
		}
		if(id!=null){
			//AllianceBusiness entity=allianceBusinessService.get(Integer.parseInt(id)); 
//			allianceBusiness.setId(Integer.parseInt(id));
//			allianceBusiness.setCreater(getCurrentSysUser().getUserName());
			allianceBusinessService.merge(allianceBusiness);
		}else { 
			allianceBusiness.setCreatetime(new Date());
			allianceBusiness.setCreater(getCurrentSysUser().getUserName());
			allianceBusinessService.persist(allianceBusiness);
		}  
		String htmlString="<script language=javascript> history.go(-1);</script>";
		writeJSON(response, htmlString);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllianceBusiness", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAllianceBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("success", false);
		Integer allianceid = getCurrentAllianceId();
		
		AllianceBusiness business = allianceBusinessService.get(allianceid);
		
		if(null!=business){
			json.put("success", true);
			json.put("business", business);
		}
		writeJSON(response, json); 
	}
	
	@RequestMapping(value = "/getAllianceBusinessById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public AllianceBusiness getAllianceBusinessById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer allianceid = Integer.parseInt(request.getParameter("id"));
		AllianceBusiness business = allianceBusinessService.get(allianceid);
		return business;
	}
	
}
