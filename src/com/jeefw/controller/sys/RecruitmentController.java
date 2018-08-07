package com.jeefw.controller.sys;
 
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.jeefw.model.sys.Recruitment;
import com.jeefw.service.sys.RecruitmentService;

import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.JavaEEFrameworkUtils;
import core.util.NumberHelper;
import core.util.StringHelper;
import net.sf.json.JSONObject;

/**
 * 主题招聘的控制层 
 */
@Controller
@RequestMapping("/sys/recruitment")
public class RecruitmentController extends JavaEEFrameworkBaseController<Recruitment> implements Constant {

	@Resource
	private RecruitmentService recruitmentService;
	
	// 查询招聘主题的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getRecruitmentList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getRecruitmentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Recruitment recruitment = new Recruitment();
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
		QueryResult<Recruitment> queryResult = recruitmentService.doPaginationQuery(recruitment);
		JqGridPageView<Recruitment>  tzzPromotionTypeListView = new JqGridPageView<Recruitment>();
		tzzPromotionTypeListView.setMaxResults(maxResults);
		List<Recruitment> tzzPromotionTypeList = queryResult.getResultList();
		tzzPromotionTypeListView.setRows(tzzPromotionTypeList);
		tzzPromotionTypeListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, tzzPromotionTypeListView);
	}
	
	// 获取单条记录
	@RequestMapping("/getRecruitment")
	public void getRecruitment(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") Integer id) throws IOException {
		Recruitment recruitment = recruitmentService.get(id);
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
	@RequestMapping(value = "/operateRecruitment", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateRecruitment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteRecruitment(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
			Recruitment entity = new Recruitment();
			 
			
			 
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
	@RequestMapping("/deleteRecruitment")
	public void deleteRecruitment(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = recruitmentService.deleteByPK(ids);
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
			Integer themeid = NumberHelper.string2Int(StringHelper.isEmpty(request.getParameter("themeid"))?"0":request.getParameter("themeid"));
			String themename =StringHelper.null2String( request.getParameter("themename"));
			String brief = StringHelper.null2String(request.getParameter("brief"));
			String content = StringHelper.null2String(request.getParameter("addContent"));
			if(StringHelper.isNotEmpty(content)){
				content = removeFourChar(content).replaceAll("0000", "");
			}
			Integer free_reservation = request.getParameter("free_reservation") != null ? 1 : 0;
			Integer pay_need = request.getParameter("pay_need") != null ? 1 : 0;
			BigDecimal price = BigDecimal.valueOf(NumberHelper.string2Float(StringHelper.null2String(request.getParameter("price")),0));
			Integer participation = NumberHelper.string2Int(
					"".equals(request.getParameter("participation")) ? "0" : request.getParameter("participation"),0);
			String sbegin_date = StringHelper.null2String(request.getParameter("begin_date"));
			String send_date = StringHelper.null2String(request.getParameter("end_date"));
			Date begin_date = null;
			Date end_date = null;
			if(StringHelper.isNotEmpty(sbegin_date)){
				if(sbegin_date.contains("/")){
					begin_date = sdfb.parse(sbegin_date.replace("/", "-"));
				}else{
					begin_date = sdfb.parse(sbegin_date);
				}
			}
			if(StringHelper.isNotEmpty(send_date)){
				if(send_date.contains("/")){
					end_date = sdfb.parse(send_date.replace("/", "-"));
				}else{
					end_date = sdfb.parse(send_date);
				}
			}
			int everyday_limit = NumberHelper.string2Int(StringHelper.isEmpty(request.getParameter("everyday_limit"))?"0":request.getParameter("everyday_limit"));
			int is_weekend = NumberHelper.string2Int(StringHelper.isEmpty(request.getParameter("is_weekend"))?"0":request.getParameter("is_weekend"));
			String contact_address = StringHelper.null2String(request.getParameter("contact_address"));
			String coordinate = StringHelper.null2String(request.getParameter("coordinate"));
			int is_publish = NumberHelper.string2Int(StringHelper.isEmpty(request.getParameter("is_publish"))?"0":request.getParameter("is_publish"));
			
			Recruitment recruitment=new Recruitment();
			recruitment.setName(name);
			recruitment.setBrief(brief);
			recruitment.setThemeid(themeid);
			recruitment.setThemename(themename);
			recruitment.setBegin_date(begin_date);
			recruitment.setEnd_date(end_date);
			recruitment.setContent(content);
			recruitment.setFree_reservation(free_reservation);
			recruitment.setPay_need(pay_need);
			recruitment.setPrice(price.floatValue());
			recruitment.setParticipation(participation);
			recruitment.setEveryday_limit(everyday_limit);
			recruitment.setIs_publish(is_publish);
			recruitment.setIs_weekend(is_weekend);
			recruitment.setContact_address(contact_address);
			recruitment.setCoordinate(coordinate);
			recruitment.setStatus(1);
			
			if(StringHelper.isNotEmpty(id)){ 
				Recruitment entity=recruitmentService.get(NumberHelper.string2Int(id));
				recruitment.setId(NumberHelper.string2Int(id));
				recruitment.setCreater(entity.getCreater());
				recruitment.setCreatetime(entity.getCreatetime());
				if(StringHelper.isEmpty(image)){
					recruitment.setImg(entity.getImg());
				}else {
					recruitment.setImg(image);
				}  
				recruitmentService.merge(recruitment);
			}else {
				recruitment.setImg(image);
				recruitment.setCreater(getCurrentSysUser().getUserName());
				recruitment.setCreatetime(new Date());
				recruitmentService.persist(recruitment);
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
		
		
		/**
	     * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案 😁
	     * @author 
	     * @data 
	     * @param content
	     * @return
		 * @throws UnsupportedEncodingException 
	     */
	    public static String removeFourChar(String content) throws UnsupportedEncodingException {
	        byte[] conbyte = content.getBytes("UTF-8");
	        for (int i = 0; i < conbyte.length; i++) {
	            if ((conbyte[i] & 0xF8) == 0xF0) {
	                for (int j = 0; j < 4; j++) {                          
	                    conbyte[i+j]=0x30;                     
	                }  
	                i += 3;
	            }
	        }
	        content = new String(conbyte,"UTF-8");
	        return content.replaceAll("0000", "");
	    }
} 


 
