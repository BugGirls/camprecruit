package com.jeefw.controller.sys;

import com.google.gson.reflect.TypeToken;
import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.OrderDetail;
import com.jeefw.service.sys.OrderMasterService;
import core.dto.OrderMasterDTO;
import core.util.GSON;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理
 *
 * @author Hystar
 * @date 2018/7/18
 */
@Controller
@RequestMapping(value = "/order/manager")
public class OrderController extends JavaEEFrameworkBaseController implements Constant {

    @Resource
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     *
     * @param productNoList
     * @throws Exception
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrder(@RequestParam(value = "productNoList") String productNoList) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        if (StringUtils.isEmpty(productNoList)) {
            resultMap.put("msg", "购物车为空");
            return resultMap;
        }

        List<String> productIdList = GSON.toObject(productNoList, new TypeToken<List<String>>() {
        }.getType());

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (String productNo : productIdList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductQuantity(1);
            orderDetail.setProductNo(productNo);
            orderDetailList.add(orderDetail);
        }
        orderMasterDTO.setOrderDetailList(orderDetailList);
        orderMasterDTO.setAllianceId(allianceId);
        OrderMasterDTO createResult = orderMasterService.create(orderMasterDTO);
        resultMap.put("orderId", createResult.getOrderId());
        resultMap.put("success", true);

        return resultMap;
    }

    /**
     * 创建智能搭配订单
     *
     * @param smartCollocationId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create_order_by_smart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrderBySmart(@RequestParam(value = "smartCollocationId") String smartCollocationId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        String orderId = orderMasterService.createBySmart(allianceId, smartCollocationId);
        resultMap.put("orderId", orderId);
        resultMap.put("success", true);

        return resultMap;
    }

    /**
     * 获取订单和订单详情信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "get_order_detail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderDetail(@RequestParam(value = "orderId") String orderId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (StringUtils.isEmpty(orderId)) {
            resultMap.put("msg", "订单不存在");
            return resultMap;
        }

        OrderMasterDTO orderMasterDTO = orderMasterService.getOrderDetail(orderId);
        resultMap.put("success", true);
        resultMap.put("orderMaster", orderMasterDTO);

        return resultMap;
    }
}
