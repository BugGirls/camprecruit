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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Association;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.AssociationService;
import com.jeefw.service.sys.TzzDictionaryService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 协会链接管理的控制层 
 */
@Controller
@RequestMapping("/sys/association")
public class AssociationController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	AssociationService associationService;
	
	@Resource
	TzzDictionaryService dictService;
	
		
	@RequestMapping(value = "/getAllianceSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		Map<Integer,String> map = associationService.getAssociationMap();
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

	@RequestMapping(value = "/getAssociationList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getAssociationList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Association association = new Association();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					association.set$like_name(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				association.setFlag("OR");
			} else {
				association.setFlag("AND");
			}
		}
		association.setFirstResult((firstResult - 1) * maxResults);
		association.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		association.setSortedConditions(sortedCondition);
		QueryResult<Association> queryResult = associationService.doPaginationQuery(association);
		List <Association> queryList = queryResult.getResultList();
		JqGridPageView<Association> authorityListView = new JqGridPageView<Association>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteAssociation")
		public void deleteAssociation(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = associationService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveAssociation", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(Association association, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) association);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				associationService.merge(association);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				associationService.persist(association);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateAssociation", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateAssociation(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteAssociation(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String associationName = request.getParameter("name");
			Association association = null;
			if (oper.equals("edit")) {
				association = associationService.get(Integer.valueOf(id));
			}			
			Association sameNameAssociation = associationService.getByProerties("name", associationName);
			
			if (StringUtils.isBlank(associationName) ) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写加盟商名称");
				writeJSON(response, result);
			} else if (null != sameNameAssociation && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "加盟商名称已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != association && (association.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "要编辑的记录有问题，请重新尝试！");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (associationName != null &&!"".equals(associationName)){
						association.setName(associationName);
					}
					association.setCmd("edit");
					doSave(association, request, response);
				} else if (oper.equals("add")) {
					Association association1 = new Association();					
					association1.setName(associationName);
					association1.setCmd("new");
					doSave(association1, request, response);
				}
			}
		}
	}
	
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 *  上传图片/sys/Association/fileUpload
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		String name=request.getParameter("name"); 
		String href=request.getParameter("href"); 
		Integer isdelete= request.getParameter("isdelete")!=null?1:0;
		Date publishtime =  new Date();
		
		Association association = new Association(name,isdelete,publishtime,image,href);
		
		if(id!=null){
			Association entity=associationService.get(Integer.parseInt(id)); 
			association.setId(Integer.parseInt(id));
			association.setCreater(getCurrentSysUser().getUserName());
			associationService.merge(association);
		}else { 
			association.setCreatetime(new Date());
			association.setCreater(getCurrentSysUser().getUserName());
			associationService.persist(association);
		}  
		String htmlString="<script language=javascript> history.go(-1);</script>";
		writeJSON(response, htmlString);
	}
	
//	@RequestMapping(value = "/getAssociation", method = { RequestMethod.POST, RequestMethod.GET })
//	public void getAssociation(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject json = new JSONObject();
//		json.put("success", false);
//		Integer id = getCurrentAllianceId();
//		
//		Association business = associationService.get(id);
//		
//		if(null!=business){
//			json.put("success", true);
//			json.put("business", business);
//		}
//		writeJSON(response, json); 
//	}
	
}
