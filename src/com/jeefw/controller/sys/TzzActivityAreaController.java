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
import com.jeefw.model.sys.TzzActivityArea;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.TzzActivityAreaService;
import com.jeefw.service.sys.TzzDictionaryService;

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
@RequestMapping("/sys/TzzActivityArea")
public class TzzActivityAreaController extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
	@Resource
	TzzActivityAreaService activityAreaService;
	
	@Resource
	TzzDictionaryService dictService;
	
	@RequestMapping(value = "/getActivityAreaList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getActivityAreaList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzActivityArea ActivityArea = new TzzActivityArea();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				ActivityArea.setFlag("OR");
			} else {
				ActivityArea.setFlag("AND");
			}
		}
		ActivityArea.setFirstResult((firstResult - 1) * maxResults);
		ActivityArea.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		ActivityArea.setSortedConditions(sortedCondition);
		QueryResult<TzzActivityArea> queryResult = activityAreaService.doPaginationQuery(ActivityArea);
		List <TzzActivityArea> queryList = queryResult.getResultList();
		
		JqGridPageView<TzzActivityArea> authorityListView = new JqGridPageView<TzzActivityArea>();
		authorityListView.setMaxResults(maxResults);
		authorityListView.setRows(queryList);
		authorityListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, authorityListView);
	}
	
	// 删除菜单
		@RequestMapping("/deleteActivityArea")
		public void deleteActivityArea(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
			boolean flag = activityAreaService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
		
		@RequestMapping(value = "/saveActivityArea", method = { RequestMethod.POST, RequestMethod.GET })
		public void doSave(TzzActivityArea tzzActivityArea, HttpServletRequest request, HttpServletResponse response) throws IOException {
			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) tzzActivityArea);
			if (CMD_EDIT.equals(parameter.getCmd())) {
				activityAreaService.merge(tzzActivityArea);
			} else if (CMD_NEW.equals(parameter.getCmd())) {
				activityAreaService.persist(tzzActivityArea);
			}
			parameter.setSuccess(true);
			writeJSON(response, parameter);
		}
	
	@RequestMapping(value = "/operateActivityArea", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateActivityArea(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteActivityArea(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String activityAreaName = request.getParameter("name");
			String activityAreaBrief = request.getParameter("brief");
			String activityAreaContent = request.getParameter("content");
			String activityAreaImage = request.getParameter("image");			
			int categoryId = 0 ; 
			TzzActivityArea tzzActivityArea = null;
			if (oper.equals("edit")) {
				tzzActivityArea = activityAreaService.get(Integer.valueOf(id));
			}			
			TzzActivityArea sameNameActivityArea = activityAreaService.getByProerties("name", activityAreaName);
			
			if (null != sameNameActivityArea && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "产品名字已经存在，请重新输入");
				writeJSON(response, result);
			} else if (null != tzzActivityArea && (tzzActivityArea.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "要编辑的记录有问题，请重新尝试！");
				writeJSON(response, result);
			} else {
				if (oper.equals("edit")) {
					if (activityAreaName != null &&!"".equals(activityAreaName)){
						tzzActivityArea.setName(activityAreaName);
					}
					if (activityAreaBrief != null &&!"".equals(activityAreaBrief)){
						tzzActivityArea.setBrief(activityAreaBrief);
					}
					if (activityAreaContent != null && !"".equals(activityAreaContent)){
						tzzActivityArea.setContent(activityAreaContent);
					}
					tzzActivityArea.setCmd("edit");
					doSave(tzzActivityArea, request, response);
				} else if (oper.equals("add")) {
					TzzActivityArea tzzActivityArea1 = new TzzActivityArea();					
					tzzActivityArea1.setName(activityAreaName);
					tzzActivityArea1.setBrief(activityAreaBrief);
					tzzActivityArea1.setContent(activityAreaContent);
					tzzActivityArea1.setCmd("new");
					doSave(tzzActivityArea1, request, response);
				}
			}
		}
	}
	
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 *  上传图片/sys/TzzActivityArea/fileUpload
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
			Integer ispublish= request.getParameter("ispublish")!=null?1:0;
			
			TzzActivityArea tzzActivityArea = new TzzActivityArea(name,brief,content,ispublish,image);
			
			if(id!=null){   
				TzzActivityArea entity=activityAreaService.get(Integer.parseInt(id)); 
				tzzActivityArea.setId(Integer.parseInt(id));
				tzzActivityArea.setCreater(getCurrentSysUser().getUserName());
				activityAreaService.merge(tzzActivityArea);
			}else { 
				tzzActivityArea.setCreattime(new Date());
				tzzActivityArea.setCreater(getCurrentSysUser().getUserName());
				activityAreaService.persist(tzzActivityArea);
			}  
			
 			String htmlString="<script language=javascript> history.go(-1);</script>";
 			writeJSON(response, htmlString);
		}
	
	
	
}
