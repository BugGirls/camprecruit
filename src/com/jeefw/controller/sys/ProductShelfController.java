package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.*;
import com.jeefw.model.sys.vo.ResultPageVo;
import com.jeefw.service.sys.*;
import core.dto.ProductShelfDTO;
import core.support.JqGridPageView;
import core.support.QueryResult;
import core.util.GSON;
import core.util.PatternUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

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
    @RequestMapping(value = "/getProductShelfList", method = {RequestMethod.POST, RequestMethod.GET})
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
        productShelf.setAllianceId(allianceId + "");
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
    @RequestMapping(value = "/operateProductShelf", method = {RequestMethod.POST, RequestMethod.GET})
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
    @RequestMapping(value = "/getProductShelfCount", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ProductWarehouseCount getProductWarehouseCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer allianceId = getCurrentAllianceId();
        ProductWarehouseCount productWarehouseCount = productShelfService.doCountProductWarehouse(allianceId);
        return productWarehouseCount;
    }

    /**
     * 上架
     *
     * @param detail
     * @param creater
     * @param createrNo
     * @param allianceId
     * @param onShelfNo
     */
    @RequestMapping(value = "/saveProductShelf", method = {RequestMethod.POST, RequestMethod.GET})
    public void saveProductShelf(String detail, String creater, String createrNo, String allianceId, String onShelfNo) {
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
            ProductWarehouse productWarehouse = productWarehouseService.get(productShelf.getProductWarehouseId());
            if (productWarehouse.getNum() > productShelf.getNum()) {//盘点库存数量是否大于上架数量
                Long num = productWarehouse.getNum() - productShelf.getNum();
                productWarehouseService.updateProductWarehouseNum(productShelf.getProductWarehouseId(), num);//更新库存数量
            } else {//删除库存商品
                productWarehouseService.delete(productWarehouse);
            }
        }
    }

    /**
     * 下架
     *
     * @param detail
     * @param creater
     * @param createrNo
     * @param allianceId
     */
    @RequestMapping(value = "/offProductShelf", method = {RequestMethod.POST, RequestMethod.GET})
    public void offProductShelf(String detail, String creater, String createrNo, String allianceId, String offShelfNo) {
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
            ProductShelf productShelf = productShelfService.get(productoffShelf.getOnShelfId());
            if (productShelf.getNum() > productoffShelf.getNum()) {//盘点货架数量是否大于需要下架数量
                Long num = productShelf.getNum() - productoffShelf.getNum();
                productShelfService.updateProductOnShelfNum(productoffShelf.getOnShelfId(), num);//更新货架数量
            } else {//删除货架商品
                productShelfService.delete(productShelf);
            }
        }
    }

    /**
     * 根据No查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findByNoAndAllianceId")
    @ResponseBody
    public ProductShelf findByNoAndAllianceId(HttpServletRequest request, HttpServletResponse response, String productNo, String allianceId) {
        String[] propName = {"productNo", "allianceId"};
        String[] propValue = {productNo, allianceId};
        List<ProductShelf> productShelf = productShelfService.queryByProerties(propName, propValue);
        if (null != productShelf && productShelf.size() > 0) {
            return productShelf.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据Id查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findByProductWarehouseId")
    @ResponseBody
    public ProductShelf findByProductWarehouseId(HttpServletRequest request, HttpServletResponse response, String onShelfId) {
        ProductShelf productShelf = productShelfService.getByProerties("id", Long.parseLong(onShelfId));
        return productShelf;
    }

    @Resource
    private ProductSalesVolumeService productSalesVolumeService;

    /**
     * 前台 - 获取商家商品分页信息
     *
     * @param request
     */
    @RequestMapping(value = "get_product_shelf_fe", method = RequestMethod.POST)
    @ResponseBody
    public ResultPageVo getProductShelfFe(HttpServletRequest request) {
        ProductShelf productShelf = new ProductShelf();

        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        String moneyInterval = request.getParameter("moneyInterval");
        String priceSort = request.getParameter("price");
        String salesSort = request.getParameter("sales");
        String firstType = request.getParameter("firstType");
        String secondType = request.getParameter("secondType");
        // 筛选
        Integer allianceId = getCurrentAllianceId();
        productShelf.set$eq_allianceId(allianceId.toString());
        if (StringUtils.isNotBlank(moneyInterval)) {
            // 匹配指定范围内的数字
            String[] dis = PatternUtil.getNumberForStr(moneyInterval);
            if (dis.length == 1 && PatternUtil.isStartWithNumber(moneyInterval)) {
                productShelf.set$eq_maxPrice(dis[0]);
            } else if (dis.length == 2) {
                if (StringUtils.isEmpty(dis[0])) {
                    productShelf.set$eq_minPrice(dis[1]);
                } else {
                    productShelf.set$eq_minPrice(dis[0]);
                    productShelf.set$eq_maxPrice(dis[1]);
                }
            }
        }
        if (StringUtils.isNotBlank(firstType)) {
            productShelf.set$eq_bigCategoryNo(firstType);
        }
        if (StringUtils.isNotBlank(firstType) && StringUtils.isNotBlank(secondType)) {
            productShelf.set$eq_smallCategoryNo(secondType);
        }

        // 排序
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put("id", "desc");
        if (StringUtils.isNotBlank(priceSort)) {
            sortedCondition.put("salePrice", priceSort);
        }
        if (StringUtils.isNotBlank(salesSort)) {
            sortedCondition.put("salesVolume", salesSort);
        }
        productShelf.setSortedConditions(sortedCondition);

        // 获取上架商品分页列表
        productShelf.setFirstResult((pageNum - 1) * pageSize);
        productShelf.setMaxResults(pageSize);
        List<ProductShelf> queryResult = productShelfService.selectProductShelfByParam(productShelf);
        System.out.println(GSON.toJson(queryResult));
        ResultPageVo resultPageVo = new ResultPageVo();
        resultPageVo.setData(assemblyProductShelfDTO(queryResult));
        resultPageVo.setPageNum(pageNum);
        resultPageVo.setPageSize(pageSize);
        resultPageVo.setTotalCount(Long.valueOf(queryResult.size()));
        resultPageVo.setTotalPage((Long.valueOf(queryResult.size()) + pageSize - 1) / pageSize);

        return resultPageVo;
    }

    private List<ProductShelfDTO> assemblyProductShelfDTO(List<ProductShelf> productShelfList) {
        List<ProductShelfDTO> productShelfDTOList = new ArrayList<>();

        for (ProductShelf productShelf : productShelfList) {
            ProductShelfDTO productShelfDTO = new ProductShelfDTO();
            productShelfDTO.setProductNo(productShelf.getProductNo());
            productShelfDTO.setProductImage(productShelf.getImage());
            productShelfDTO.setProductName(productShelf.getProductName());
            productShelfDTO.setProductPrice(new BigDecimal(productShelf.getSalePrice()));
            productShelfDTO.setProductSubTitle(productShelf.getProductSubTitle());
            productShelfDTO.setProductSalesVolume(productShelf.getSalesVolume());
            productShelfDTOList.add(productShelfDTO);
        }

        return productShelfDTOList;
    }
}
