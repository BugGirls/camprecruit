package com.jeefw.controller.sys;

import java.io.OutputStream;
import java.net.URLDecoder;
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
import com.jeefw.model.sys.ProductWarehouse;
import com.jeefw.model.sys.ProductWarehouseCount;
import com.jeefw.service.sys.ProductWarehouseService;

import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 库存商品详情信息控制层 
 */
@Controller
@RequestMapping("/sys/warehouseProduct")
public class ProductWarehouseController extends JavaEEFrameworkBaseController<IntoWarehouseRecord> implements Constant {

	@Resource
	ProductWarehouseService productWarehouseService;

	// 库存查询盘点
	@RequestMapping(value = "/getWarehouseProductList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getWarehouseProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		ProductWarehouse productWarehouse = new ProductWarehouse();
		Integer allianceId = getCurrentAllianceId();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("productNo") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_productNo(result.getString("data"));
				}
				if (result.getString("field").equals("productName") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_productName(result.getString("data"));
				}
				if (result.getString("field").equals("brand") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_brand(result.getString("data"));
				}
				if (result.getString("field").equals("barCode") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_barCode(result.getString("data"));
				}
				if (result.getString("field").equals("bigCategoryName") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_bigCategoryName(result.getString("data"));
				}
				if (result.getString("field").equals("smallCategoryName") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_smallCategoryName(result.getString("data"));
				}
				if (result.getString("field").equals("storageLocation") && result.getString("op").equals("cn")) {
					productWarehouse.set$like_storageLocation(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				productWarehouse.setFlag("OR");
			} else {
				productWarehouse.setFlag("AND");
			}
		}
		productWarehouse.setAllianceId(allianceId+"");
		productWarehouse.setFirstResult((firstResult - 1) * maxResults);
		productWarehouse.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		productWarehouse.setSortedConditions(sortedCondition);
		QueryResult<ProductWarehouse> queryResult = productWarehouseService.doPaginationQuery(productWarehouse);
		JqGridPageView<ProductWarehouse> dictListView = new JqGridPageView<ProductWarehouse>();
		dictListView.setMaxResults(maxResults);
		List<ProductWarehouse> dictWithSubList = productWarehouseService.queryProductWarehouseWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}


	// 操作类型的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateWarehouseProduct", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateWarehouseProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	@RequestMapping(value = "/getProductWarehouseCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ProductWarehouseCount getProductWarehouseCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer allianceId = getCurrentAllianceId();
		ProductWarehouseCount productWarehouseCount = productWarehouseService.doCountProductWarehouse(allianceId);
		return productWarehouseCount;
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
	public ProductWarehouse findByNoAndAllianceId(HttpServletRequest request, HttpServletResponse response, String productNo, String allianceId){
//		List<ProductWarehouse> productWarehouse  = productWarehouseService.findByNoAndAllianceId(productNo,allianceId);
		String[] propName = {"productNo","allianceId"};
		String[] propValue = {productNo,allianceId};
		List<ProductWarehouse> productWarehouse  = productWarehouseService.queryByProerties(propName, propValue);
		if(null != productWarehouse && productWarehouse.size() > 0){
			return productWarehouse.get(0);
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
	public ProductWarehouse findByProductWarehouseId(HttpServletRequest request, HttpServletResponse response, String productWarehouseId){
		ProductWarehouse productWarehouse  = productWarehouseService.getByProerties("id", Long.parseLong(productWarehouseId));
		return productWarehouse;
	}

}
