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
import com.jeefw.model.sys.CooperationColleges;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.CooperationCollegesService;
import com.jeefw.service.sys.TzzDictionaryService;

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
@RequestMapping("/sys/cooperationcolleges")
public class CooperationCollegesController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	CooperationCollegesService cooperationCollegesService;
	
	@Resource
	TzzDictionaryService dictService;
	
		
	@RequestMapping(value = "/getAllianceSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		Map<Integer,String> map = cooperationCollegesService.getCooperationCollegesMap();
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

	@RequestMapping(value = "/getCooperationCollegesList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCooperationCollegesList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		CooperationColleges cooperationColleges = new CooperationColleges();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					cooperationColleges.set$like_name(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				cooperationColleges.setFlag("OR");
			} else {
				cooperationColleges.setFlag("AND");
			}
		}
		cooperationColleges.setFirstResult((firstResult - 1) * maxResults);
		cooperationColleges.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		cooperationColleges.setSortedConditions(sortedCondition);
		QueryResult<CooperationColleges> queryResult = cooperationCollegesService.doPaginationQuery(cooperationColleges);
		List <CooperationColleges> queryList = queryResult.getResultList();
		JqGridPageView<CooperationColleges> authorityListView = new JqGridPageView<CooperationColleges>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteCooperationColleges")
		public void deleteCooperationColleges(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = cooperationCollegesService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveCooperationColleges", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(CooperationColleges cooperationColleges, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) cooperationColleges);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				cooperationCollegesService.merge(cooperationColleges);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				cooperationCollegesService.persist(cooperationColleges);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateCooperationColleges", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateCooperationColleges(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteCooperationColleges(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String cooperationCollegesName = request.getParameter("name");
			String cooperationCollegesBrief = request.getParameter("brief");
			String cooperationCollegesContent = request.getParameter("content");
			String cooperationCollegesImage = request.getParameter("image");			
			String categoryName = request.getParameter("categoryName");
			String cooperationCollegesRexiao = "0";
			TzzDictionary tzzDict =  dictService.getByProerties("name",categoryName);
			int categoryId = 0 ; 
			if (tzzDict != null && tzzDict.getId() > 0 ){
				categoryId = tzzDict.getId();
			} 	
			CooperationColleges cooperationColleges = null;
			if (oper.equals("edit")) {
				cooperationColleges = cooperationCollegesService.get(Integer.valueOf(id));
			}			
			CooperationColleges sameNameCooperationColleges = cooperationCollegesService.getByProerties("name", cooperationCollegesName);
			
			if (StringUtils.isBlank(cooperationCollegesName) ) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写加盟商名称");
				writeJSON(response, result);
			} else if (null != sameNameCooperationColleges && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "加盟商名称已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != cooperationColleges && (cooperationColleges.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "要编辑的记录有问题，请重新尝试！");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (cooperationCollegesName != null &&!"".equals(cooperationCollegesName)){
						cooperationColleges.setName(cooperationCollegesName);
					}
					if (cooperationCollegesBrief != null &&!"".equals(cooperationCollegesBrief)){
						cooperationColleges.setBrief(cooperationCollegesBrief);
					}
					if (cooperationCollegesContent != null && !"".equals(cooperationCollegesContent)){
						cooperationColleges.setContent(cooperationCollegesContent);
					}
					cooperationColleges.setCmd("edit");
					doSave(cooperationColleges, request, response);
				} else if (oper.equals("add")) {
					CooperationColleges cooperationColleges1 = new CooperationColleges();					
					cooperationColleges1.setName(cooperationCollegesName);
					cooperationColleges1.setBrief(cooperationCollegesBrief);
					cooperationColleges1.setContent(cooperationCollegesContent);
					cooperationColleges1.setCmd("new");
					doSave(cooperationColleges1, request, response);
				}
			}
		}
	}
	
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 *  上传图片/sys/CooperationColleges/fileUpload
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
		String brief=request.getParameter("brief");
		String content=request.getParameter("addContent");
		Integer type= Integer.parseInt(request.getParameter("type")) ;
		Integer xingzhi= Integer.parseInt(request.getParameter("xingzhi")) ;
		Integer isdelete= request.getParameter("isdelete")!=null?1:0;
		Integer isniubi= request.getParameter("isniubi")!=null?1:0;
		Integer ishenniubi= request.getParameter("ishenniubi")!=null?1:0;
		Date publishtime =  new Date();
		
		CooperationColleges cooperationColleges = new CooperationColleges(name,brief,content,type,isdelete,publishtime,image,xingzhi,isniubi,ishenniubi);
		
		if(id!=null){
			CooperationColleges entity=cooperationCollegesService.get(Integer.parseInt(id)); 
			cooperationColleges.setId(Integer.parseInt(id));
			cooperationColleges.setCreater(getCurrentSysUser().getUserName());
			cooperationCollegesService.merge(cooperationColleges);
		}else { 
			cooperationColleges.setCreatetime(new Date());
			cooperationColleges.setCreater(getCurrentSysUser().getUserName());
			cooperationCollegesService.persist(cooperationColleges);
		}  
		String htmlString="<script language=javascript> history.go(-1);</script>";
		writeJSON(response, htmlString);
	}
	
//	@RequestMapping(value = "/getCooperationColleges", method = { RequestMethod.POST, RequestMethod.GET })
//	public void getCooperationColleges(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject json = new JSONObject();
//		json.put("success", false);
//		Integer allianceid = getCurrentAllianceId();
//		
//		CooperationColleges business = cooperationCollegesService.get(allianceid);
//		
//		if(null!=business){
//			json.put("success", true);
//			json.put("business", business);
//		}
//		writeJSON(response, json); 
//	}
	
}
