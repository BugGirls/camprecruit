package com.jeefw.controller.sys;
 
import java.io.File; 
import java.io.IOException;
import java.io.OutputStream; 
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.jeefw.model.sys.TzzIndexMenu;
import com.jeefw.service.sys.TzzIndexMenuService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult; 
import core.util.JavaEEFrameworkUtils;

/**
 * 主页功能列表的控制层 
 */
@Controller
@RequestMapping("/sys/indexmenu")
public class TzzIndexMenuController extends JavaEEFrameworkBaseController<TzzIndexMenu> implements Constant {

	@Resource
	private TzzIndexMenuService tzzIndexMenuService;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// 查询主页功能列表的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getTzzIndexMenu", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTzzIndexMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		TzzIndexMenu tzzIndexMenu = new TzzIndexMenu();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				tzzIndexMenu.setFlag("OR");
			} else {
				tzzIndexMenu.setFlag("AND");
			}
		}
		tzzIndexMenu.setFirstResult((firstResult - 1) * maxResults);
		tzzIndexMenu.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		tzzIndexMenu.setSortedConditions(sortedCondition);
		QueryResult<TzzIndexMenu> queryResult = tzzIndexMenuService.doPaginationQuery(tzzIndexMenu);
		JqGridPageView<TzzIndexMenu>  tzzIndexMenuListView = new JqGridPageView<TzzIndexMenu>();
		tzzIndexMenuListView.setMaxResults(maxResults);
		tzzIndexMenuListView.setRows(queryResult.getResultList());
		tzzIndexMenuListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzIndexMenuListView);
	}

	// 保存活动轮播的实体Bean
	@RequestMapping(value = "/saveTzzIndexCarouse", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(TzzIndexMenu entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			TzzIndexMenu tzzIndexMenu =tzzIndexMenuService.get(entity.getId());
			entity.setCreateTime(tzzIndexMenu.getCreateTime());
			tzzIndexMenuService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			tzzIndexMenuService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作活动轮播的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateTzzIndexMenu", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateTzzIndexMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteTzzIndexMenu(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			String href = request.getParameter("href");
			String sort = request.getParameter("sort");
			TzzIndexMenu entity = new TzzIndexMenu();
			 
			String state = request.getParameter("statecn");
			
			 
			if (StringUtils.isBlank(image)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请选择图片");
				writeJSON(response, result);
			}else {
				
				if(state.equals("显示"))
					entity.setState("1");
				else  entity.setState("0");
				entity.setIcon(image); 
				entity.setTitle(title);
				entity.setHref(href);
				entity.setSort(Integer.parseInt(sort));
				if (oper.equals("edit")) {
					entity.setId(Integer.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCreateTime(new Date() );
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除活动轮播
	@RequestMapping("/deleteTzzIndexMenu")
	public void deleteTzzIndexMenu(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = tzzIndexMenuService.deleteByPK(ids);
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
			String href=request.getParameter("href");
			String sort=request.getParameter("sort");
			String state=request.getParameter("state"); 
			String roletype=request.getParameter("roletype"); 
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
			
			TzzIndexMenu tzzIndexMenu=new TzzIndexMenu();
			
			tzzIndexMenu.setTitle(title);
			tzzIndexMenu.setHref(href);
			tzzIndexMenu.setSort(Integer.parseInt(sort));
			tzzIndexMenu.setState(state); 
			tzzIndexMenu.setRoletype(roletype);
			if(image.equals("")){
				tzzIndexMenu.setIcon(tzzIndexMenuService.get(Integer.parseInt(id)).getIcon());
			}else {
				tzzIndexMenu.setIcon(image);
			}  
			
			if(id!=null){ 
				TzzIndexMenu entity=tzzIndexMenuService.get(Integer.parseInt(id));
				tzzIndexMenu.setId(Integer.parseInt(id));
				tzzIndexMenu.setCreateTime(entity.getCreateTime());
				tzzIndexMenuService.merge(tzzIndexMenu);
			}else {
				tzzIndexMenu.setCreateTime(new Date());
				tzzIndexMenuService.persist(tzzIndexMenu);
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


 
