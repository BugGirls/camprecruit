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
import com.jeefw.model.sys.Position;
import com.jeefw.service.sys.PositionService;

import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 岗位的控制层 
 */
@Controller
@RequestMapping("/sys/career")
public class CareerController extends JavaEEFrameworkBaseController<Position> implements Constant {

	@Resource
	private PositionService careerService;
	
	// 查询招聘主题包含的岗位的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getCareerList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getCareerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		String rid = request.getParameter("rid");
		Position recruitment = new Position();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				recruitment.setFlag("OR");
			} else {
				recruitment.setFlag("AND");
			}
		}
		recruitment.setFirstResult((firstResult - 1) * maxResults);
		recruitment.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		recruitment.setSortedConditions(sortedCondition);
		QueryResult<Position> queryResult = careerService.doPaginationQuery(recruitment);
		JqGridPageView<Position>  tzzPromotionTypeListView = new JqGridPageView<Position>();
		tzzPromotionTypeListView.setMaxResults(maxResults);
		List<Position> tzzPromotionTypeList = queryResult.getResultList();
		tzzPromotionTypeListView.setRows(tzzPromotionTypeList);
		tzzPromotionTypeListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzPromotionTypeListView);
	}
	
	// 获取单条记录
	@RequestMapping("/getCareer")
	public void getCareer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") Integer id) throws IOException {
		Position recruitment = careerService.get(id);
		JSONObject json = new JSONObject();
		if (null != recruitment && 0 != recruitment.getId()) {
			json.put("obj", recruitment);
			json.put("success", true);
			writeJSON(response, json);
		} else {
			json.put("success", false);
			writeJSON(response, json);
		}
	}

	// 操作活动类型的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateCareer", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateCareer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteCareer(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			Position entity = new Position();
			 
			
			 
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

	// 删除活动类型
	@RequestMapping("/deleteCareer")
	public void deleteCareer(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = careerService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static SimpleDateFormat sdfb = new SimpleDateFormat("yyyy-MM-dd");
	
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
			Position recruitment=new Position();
			recruitment.setName(name);
			
			if(StringHelper.isNotEmpty(id)){ 
				Position entity=careerService.get(NumberHelper.string2Int(id));
				recruitment.setId(NumberHelper.string2Int(id));
				recruitment.setCreatetime(entity.getCreatetime());
				careerService.merge(recruitment);
			}else {
				recruitment.setCreatetime(new Date());
				careerService.persist(recruitment);
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


 
