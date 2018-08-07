package com.jeefw.controller.sys;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.IntoWarehouseRecord;
import com.jeefw.model.sys.IntoWarehouseRecordDatail;
import com.jeefw.service.sys.IntoWarehouseRecordDetailService;
import com.jeefw.service.sys.IntoWarehouseRecordService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.BarcodeUtil;
import core.util.Const;
import core.util.JavaEEFrameworkUtils;
import core.util.PathUtil;
import core.util.TwoDimensionCode;

/**
 * 入库单详情信息控制层 
 */
@Controller
@RequestMapping("/sys/intoWarehouseRecordDatail")
public class IntoWarehouseRecordDetailController extends JavaEEFrameworkBaseController<IntoWarehouseRecord> implements Constant {

	@Resource
	private IntoWarehouseRecordDetailService intoWarehouseRecordDetailService;

	// 查询类型的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getIntoWarehouseRecordDatailByNo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getIntoWarehouseRecordDatailByNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		String intoWarehouseRecordNo = request.getParameter("intoWarehouseRecordNo");
		IntoWarehouseRecordDatail deDatail = new IntoWarehouseRecordDatail();
		deDatail.setIntoWarehouseRecordNo(intoWarehouseRecordNo);
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
//				if (result.getString("field").equals("no") && result.getString("op").equals("cn")) {
//					intoWarehouseRecord.set$like_no(result.getString("data"));
//				}
//				if (result.getString("field").equals("creater") && result.getString("op").equals("cn")) {
//					intoWarehouseRecord.set$like_creater(result.getString("data"));
//				}
//				if (result.getString("field").equals("title") && result.getString("op").equals("cn")) {
//					intoWarehouseRecord.set$like_title(result.getString("data"));
//				}
//				if (result.getString("field").equals("supplier") && result.getString("op").equals("cn")) {
//					intoWarehouseRecord.set$like_supplier(result.getString("data"));
//				}
//				if (result.getString("field").equals("createtime") && result.getString("op").equals("cn")) {
//					intoWarehouseRecord.set$like_createtime(result.getString("data"));
//				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				deDatail.setFlag("OR");
			} else {
				deDatail.setFlag("AND");
			}
		}
		deDatail.setFirstResult((firstResult - 1) * maxResults);
		deDatail.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		deDatail.setSortedConditions(sortedCondition);
		QueryResult<IntoWarehouseRecordDatail> queryResult = intoWarehouseRecordDetailService.doPaginationQuery(deDatail);
		JqGridPageView<IntoWarehouseRecordDatail> dictListView = new JqGridPageView<IntoWarehouseRecordDatail>();
		dictListView.setMaxResults(maxResults);
		List<IntoWarehouseRecordDatail> dictWithSubList = intoWarehouseRecordDetailService.queryIntoWarehouseRecordDatailWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}
