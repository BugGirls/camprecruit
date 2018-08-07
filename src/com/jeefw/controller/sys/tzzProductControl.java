//package com.jeefw.controller.sys;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.RequestContext;
//
//import com.jeefw.core.Constant;
//import com.jeefw.core.JavaEEFrameworkBaseController;
//import com.jeefw.model.sys.Authority;
//import com.jeefw.model.sys.Role;
//import com.jeefw.model.sys.TzzDictionary;
//import com.jeefw.model.sys.TzzIndexCarousel;
//import com.jeefw.model.sys.TzzProduct;
//import com.jeefw.service.sys.RoleService;
//import com.jeefw.service.sys.SysUserService;
//import com.jeefw.service.sys.TzzDictionaryService;
//import com.jeefw.service.sys.TzzProductService;
//
//import core.support.ExtJSBaseParameter;
//import core.support.JqGridPageView;
//import core.support.QueryResult;
//import core.util.JavaEEFrameworkUtils;
//
///**
// * 角色的控制层 
// */
//@Controller
//@RequestMapping("/sys/TzzProduct")
//public class tzzProductControl extends JavaEEFrameworkBaseController<TzzDictionary> implements Constant {
//	@Resource
//	TzzProductService productService;
//	
//	@Resource
//	TzzDictionaryService dictService;
//	
//	
//
//
//	/**
//	 * 获取大分类（营地，社会等）下拉菜单
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//		@RequestMapping(value = "/getviptypeSelectList", method = { RequestMethod.POST, RequestMethod.GET })
//		public void getvipSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
////			List<TzzDictionary> tzzdicList = tzzDictionaryService.queryByProerties("type", 3);
//			StringBuilder builder = new StringBuilder();
////			builder.append("<select>");
////			for (int i = 0; i < tzzdicList.size(); i++) {
////				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +  "</option>");
////			}
////			builder.append("</select>");
//			writeJSON(response, builder.toString());
//		}
//		
//		
//		
//		@RequestMapping(value = "/getDictSelectList", method = { RequestMethod.POST, RequestMethod.GET })
//		public void getDictSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//			
//		 
//			String [] keys = new String [2];
//			keys[0] = "level";
//			keys[1] = "type";
//			Object [] values = new Object[2];
//			values[0] = "3";
//			values[1] = "1";
//				
//			List<TzzDictionary> tzzdicList = dictService.queryByProerties(keys, values);
//			StringBuilder builder = new StringBuilder();
// 			builder.append("<select>");
// 			for (int i = 0; i < tzzdicList.size(); i++) {
// 				builder.append("<option value='" + tzzdicList.get(i).getId() + "'>" +tzzdicList.get(i).getName()+  "</option>");
// 			}
// 			builder.append("</select>");
// 			JSONObject jsonObject=new JSONObject();
// 			jsonObject.put("d", builder.toString());
//			writeJSON(response, jsonObject ); 
//		}
//
//	@RequestMapping(value = "/getProductList", method = { RequestMethod.POST, RequestMethod.GET })
//	public void getProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Integer firstResult = Integer.valueOf(request.getParameter("page"));
//		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
//		String sortedObject = request.getParameter("sidx");
//		String sortedValue = request.getParameter("sord");
//		String filters = request.getParameter("filters");
//		TzzProduct Product = new TzzProduct();
//		if (StringUtils.isNotBlank(filters)) {
//			JSONObject jsonObject = JSONObject.fromObject(filters);
//			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
//			for (int i = 0; i < jsonArray.size(); i++) {
//				JSONObject result = (JSONObject) jsonArray.get(i);
//				if (result.getString("field").equals("name") && result.getString("op").equals("cn")) {
//					Product.set$like_name(result.getString("data"));
//				}
////				if (result.getString("field").equals("menuName") && result.getString("op").equals("cn")) {
////					Product.set$like_menuName(result.getString("data"));
////				}
//			}
//			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
//				Product.setFlag("OR");
//			} else {
//				Product.setFlag("AND");
//			}
//		}
//		Product.setFirstResult((firstResult - 1) * maxResults);
//		Product.setMaxResults(maxResults);
//		Map<String, String> sortedCondition = new HashMap<String, String>();
//		sortedCondition.put(sortedObject, sortedValue);
//		Product.setSortedConditions(sortedCondition);
//		QueryResult<TzzProduct> queryResult = productService.doPaginationQuery(Product);
//		List <TzzProduct> queryList = queryResult.getResultList();
//		
//		String [] keys = new String [2];
//		keys[0] = "level";
//		keys[1] = "type";
//		Object [] values = new Object[2];
//		values[0] = "3";
//		values[1] = "1"; 
//		List<TzzDictionary> tzzdicList = dictService.queryByProerties(keys, values);
//		
//		List<TzzDictionary> dict = tzzdicList ;
//		queryList = productService.queryTzzProductCnList(queryList,dict); 
//		JqGridPageView<TzzProduct> authorityListView = new JqGridPageView<TzzProduct>();
//		authorityListView.setMaxResults(maxResults);
//		authorityListView.setRows(queryList);
//		authorityListView.setRecords(queryResult.getTotalCount());
//		writeJSON(response, authorityListView);
//	}
//	
//	// 删除菜单
//		@RequestMapping("/deleteProduct")
//		public void deleteProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
//			boolean flag = productService.deleteByPK(ids);
//			if (flag) {
//				writeJSON(response, "{success:true}");
//			} else {
//				writeJSON(response, "{success:false}");
//			}
//		}
//		
//		@RequestMapping(value = "/saveProduct", method = { RequestMethod.POST, RequestMethod.GET })
//		public void doSave(TzzProduct tzzProduct, HttpServletRequest request, HttpServletResponse response) throws IOException {
//			ExtJSBaseParameter parameter = ((ExtJSBaseParameter) tzzProduct);
//			if (CMD_EDIT.equals(parameter.getCmd())) {
//				productService.merge(tzzProduct);
//			} else if (CMD_NEW.equals(parameter.getCmd())) {
//				productService.persist(tzzProduct);
//			}
//			parameter.setSuccess(true);
//			writeJSON(response, parameter);
//		}
//	
//	@RequestMapping(value = "/operateProduct", method = { RequestMethod.POST, RequestMethod.GET })
//	public void operateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		String oper = request.getParameter("oper");
//		String id = request.getParameter("id");
//		if (oper.equals("del")) {
//			String[] ids = id.split(",");
//			deleteProduct(request, response, (Integer[]) ConvertUtils.convert(ids, Integer.class));
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
//			String productName = request.getParameter("name");
//			String productBrief = request.getParameter("brief");
//			String productContent = request.getParameter("content");
//			String productAmount = request.getParameter("amount");
//			String productImage = request.getParameter("image");			
//			String produnctXinghao = request.getParameter("xinghao");
//			String productGuige = request.getParameter("guige");
//			String productCaizhi = request.getParameter("caizhi");
//			String productPinpai = request.getParameter("pinpai");
//			String productChandi = request.getParameter("chandi");			
//			String productDanwei = request.getParameter("danwei");
//			String productClickNum = request.getParameter("clickNum");
//			int productSales =Integer.parseInt( request.getParameter("sales"));
//			String productRexiaoName = request.getParameter("rexiao");
//			String categoryName = request.getParameter("categoryName");
//			String productRexiao = "0";
//			if (productRexiaoName != null && "热销".equals(productRexiaoName)){
//				productRexiao = "1";
//			}			
//			TzzDictionary tzzDict =  dictService.getByProerties("name",categoryName);
//			int categoryId = 0 ; 
//			if (tzzDict != null && tzzDict.getId() > 0 ){
//				categoryId = tzzDict.getId();
//			} 	
//			TzzProduct tzzProduct = null;
//			if (oper.equals("edit")) {
//				tzzProduct = productService.get(Integer.valueOf(id));
//			}			
//			TzzProduct sameNameProduct = productService.getByProerties("name", productName);
//			
//			if (StringUtils.isBlank(productName) || StringUtils.isBlank(productAmount) || StringUtils.isBlank(productGuige) || StringUtils.isBlank(productPinpai)|| StringUtils.isBlank(productChandi)) {
//				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
//				result.put("message", "请填写产品的名字、销售金额， 产品规格、品牌， 产地等级信息");
//				writeJSON(response, result);
//			} else if (null != sameNameProduct && oper.equals("add")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "产品名字已经存在，请重新输入");
//				writeJSON(response, result);
//			} else if (null != tzzProduct && (tzzProduct.getId() != Integer.valueOf(id)) && oper.equals("edit")) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "要编辑的记录有问题，请重新尝试！");
//				writeJSON(response, result);
//			} else if (categoryId != 0  && null == tzzProduct) {
//				response.setStatus(HttpServletResponse.SC_CONFLICT);
//				result.put("message", "该产品对应的分类不存在，请先添加分类！");
//				writeJSON(response, result);
//			} else {
//				if (oper.equals("edit")) {
//					if (productName != null &&!"".equals(productName)){
//						tzzProduct.setName(productName);
//					}
//					if (productBrief != null &&!"".equals(productBrief)){
//						tzzProduct.setBrief(productBrief);
//					}
//					if (productContent != null && !"".equals(productContent)){
//						tzzProduct.setContent(productContent);
//					}
//					if (productAmount != null && !"".equals(productAmount)){
//						tzzProduct.setAmount(Float.valueOf(productAmount));
//					}
//					tzzProduct.setImage(productImage);
//					if  (produnctXinghao != null && !"".equals(produnctXinghao)){
//						tzzProduct.setXinghao(produnctXinghao);
//					}
//					if  (productGuige != null && !"".equals(productGuige)){
//						tzzProduct.setGuige(productGuige);
//					}
//					if  (productCaizhi != null && !"".equals(productCaizhi)){
//						tzzProduct.setCaizhi(productCaizhi);
//					}
//					if (productPinpai != null && !"".equals(productPinpai)){
//						tzzProduct.setPinpai(productPinpai);
//					}
//					if (productChandi != null && !"".equals(productChandi)){
//						tzzProduct.setChandi(productChandi);
//					}
//					if (productDanwei != null && !"".equals(productDanwei)){
//						tzzProduct.setDanwei(productDanwei);
//					}
//					if (productClickNum != null && !"".equals(productClickNum)){
//						tzzProduct.setClickNum(Integer.valueOf(productClickNum));
//					}
//					tzzProduct.setSales(productSales);
//					tzzProduct.setRexiao(productRexiao);
//					tzzProduct.setCmd("edit");
//					doSave(tzzProduct, request, response);
//				} else if (oper.equals("add")) {
//					TzzProduct tzzProduct1 = new TzzProduct();					
//					tzzProduct1.setName(productName);
//					tzzProduct1.setBrief(productBrief);
//					tzzProduct1.setContent(productContent);
//					tzzProduct1.setAmount(Float.valueOf(productAmount));
//					tzzProduct1.setImage(productImage);
//					tzzProduct1.setXinghao(produnctXinghao);
//					tzzProduct1.setGuige(productGuige);
//					tzzProduct1.setCaizhi(productCaizhi);
//					tzzProduct1.setPinpai(productPinpai);
//					tzzProduct1.setChandi(productChandi);
//					tzzProduct1.setDanwei(productDanwei);
//					tzzProduct1.setClickNum(Integer.valueOf(productClickNum));
//					tzzProduct1.setSales(productSales);
//					tzzProduct1.setRexiao(productRexiao);
//					tzzProduct1.setCmd("new");
//					doSave(tzzProduct1, request, response);
//				}
//			}
//		}
//	}
//	
//	
//	private static SimpleDateFormat sdfa = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//	/**
//	 *  上传图片/sys/TzzProduct/fileUpload
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
//				HttpServletRequest request, HttpServletResponse response) throws Exception {
//			RequestContext requestContext = new RequestContext(request);
//			//name 简介brief 内容介绍content 成本价costPrice 金额amount 类型categoryName 
//			//图片image 型号xinghao 规格guige 材质caizhi 品牌pinpai 产地chandi 单位danwei 点击浏览次数clickNum 销量sales 热销rexiaoName
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			String id= request.getParameter("id");  
//			
//			String name=request.getParameter("name"); 
//			String brief=request.getParameter("brief");
//			String content=request.getParameter("addContent");
//			String costPrice=request.getParameter("costPrice");
//			String amount=request.getParameter("amount");
//			String xinghao=request.getParameter("xinghao");
//			String guige=request.getParameter("guige");
//			
//			String caizhi=request.getParameter("caizhi");
//			String pinpai=request.getParameter("pinpai");
//			String chandi=request.getParameter("chandi");
//			String danwei=request.getParameter("danwei");  
//			String rexiao=request.getParameter("rexiao");  
//			
//			int familyOne = Integer.valueOf(request.getParameter("familyOne"));
//			int familyTwo = Integer.valueOf(request.getParameter("familyTwo"));
//			int familyId = Integer.valueOf(request.getParameter("familyId"));
//			
//			List<MultipartFile> list = new ArrayList<MultipartFile>();
//	    	list.add(file);
//	    	list.add(file1);
//	    	list.add(file2);
//	    	list.add(file3);
//		    
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
//			TzzProduct tzzProduct=new TzzProduct();
////			String name 
////			String brief 
////			String content 
////			String costPrice 
////			String amount 
////			String category 
////			String xinghao 
////			String guige 
////			
////			String caizhi 
////			String pinpai 
////			String chandi 
////			String danwei  
////			String rexiao 
//			tzzProduct.setName(name);
//			tzzProduct.setBrief(brief);
//			tzzProduct.setContent(content);
//			tzzProduct.setCostPrice(Float.valueOf(costPrice)); 
//			tzzProduct.setAmount(Float.valueOf(amount));
//			tzzProduct.setXinghao(xinghao);
//			tzzProduct.setGuige(guige);
//			tzzProduct.setCaizhi(caizhi);
//			tzzProduct.setPinpai(pinpai);
//			tzzProduct.setChandi(chandi);
//			tzzProduct.setDanwei(danwei);
//			tzzProduct.setRexiao(rexiao);
//			tzzProduct.setFamilyOne(familyOne);
//			tzzProduct.setFamilyTwo(familyTwo);
//			tzzProduct.setFamilyId(familyId);
//			
//	//		tzzIndexCarousel.setStyle("0"); 
//	//		tzzIndexCarousel.setCarouselType(Integer.parseInt(carouselType)); 
//			if((image.equals("") || image ==null) && id != null){
//				tzzProduct.setImage(productService.get(Integer.parseInt(id)).getImage());
//			}else {
//				tzzProduct.setImage(image);
//			}  
//			
//			if( (image1.equals("") || image1 == null) && id != null){
//				tzzProduct.setImage1(productService.get(Integer.parseInt(id)).getImage1());
//			}else {
//				tzzProduct.setImage1(image1);
//			} 
//			
//			if( (image2.equals("") || image2 ==null) && id != null){
//				tzzProduct.setImage2(productService.get(Integer.parseInt(id)).getImage2());
//			}else {
//				tzzProduct.setImage2(image2);
//			} 
//			
//			if((image3.equals("")||image3 ==null) && id != null){
//				tzzProduct.setImage3(productService.get(Integer.parseInt(id)).getImage3());
//			}else {
//				tzzProduct.setImage3(image3);
//			} 
//			
//			if(id!=null){   
//				TzzProduct entity=productService.get(Integer.parseInt(id)); 
//				tzzProduct.setId(Integer.parseInt(id));
//				tzzProduct.setClickNum(entity.getClickNum());
//				tzzProduct.setSales(entity.getSales());
//	 			tzzProduct.setCreateTime(entity.getCreateTime());
//				productService.merge(tzzProduct);
//			}else { 
//				tzzProduct.setClickNum(0);
//				tzzProduct.setSales(0);
//				tzzProduct.setCreateTime(new Date());
//				productService.persist(tzzProduct);
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
//	
//	
//}
