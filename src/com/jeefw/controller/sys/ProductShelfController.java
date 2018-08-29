package com.jeefw.controller.sys;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.IntoWarehouseRecord;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.model.sys.ProductOffShelf;
import com.jeefw.model.sys.ProductShelf;
import com.jeefw.model.sys.ProductWarehouse;
import com.jeefw.model.sys.ProductWarehouseCount;
import com.jeefw.service.sys.ProductInfoService;
import com.jeefw.service.sys.ProductOffShelfService;
import com.jeefw.service.sys.ProductShelfService;
import com.jeefw.service.sys.ProductWarehouseService;

import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 商品上架控制层 
 */
@Controller
@RequestMapping("/sys/productShelf")
public class ProductShelfController extends JavaEEFrameworkBaseController<IntoWarehouseRecord> implements Constant {

	@Resource
	ProductShelfService productShelfService;
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	ProductWarehouseService productWarehouseService;
	@Resource
	ProductOffShelfService productOffShelfService;

	// 库存查询盘点
	@RequestMapping(value = "/getProductShelfList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getProductShelfList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		ProductShelf productShelf = new ProductShelf();
		Integer allianceId = getCurrentAllianceId();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("productNo") && result.getString("op").equals("cn")) {
					productShelf.set$like_productNo(result.getString("data"));
				}
				if (result.getString("field").equals("productName") && result.getString("op").equals("cn")) {
					productShelf.set$like_productName(result.getString("data"));
				}
				if (result.getString("field").equals("brand") && result.getString("op").equals("cn")) {
					productShelf.set$like_brand(result.getString("data"));
				}
				if (result.getString("field").equals("barCode") && result.getString("op").equals("cn")) {
					productShelf.set$like_barCode(result.getString("data"));
				}
				if (result.getString("field").equals("bigCategoryName") && result.getString("op").equals("cn")) {
					productShelf.set$like_bigCategoryName(result.getString("data"));
				}
				if (result.getString("field").equals("smallCategoryName") && result.getString("op").equals("cn")) {
					productShelf.set$like_smallCategoryName(result.getString("data"));
				}
				if (result.getString("field").equals("storageLocation") && result.getString("op").equals("cn")) {
					productShelf.set$like_storageLocation(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				productShelf.setFlag("OR");
			} else {
				productShelf.setFlag("AND");
			}
		}
		productShelf.setAllianceId(allianceId+"");
		productShelf.setFirstResult((firstResult - 1) * maxResults);
		productShelf.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		productShelf.setSortedConditions(sortedCondition);
		QueryResult<ProductShelf> queryResult = productShelfService.doPaginationQuery(productShelf);
		JqGridPageView<ProductShelf> dictListView = new JqGridPageView<ProductShelf>();
		dictListView.setMaxResults(maxResults);
		List<ProductShelf> dictWithSubList = productShelfService.queryProductShelfWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}


	// 操作类型的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateProductShelf", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateProductShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		//String id = request.getParameter("id");
		if (oper.equals("excel")) {
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
		}
	}
	
	// 总进价
	@RequestMapping(value = "/getProductShelfCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ProductWarehouseCount getProductWarehouseCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer allianceId = getCurrentAllianceId();
		ProductWarehouseCount productWarehouseCount = productShelfService.doCountProductWarehouse(allianceId);
		return productWarehouseCount;
	}
	
	/**
	 * 上架
	 * @param detail
	 * @param creater
	 * @param createrNo
	 * @param allianceId
	 * @param onShelfNo
	 */
	@RequestMapping(value = "/saveProductShelf", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveProductShelf(String detail, String creater, String createrNo, String allianceId, String onShelfNo){
		JSONArray jsonarrayDetail = JSONArray.fromObject(detail);
		List<ProductShelf> productShelfList = (List<ProductShelf>) JSONArray.toCollection(jsonarrayDetail, ProductShelf.class);
		for (ProductShelf productShelf : productShelfList) {
			ProductInfo productInfo = productInfoService.getByProerties("no", productShelf.getProductNo());
			productShelf.setCreater(creater);
			productShelf.setCreaterNo(createrNo);
			productShelf.setAllianceId(allianceId);
			productShelf.setOnShelfNo(onShelfNo);
			productShelf.setGroundDate(new Date());
//			productShelf.setAdvicePrice(new BigDecimal(productInfo.getAdvicePrice()+""));
			productShelf.setBigCategoryName(productInfo.getBigCategoryName());
			productShelf.setBigCategoryNo(productInfo.getBigCategoryNo());
			productShelf.setSmallCategoryName(productInfo.getSmallCategoryName());
			productShelf.setSmallCategoryNo(productInfo.getSmallCategoryNo());
			productShelf.setProductPropertyTempNo(productInfo.getProductPropertyTempNo());
			productShelf.setImage(productInfo.getImage());
			productShelf.setImage1(productInfo.getImage1());
			productShelf.setImage2(productInfo.getImage2());
			productShelf.setImage3(productInfo.getImage3());
			productShelf.setBrand(productInfo.getBrand());
			//添加货架
			productShelfService.merge(productShelf);
			//对应库存商品减少
			ProductWarehouse productWarehouse  = productWarehouseService.get(productShelf.getProductWarehouseId());
			if(productWarehouse.getNum() > productShelf.getNum()){//盘点库存数量是否大于上架数量
				Long num = productWarehouse.getNum() - productShelf.getNum();
				productWarehouseService.updateProductWarehouseNum(productShelf.getProductWarehouseId(),num);//更新库存数量
			} else {//删除库存商品
				productWarehouseService.delete(productWarehouse);
			}
		}
	}
	
	/**
	 * 下架
	 * @param detail
	 * @param creater
	 * @param createrNo
	 * @param allianceId
	 * @param onShelfNo
	 */
	@RequestMapping(value = "/offProductShelf", method = { RequestMethod.POST, RequestMethod.GET })
	public void offProductShelf(String detail, String creater, String createrNo, String allianceId, String offShelfNo){
		JSONArray jsonarrayDetail = JSONArray.fromObject(detail);
		List<ProductOffShelf> productoffShelfList = (List<ProductOffShelf>) JSONArray.toCollection(jsonarrayDetail, ProductOffShelf.class);
		for (ProductOffShelf productoffShelf : productoffShelfList) {
			ProductInfo productInfo = productInfoService.getByProerties("no", productoffShelf.getProductNo());
			productoffShelf.setCreater(creater);
			productoffShelf.setCreaterNo(createrNo);
			productoffShelf.setAllianceId(allianceId);
			productoffShelf.setOffShelfNo(offShelfNo);
			productoffShelf.setGroundDate(new Date());
//			productShelf.setAdvicePrice(new BigDecimal(productInfo.getAdvicePrice()+""));
			productoffShelf.setBigCategoryName(productInfo.getBigCategoryName());
			productoffShelf.setBigCategoryNo(productInfo.getBigCategoryNo());
			productoffShelf.setSmallCategoryName(productInfo.getSmallCategoryName());
			productoffShelf.setSmallCategoryNo(productInfo.getSmallCategoryNo());
			productoffShelf.setProductPropertyTempNo(productInfo.getProductPropertyTempNo());
			productoffShelf.setImage(productInfo.getImage());
			productoffShelf.setImage1(productInfo.getImage1());
			productoffShelf.setImage2(productInfo.getImage2());
			productoffShelf.setImage3(productInfo.getImage3());
			productoffShelf.setBrand(productInfo.getBrand());
			//下架记录
			productOffShelfService.merge(productoffShelf);
			//对应货架商品减少
			ProductShelf productShelf  = productShelfService.get(productoffShelf.getOnShelfId());
			if(productShelf.getNum() > productoffShelf.getNum()){//盘点货架数量是否大于需要下架数量
				Long num = productShelf.getNum() - productoffShelf.getNum();
				productShelfService.updateProductOnShelfNum(productoffShelf.getOnShelfId(),num);//更新货架数量
			} else {//删除货架商品
				productShelfService.delete(productShelf);
			}
		}
	}
	
	/**
	 * 根据No查询
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping(value="/findByNoAndAllianceId")
	@ResponseBody
	public ProductShelf findByNoAndAllianceId(HttpServletRequest request, HttpServletResponse response, String productNo, String allianceId){
		String[] propName = {"productNo","allianceId"};
		String[] propValue = {productNo,allianceId};
		List<ProductShelf> productShelf  = productShelfService.queryByProerties(propName, propValue);
		if(null != productShelf && productShelf.size() > 0){
			return productShelf.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据Id查询
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping(value="/findByProductWarehouseId")
	@ResponseBody
	public ProductShelf findByProductWarehouseId(HttpServletRequest request, HttpServletResponse response, String onShelfId){
		ProductShelf productShelf  = productShelfService.getByProerties("id", Long.parseLong(onShelfId));
		return productShelf;
	}

}