//
//	// 保存实体Bean
//	@RequestMapping(value = "/saveProductInfo", method = { RequestMethod.POST, RequestMethod.GET })
//	public void doSave(ProductInfo entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
//		if (CMD_EDIT.equals(parameter.getCmd())) {
//			productInfoService.merge(entity);
//		} else if (CMD_NEW.equals(parameter.getCmd())) {
//			productInfoService.persist(entity);
//		}
//		parameter.setSuccess(true);
//		writeJSON(response, parameter);
//	}
//
//	// 操作类型的删除、导出Excel、字段判断和保存
//	@RequestMapping(value = "/operateProductInfo", method = { RequestMethod.POST, RequestMethod.GET })
//	public void operateProductInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String oper = request.getParameter("oper");
//		String id = request.getParameter("id");
//		if (oper.equals("del")) {
//			String[] ids = id.split(",");
//			deleteProductInfo(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
//		} else if (oper.equals("excel")) {
//			response.setContentType("application/msexcel;charset=UTF-8");
//			try {
//				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
//				OutputStream out = response.getOutputStream();
//				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
//				out.flush();
//				out.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			Map<String, Object> result = new HashMap<String, Object>();
//			String name = request.getParameter("name");
//			String no = request.getParameter("no");
//			String barCode = request.getParameter("barCode");
//			BigDecimal advice_price = new BigDecimal(StringHelper.isEmpty(request.getParameter("advice_price")) ? "0" : request.getParameter("advice_price"));
//			String bigCategoryNo = request.getParameter("bigCategoryNo");
//			String smallCategoryNo = request.getParameter("smallCategoryNo");
//			String bigCategoryName = request.getParameter("bigCategoryName");
//			String smallCategoryName = request.getParameter("smallCategoryName");
//			String productPropertyTempNo = request.getParameter("productPropertyTempNo");
//			String image = request.getParameter("image");
//			String content = request.getParameter("content");
//			String brand = request.getParameter("brand");
//			ProductInfo dict = null;
//			if (oper.equals("edit")) {
//				dict = productInfoService.get(Long.valueOf(id));
//			}
//			ProductInfo productinfo_no = productInfoService.getByProerties("no", no);
//			ProductInfo productinfo_barCode = productInfoService.getByProerties("barCode", barCode);
//			if (StringUtils.isBlank(name)) {
//				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
//				result.put("message", "请填写商品名称");
//				writeJSON(response, result);
//			} else if (StringUtils.isBlank(bigCategoryNo) || StringUtils.isBlank(smallCategoryNo)) {
//				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
//				result.put("message", "请填写商品类型");
//				writeJSON(response, result);
//			} else if (null != productinfo_no && oper.equals("add")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此商品编码已存在，请重新输入");
//				writeJSON(response, result);
//			} else if (null != productinfo_barCode && oper.equals("add")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此商品条形码已存在，请重新输入");
//				writeJSON(response, result);
//			} else if (null != productinfo_no && !dict.getNo().equalsIgnoreCase(no) && oper.equals("edit")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此商品编码已存在，请重新输入");
//				writeJSON(response, result);
//			} else if (null != productinfo_barCode && !dict.getBarCode().equalsIgnoreCase(barCode) && oper.equals("edit")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "此商品条形码已存在，请重新输入");
//				writeJSON(response, result);
//			} else {
//				ProductInfo entity = new ProductInfo();
//				entity.setName(name);
//				entity.setNo(no);
//				entity.setBarCode(barCode);
//				entity.setAdvicePrice(advice_price);
//				entity.setBigCategoryNo(bigCategoryNo);
//				entity.setSmallCategoryNo(smallCategoryNo);
//				entity.setBigCategoryNo(bigCategoryNo);
//				entity.setSmallCategoryName(smallCategoryName);
//				entity.setBigCategoryName(bigCategoryName);
//				entity.setProductPropertyTempNo(productPropertyTempNo);
//				entity.setImage(image);
//				entity.setContent(content);
//				entity.setBrand(brand);
//				if (oper.equals("edit")) {
//					entity.setId(Long.valueOf(id));
//					entity.setCmd("edit");
//					doSave(entity, request, response);
//				} else if (oper.equals("add")) {
//					entity.setCmd("new");
//					doSave(entity, request, response);
//				}
//			}
//		}
//	}
//
//	// 删除
//	@RequestMapping("/deleteProductInfo")
//	public void deleteProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
//		boolean flag = productInfoService.deleteByPK(ids);
//		if (flag) {
//			writeJSON(response, "{success:true}");
//		} else {
//			writeJSON(response, "{success:false}");
//		}
//	}
//
//	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//	/**
//	 *  上传图片
//	 * @param file
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//		@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
//		public void fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, 
//			  @RequestParam(value="file1", required=false) MultipartFile file1, 
//			  @RequestParam(value="file2", required=false) MultipartFile file2, 
//			  @RequestParam(value="file3", required=false) MultipartFile file3, 
//				HttpServletRequest request, HttpServletResponse response,
//				ProductInfo productInfo) throws Exception {
//			RequestContext requestContext = new RequestContext(request);
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			
//			List<MultipartFile> list = new ArrayList<MultipartFile>();
//	    	list.add(file);
//	    	list.add(file1);
//	    	list.add(file2);
//	    	list.add(file3);
//		    
//			JSONObject json = new JSONObject();
//			String image ="";
//			String image1 = "";
//		    String image2 = "";
//		    String image3 = "";
//			for(int i = 0 ; i < list.size() ; i++){
//				file = list.get(i);
//				if (file != null && !file.isEmpty()) {
//					if (file.getSize() > 2097152) {//============最大两兆
//						json.put("message", requestContext.getMessage("g_fileTooLarge"));
//					} else {
//						try {
//							String originalFilename = file.getOriginalFilename();
//							//System.out.println(originalFilename);
//							String fileName = sdfa.format(new Date()) + JavaEEFrameworkUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
//							File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/upload/img/" + sdf.format(new Date())));
//							if (!filePath.exists()) {
//								filePath.mkdirs();
//							}
//							file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
//							String destinationFilePath = "/static/upload/img/" + sdf.format(new Date()) + "/" + fileName;
//							switch(i){
//					          case  0 :
//					        	  image = destinationFilePath;
//					        	  break;
//					          case 1: 
//					        	  image1 = destinationFilePath;
//					        	  break;
//					          case 2:
//					        	  image2 = destinationFilePath;
//					        	  break;
//					          case 3:
//					        	  image3 = destinationFilePath;
//					        	  break;
//					          }
////							image=destinationFilePath;
//							json.put("status", "OK"); 
//							json.put("url", destinationFilePath);
//							json.put("message", requestContext.getMessage("g_uploadSuccess"));
//						} catch (Exception e) {
//							e.printStackTrace();
//							json.put("message", requestContext.getMessage("g_uploadFailure"));
//						}
//					}
//				} else {
//					json.put("message", requestContext.getMessage("g_uploadNotExists"));
//				}
//			}
// 
//; 
//			if((image.equals("") || image ==null) && productInfo.getId() != null){
//				productInfo.setImage(productInfoService.get(productInfo.getId()).getImage());
//			}else {
//				productInfo.setImage(image);
//			}  
//			
//			if( (image1.equals("") || image1 == null) && productInfo.getId() != null){
//				productInfo.setImage1(productInfoService.get(productInfo.getId()).getImage1());
//			}else {
//				productInfo.setImage1(image1);
//			} 
//			
//			if( (image2.equals("") || image2 ==null) && productInfo.getId() != null){
//				productInfo.setImage2(productInfoService.get(productInfo.getId()).getImage2());
//			}else {
//				productInfo.setImage2(image2);
//			} 
//			
//			if((image3.equals("")||image3 ==null) && productInfo.getId() != null){
//				productInfo.setImage3(productInfoService.get(productInfo.getId()).getImage3());
//			}else {
//				productInfo.setImage3(image3);
//			} 
//			
//			if(productInfo.getId()!=null){   
//				productInfoService.merge(productInfo);
//			}else { 
//				productInfoService.persist(productInfo);
//			}  
//			
//	//		String msg=json.getString("message");
//	//		if(msg.equals("文件不存在！")){
//	//			String htmlString="<script language=javascript>alert('<p1 style='color:red;' >文件不存在！请返回！</p1>');history.go(-1);</script>";
//	//			writeJSON(response, htmlString);
//	//		}else{  
// 			String htmlString="<script language=javascript> history.go(-1);</script>";
// 			writeJSON(response, htmlString);
//	//		} 
//		}
//	
//		/**条形码页面
//		 * @param
//		 * @throws Exception
//		 */
//		@RequestMapping(value="/barcode")
//		public void barcode(HttpServletRequest request, HttpServletResponse response, String barcode) throws Exception{
//			String barcodeImgId = barcode+".png"; 									//barcodeImgId此处条形码的图片名
//			String filePath = PathUtil.getClasspath() + Const.FILEPATHTBARCODE + barcodeImgId; 		//存放路径
//			BarcodeUtil.generateFile(barcode, filePath);								//执行生成条形码
////			String path = request.getSession().getServletContext().getRealPath("uploadFiles");
////			writeJSON(response, path + Const.FILEPATHTBARCODE + barcodeImgId);
//		}
//		
//		/**二维码页面
//		 * @param
//		 * @throws Exception
//		 */
//		@RequestMapping(value="/erweima")
//		public void erweima(HttpServletRequest request, HttpServletResponse response, String barcode) throws Exception{
//			String encoderImgId = barcode+".png"; 	 //encoderImgId此处二维码的图片名
//			String filePath = PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + encoderImgId; 		//存放路径
//			TwoDimensionCode.encoderQRCode(barcode, filePath, "png");							//执行生成二维码
//		}
//		
//		
//		/**
//		 * 根据No查询
//		 * @param request
//		 * @param response
//		 * @param no
//		 * @return
//		 */
//		@RequestMapping(value="/findByNo")
//		@ResponseBody
//		public ProductInfo findByNo(HttpServletRequest request, HttpServletResponse response, String no){
//			ProductInfo productinfo = productInfoService.getByProerties("no", no);
//			return productinfo;
//		}
}
