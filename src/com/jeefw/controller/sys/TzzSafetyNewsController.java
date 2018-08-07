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
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzSafetyNews;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzSafetyNewsService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 角色的控制层 
 */
@Controller
@RequestMapping("/sys/TzzSafetyNews")
public class TzzSafetyNewsController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	TzzSafetyNewsService safetyNewsService;
	
	@Resource
	TzzDictionaryService dictService;
	
		
		@RequestMapping(value = "/getDictSelectList", method = { RequestMethod.POST, RequestMethod.GET })
		public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
			String [] keys = new String [2];
			keys[0] = "level";
			keys[1] = "type";
			Object [] values = new Object[2];
			values[0] = "3";
			values[1] = "1";
				
			List<TzzDictionary> tzzdicList = dictService.queryByProerties(keys, values);
			StringBuilder builder = new StringBuilder();
 			builder.append("<select>");
 			for (int i = 0; i < tzzdicList.size(); i++) {
 				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
 			}
 			builder.append("</select>");
 			JSONObject jsonObject=new JSONObject();
 			jsonObject.put("d", builder.toString());
			writeJSON(response, jsonObject ); 
		}

	@RequestMapping(value = "/getSafetyNewsList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getSafetyNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzSafetyNews SafetyNews = new TzzSafetyNews();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
					SafetyNews.set$like_name(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				SafetyNews.setFlag("OR");
			} else {
				SafetyNews.setFlag("AND");
			}
		}
		SafetyNews.setFirstResult((firstResult - 1) * maxResults);
		SafetyNews.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		SafetyNews.setSortedConditions(sortedCondition);
		QueryResult<TzzSafetyNews> queryResult = safetyNewsService.doPaginationQuery(SafetyNews);
		List <TzzSafetyNews> queryList = queryResult.getResultList();
		
		String [] keys = new String [2];
		keys[0] = "level";
		keys[1] = "type";
		Object [] values = new Object[2];
		values[0] = "2";
		values[1] = "0"; 
		List<TzzDictionary> tzzdicList = dictService.queryByProerties(keys, values);
		
		List<TzzDictionary> dict = tzzdicList ;
		queryList = safetyNewsService.queryTzzSafetyNewsCnList(queryList,dict); 
		JqGridPageView<TzzSafetyNews> authorityListView = new JqGridPageView<TzzSafetyNews>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteSafetyNews")
		public void deleteSafetyNews(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = safetyNewsService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveSafetyNews", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(TzzSafetyNews tzzSafetyNews, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) tzzSafetyNews);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				safetyNewsService.merge(tzzSafetyNews);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				safetyNewsService.persist(tzzSafetyNews);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateSafetyNews", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateSafetyNews(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteSafetyNews(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String safetyNewsName = request.getParameter("name");
			String safetyNewsBrief = request.getParameter("brief");
			String safetyNewsContent = request.getParameter("content");
			String safetyNewsImage = request.getParameter("image");			
			String categoryName = request.getParameter("categoryName");
			String safetyNewsRexiao = "0";
			TzzDictionary tzzDict =  dictService.getByProerties("name",categoryName);
			int categoryId = 0 ; 
			if (tzzDict != null && tzzDict.getId() > 0 ){
				categoryId = tzzDict.getId();
			} 	
			TzzSafetyNews tzzSafetyNews = null;
			if (oper.equals("edit")) {
				tzzSafetyNews = safetyNewsService.get(Integer.valueOf(id));
			}			
			TzzSafetyNews sameNameSafetyNews = safetyNewsService.getByProerties("name", safetyNewsName);
			
			if (StringUtils.isBlank(safetyNewsName) ) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写新闻的标题信息");
				writeJSON(response, result);
			} else if (null != sameNameSafetyNews && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "新闻的标题已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != tzzSafetyNews && (tzzSafetyNews.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "要编辑的记录有问题，请重新尝试！");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (safetyNewsName != null &&!"".equals(safetyNewsName)){
						tzzSafetyNews.setName(safetyNewsName);
					}
					if (safetyNewsBrief != null &&!"".equals(safetyNewsBrief)){
						tzzSafetyNews.setBrief(safetyNewsBrief);
					}
					if (safetyNewsContent != null && !"".equals(safetyNewsContent)){
						tzzSafetyNews.setContent(safetyNewsContent);
					}
					tzzSafetyNews.setCmd("edit");
					doSave(tzzSafetyNews, request, response);
				} else if (oper.equals("add")) {
					TzzSafetyNews tzzSafetyNews1 = new TzzSafetyNews();					
					tzzSafetyNews1.setName(safetyNewsName);
					tzzSafetyNews1.setBrief(safetyNewsBrief);
					tzzSafetyNews1.setContent(safetyNewsContent);
					tzzSafetyNews1.setCmd("new");
					doSave(tzzSafetyNews1, request, response);
				}
			}
		}
	}
	
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 *  上传图片/sys/TzzSafetyNews/fileUpload
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
			Integer origin= Integer.parseInt(request.getParameter("origin"));
			Integer istop= request.getParameter("istop")!=null?1:0;
			Integer ispublish= request.getParameter("ispublish")!=null?1:0;
			Date publishtime = null;
			if(ispublish==1)
				publishtime = new Date();
			
			TzzSafetyNews tzzSafetyNews = new TzzSafetyNews(name,brief,content,type,origin,istop,ispublish,publishtime,image);
			
			if(id!=null){   
				TzzSafetyNews entity=safetyNewsService.get(Integer.parseInt(id)); 
				tzzSafetyNews.setId(Integer.parseInt(id));
				tzzSafetyNews.setCreater(getCurrentSysUser().getUserName());
				safetyNewsService.merge(tzzSafetyNews);
			}else { 
				tzzSafetyNews.setCreattime(new Date());
				tzzSafetyNews.setCreater(getCurrentSysUser().getUserName());
				safetyNewsService.persist(tzzSafetyNews);
			}  
 			String htmlString="<script language=javascript> history.go(-1);</script>";
 			writeJSON(response, htmlString);
		}
	
	
	
}
