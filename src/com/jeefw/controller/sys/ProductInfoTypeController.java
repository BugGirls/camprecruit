package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.ProductInfoType;
import com.jeefw.service.sys.ProductInfoTypeService;
import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.*;

/**
 * 商品类型控制层
 */
@Controller
@RequestMapping("/sys/productType")
public class ProductInfoTypeController extends JavaEEFrameworkBaseController<ProductInfoType> implements Constant {

    @Resource
    private ProductInfoTypeService productInfoTypeService;

    // 查询类型的表格，包括分页、搜索和排序
    @RequestMapping(value = "/getProductType", method = {RequestMethod.POST, RequestMethod.GET})
    public void getProductType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer firstResult = Integer.valueOf(request.getParameter("page"));
        Integer maxResults = Integer.valueOf(request.getParameter("rows"));
        String sortedObject = request.getParameter("sidx");
        String sortedValue = request.getParameter("sord");
        String filters = request.getParameter("filters");
        ProductInfoType producttype = new ProductInfoType();
        if (StringUtils.isNotBlank(filters)) {
            JSONObject jsonObject = JSONObject.fromObject(filters);
            JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject result = (JSONObject) jsonArray.get(i);
                if (result.getString("field").equals("typeKey") && result.getString("op").equals("eq")) {
                    producttype.set$eq_typeKey(result.getString("data"));
                }
                if (result.getString("field").equals("typeValue") && result.getString("op").equals("cn")) {
                    producttype.set$like_typeValue(result.getString("data"));
                }
            }
            if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
                producttype.setFlag("OR");
            } else {
                producttype.setFlag("AND");
            }
        }
        producttype.setFirstResult((firstResult - 1) * maxResults);
        producttype.setMaxResults(maxResults);
        Map<String, String> sortedCondition = new HashMap<String, String>();
        sortedCondition.put(sortedObject, sortedValue);
        producttype.setSortedConditions(sortedCondition);
        QueryResult<ProductInfoType> queryResult = productInfoTypeService.doPaginationQuery(producttype);
        JqGridPageView<ProductInfoType> dictListView = new JqGridPageView<ProductInfoType>();
        dictListView.setMaxResults(maxResults);
        List<ProductInfoType> dictWithSubList = productInfoTypeService.queryProductInfoTypeWithSubList(queryResult.getResultList());
        dictListView.setRows(dictWithSubList);
        dictListView.setRecords(queryResult.getTotalCount());
        writeJSON(response, dictListView);
    }

    /**
     * 获取商品类别信息，包括一级分类和二级分类-前台
     *
     * @return
     */
    @RequestMapping(value = "/getProductTypeFe", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getProductTypeFe() {
        ProductInfoType productInfoType = new ProductInfoType();
        QueryResult<ProductInfoType> queryResult = productInfoTypeService.doPaginationQuery(productInfoType);

        Map<String, Object> resultMap = new HashMap<>();
        for (Iterator iterator = queryResult.getResultList().iterator(); iterator.hasNext(); ) {
            ProductInfoType type = (ProductInfoType) iterator.next();
            if (StringUtils.isBlank(type.getParentTypekey())) {
                List<ProductInfoType> typeList = productInfoTypeService.queryByProerties("parentTypekey", type.getTypeKey());
                resultMap.put(type.getTypeValue(), typeList);
            }
        }

        return resultMap;
    }

    // 保存实体Bean
    @RequestMapping(value = "/saveProductType", method = {RequestMethod.POST, RequestMethod.GET})
    public void doSave(ProductInfoType entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
        if (CMD_EDIT.equals(parameter.getCmd())) {
            productInfoTypeService.merge(entity);
        } else if (CMD_NEW.equals(parameter.getCmd())) {
            productInfoTypeService.persist(entity);
        }
        parameter.setSuccess(true);
        writeJSON(response, parameter);
    }

    // 操作类型的删除、导出Excel、字段判断和保存
    @RequestMapping(value = "/operateProductType", method = {RequestMethod.POST, RequestMethod.GET})
    public void operateProductType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oper = request.getParameter("oper");
        String id = request.getParameter("id");
        if (oper.equals("del")) {
            String[] ids = id.split(",");
            deleteProductType(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
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
            String typeKey = request.getParameter("typeKey");
            String typeValue = request.getParameter("typeValue");
            String sequence = request.getParameter("sequence");
            String parentTypekey = request.getParameter("parentTypekey");
            ProductInfoType dict = null;
            if (oper.equals("edit")) {
                dict = productInfoTypeService.get(Long.valueOf(id));
            }
            ProductInfoType typeKeyProductType = productInfoTypeService.getByProerties("typeKey", typeKey);
            ProductInfoType parentTypekeyDict = productInfoTypeService.getByProerties("typeKey", parentTypekey);
            if (StringUtils.isBlank(typeKey) || StringUtils.isBlank(typeValue)) {
                response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                result.put("message", "请填写类型编码、类型名称");
                writeJSON(response, result);
            } else if (null != typeKeyProductType && oper.equals("add")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此类型编码已存在，请重新输入");
                writeJSON(response, result);
            } else if (null != typeKeyProductType && !dict.getTypeKey().equalsIgnoreCase(typeKey) && oper.equals("edit")) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "此类型编码已存在，请重新输入");
                writeJSON(response, result);
            } else if (StringUtils.isNotBlank(parentTypekey) && null == parentTypekeyDict) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                result.put("message", "上级类型编码输入有误，请重新输入");
                writeJSON(response, result);
            } else {
                ProductInfoType entity = new ProductInfoType();
                entity.setTypeKey(typeKey);
                entity.setTypeValue(typeValue);
                entity.setSequence(sequence == "" ? null : Long.valueOf(sequence));
                entity.setParentTypekey(parentTypekey);
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

    // 删除类型
    @RequestMapping("/deleteProductType")
    public void deleteProductType(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
        boolean flag = productInfoTypeService.deleteByPK(ids);
        if (flag) {
            writeJSON(response, "{success:true}");
        } else {
            writeJSON(response, "{success:false}");
        }
    }

    // 父类类型
    @RequestMapping("/getPartnetProductType")
    public void getPartnetProductType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bigCategoryNo = request.getParameter("bigCategoryNo");
        List<ProductInfoType> typeList = productInfoTypeService.queryByProerties("parentTypekey", "");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < typeList.size(); i++) {
            if (StringUtils.isNotEmpty(bigCategoryNo) && bigCategoryNo.equals(typeList.get(i).getTypeKey())) {
                builder.append("<option value='" + typeList.get(i).getTypeKey() + "' selected='selected'>" + typeList.get(i).getTypeValue() + "</option>");
            } else {
                builder.append("<option value='" + typeList.get(i).getTypeKey() + "'>" + typeList.get(i).getTypeValue() + "</option>");
            }

        }
        writeJSON(response, builder.toString());
    }

    /**
     * 根据父类查询子类
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getTypeSelectList", method = {RequestMethod.POST, RequestMethod.GET})
    public void getTypeSelectList(HttpServletRequest request, HttpServletResponse response, String parentTypeKey) throws Exception {
        List<ProductInfoType> typeList = productInfoTypeService.queryByProerties("parentTypekey", parentTypeKey);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < typeList.size(); i++) {
            builder.append("<option value='" + typeList.get(i).getTypeKey() + "'>" + typeList.get(i).getTypeValue() + "</option>");
        }
        writeJSON(response, builder.toString());
    }

    // 根据typeKey查询
    @RequestMapping("/getProductTypeByTypeKey")
    public void getProductTypeByTypeKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String smallCategoryNo = request.getParameter("smallCategoryNo");
        String bigCategoryNo = request.getParameter("bigCategoryNo");
        List<ProductInfoType> typeList = productInfoTypeService.queryByProerties("parentTypekey", bigCategoryNo);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < typeList.size(); i++) {
            if (StringUtils.isNotEmpty(smallCategoryNo) && smallCategoryNo.equals(typeList.get(i).getTypeKey())) {
                builder.append("<option value='" + typeList.get(i).getTypeKey() + "' selected='selected'>" + typeList.get(i).getTypeValue() + "</option>");
            } else {
                builder.append("<option value='" + typeList.get(i).getTypeKey() + "'>" + typeList.get(i).getTypeValue() + "</option>");
            }

        }
        writeJSON(response, builder.toString());
    }


}
