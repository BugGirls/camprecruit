package com.jeefw.controller.sys;

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
public class OrderController {

    @Resource
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     *
     * @param orderForm
     * @throws Exception
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    @ResponseBody
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

        return resultMap;
    }
}
