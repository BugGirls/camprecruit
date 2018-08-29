package com.jeefw.controller.sys;

<<<<<<< HEAD
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
=======
import com.jeefw.model.sys.OrderDetail;
import com.jeefw.service.sys.OrderMasterService;
import core.converter.OrderFormToOrderMasterDTO;
import core.dto.OrderMasterDTO;
import core.form.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
>>>>>>> merge project
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
<<<<<<< HEAD
public class OrderController extends JavaEEFrameworkBaseController implements Constant {
=======
public class OrderController {
>>>>>>> merge project

    @Resource
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     *
<<<<<<< HEAD
     * @param idLIst
=======
     * @param orderForm
>>>>>>> merge project
     * @throws Exception
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    @ResponseBody
<<<<<<< HEAD
    public Map<String, Object> createOrder(@RequestParam(value = "productIdList") String idLIst) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        if (StringUtils.isEmpty(idLIst)) {
            resultMap.put("msg", "购物车为空");
            return resultMap;
        }

        List<Long> productIdList = GSON.toObject(idLIst, new TypeToken<List<Long>>() {
        }.getType());

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Long id : productIdList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductQuantity(1);
            orderDetail.setProductId(id);
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
=======
    public Map<String, String> createOrder(OrderForm orderForm) throws Exception {
        Map<String, String> resultMap = new HashMap<>(16);

//        OrderMasterDTO orderMasterDTO = OrderFormToOrderMasterDTO.convert(orderForm);
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductQuantity(10);
        orderDetail.setProductId(2L);
        orderDetailList.add(orderDetail);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())) {
            throw new Exception("购物车不能为空");
        }

        OrderMasterDTO createResult = orderMasterService.create(orderMasterDTO);
        resultMap.put("orderId", createResult.getOrderId());
>>>>>>> merge project

        return resultMap;
    }
}
