package com.jeefw.controller.sys;

import java.io.IOException;
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

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.ProductInfoType;
import com.jeefw.model.sys.ProductPropertyTemp;
import com.jeefw.service.sys.ProductPropertyTempService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 商品属性规格模板控制层 
 */
@Controller
@RequestMapping("/sys/productPropertyTemp")
public class ProductPropertyTempController extends JavaEEFrameworkBaseController<ProductInfoType> implements Constant {

	@Resource
	private ProductPropertyTempService productPropertyTempService;

	// 查询属性模板的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getProductPropertyTemp", method = { RequestMethod.POST, RequestMethod.GET })
	public void getProductType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		ProductPropertyTemp productPropertyTemp = new ProductPropertyTemp();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("propertyKey") && result.getString("op").equals("eq")) {
					productPropertyTemp.set$eq_propertyKey(result.getString("data"));
				}
				if (result.getString("field").equals("propertyValue") && result.getString("op").equals("cn")) {
					productPropertyTemp.set$like_propertyValue(result.getString("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				productPropertyTemp.setFlag("OR");
			} else {
				productPropertyTemp.setFlag("AND");
			}
		}
		productPropertyTemp.setFirstResult((firstResult - 1) * maxResults);
		productPropertyTemp.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		productPropertyTemp.setSortedConditions(sortedCondition);
		QueryResult<ProductPropertyTemp> queryResult = productPropertyTempService.doPaginationQuery(productPropertyTemp);
		JqGridPageView<ProductPropertyTemp> dictListView = new JqGridPageView<ProductPropertyTemp>();
		dictListView.setMaxResults(maxResults);
		List<ProductPropertyTemp> dictWithSubList = productPropertyTempService.queryProductPropertyTempWithSubList(queryResult.getResultList());
		dictListView.setRows(dictWithSubList);
		dictListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, dictListView);
	}

	// 保存实体Bean
	@RequestMapping(value = "/saveProductPropertyTemp", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(ProductPropertyTemp entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			productPropertyTempService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			productPropertyTempService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作属性模板的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateProductPropertyTemp", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateProductType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteProductPropertyTemp(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
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
			String propertyKey = request.getParameter("propertyKey");
			String propertyValue = request.getParameter("propertyValue");
			String sequence = request.getParameter("sequence");
			String parentPropertykey = request.getParameter("parentPropertykey");
			ProductPropertyTemp dict = null;
			if (oper.equals("edit")) {
				dict = productPropertyTempService.get(Long.valueOf(id));
			}
			ProductPropertyTemp propertyKeyProductType = productPropertyTempService.getByProerties("propertyKey", propertyKey);
			ProductPropertyTemp parentPropertykeyDict = productPropertyTempService.getByProerties("propertyKey", parentPropertykey);
			if (StringUtils.isBlank(propertyKey) || StringUtils.isBlank(propertyValue)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写属性编码、属性名称");
				writeJSON(response, result);
			} else if (null != propertyKeyProductType && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此属性编码已存在，请重新输入");
				writeJSON(response, result);
			} else if (null != propertyKeyProductType && !dict.getPropertyKey().equalsIgnoreCase(propertyKey) && oper.equals("edit")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此属性编码已存在，请重新输入");
				writeJSON(response, result);
			} else if (StringUtils.isNotBlank(parentPropertykey) && null == parentPropertykeyDict) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "上级属性编码输入有误，请重新输入");
				writeJSON(response, result);
			} else {
				ProductPropertyTemp entity = new ProductPropertyTemp();
				entity.setPropertyKey(propertyKey);
				entity.setPropertyValue(propertyValue);
				entity.setSequence(sequence == "" ? null : Long.valueOf(sequence));
				entity.setParentPropertykey(parentPropertykey);
				if(null != parentPropertykeyDict){
					entity.setParentPropertyValue(parentPropertykeyDict.getPropertyValue());
				}
				if (oper.equals("edit")) {
					entity.setId(Long.valueOf(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除
	@RequestMapping("/deleteProductPropertyTemp")
	public void deleteProductPropertyTemp(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = productPropertyTempService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}
	
	// 父类类型
	@RequestMapping("/getPartnetProductPropertyTemp")
	public void getPartnetProductPropertyTemp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String propertykey = request.getParameter("propertykey");
		ProductPropertyTemp productPropertyTemp = productPropertyTempService.getByProerties("propertyKey", propertykey);
		List<ProductPropertyTemp> typeList = productPropertyTempService.queryByProerties("parentPropertykey", "");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < typeList.size(); i++) {
			if(null != productPropertyTemp && StringUtils.isNotEmpty(productPropertyTemp.getParentPropertykey()) && productPropertyTemp.getParentPropertykey().equals(typeList.get(i).getPropertyKey())){
				builder.append("<option value='" + typeList.get(i).getPropertyKey() + "' selected='selected'>" +typeList.get(i).getPropertyValue()+  "</option>");
			} else {
				builder.append("<option value='" + typeList.get(i).getPropertyKey() + "'>" +typeList.get(i).getPropertyValue()+  "</option>");
			}
		}
		writeJSON(response, builder.toString());
	}

	/**
	 * 根据父类查询子类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPropertyTempSelectList", method = { RequestMethod.POST, RequestMethod.GET })
	public void getPropertyTempSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentPropertykey = request.getParameter("parentPropertykey");
		String propertykey = request.getParameter("propertykey");
		ProductPropertyTemp productPropertyTemp = productPropertyTempService.getByProerties("propertyKey", propertykey);
		List<ProductPropertyTemp> typeList = productPropertyTempService.queryByProerties("parentPropertykey", StringUtils.isNotEmpty(parentPropertykey)?parentPropertykey:productPropertyTemp.getParentPropertykey());
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < typeList.size(); i++) {
			if(null != productPropertyTemp && StringUtils.isNotEmpty(productPropertyTemp.getPropertyKey()) && productPropertyTemp.getPropertyKey().equals(typeList.get(i).getPropertyKey())){
				builder.append("<option value='" + typeList.get(i).getPropertyKey() + "' selected='selected'>" +typeList.get(i).getPropertyValue()+  "</option>");
			} else {
				builder.append("<option value='" + typeList.get(i).getPropertyKey() + "'>" +typeList.get(i).getPropertyValue()+  "</option>");
			}
		}
		writeJSON(response, builder.toString());
	}
}
