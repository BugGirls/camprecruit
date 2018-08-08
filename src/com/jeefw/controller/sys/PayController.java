package com.jeefw.controller.sys;

import com.jeefw.service.sys.OrderMasterService;
import com.jeefw.service.sys.PayService;
import core.dto.OrderMasterDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 支付
 *
 * @author Hystar
 * @date 2018/7/18
 */
@Controller
@RequestMapping(value = "/pay/manager")
public class PayController {

    @Resource
    private OrderMasterService orderMasterService;

    @Resource
    private PayService payService;

    /**
     * 微信刷卡支付
     *
     * @param orderId  商户订单ID
     * @param authCode 授权码
     * @throws Exception
     */
    @RequestMapping(value = "create_wechat_swipe_pay", method = RequestMethod.POST)
    @ResponseBody
    public String createWechatSwipePay(@RequestParam(value = "orderId") String orderId,
                                       @RequestParam(value = "authCode") String authCode) throws Exception {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(authCode)) {
            throw new Exception("参数错误");
        }

        OrderMasterDTO orderMasterDTO = orderMasterService.getOrderById(orderId);
        if (orderMasterDTO == null) {
            throw new Exception("用户没有该订单");
        }
        orderMasterDTO.setAuthCode(authCode);

        String result = payService.createWechatSwipePay(orderMasterDTO);
        return result;
    }

    /**
     * 支付宝刷卡、声波支付
     *
     * @param orderId
     * @param authCode
     * @throws Exception
     */
    @RequestMapping(value = "create_alipay_trade_pay", method = RequestMethod.POST)
    @ResponseBody
    public String createAlipayTradePay(@RequestParam(value = "orderId") String orderId,
                                       @RequestParam(value = "authCode") String authCode) throws Exception {

        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(authCode)) {
            throw new Exception("参数错误");
        }

        OrderMasterDTO orderMasterDTO = orderMasterService.getOrderById(orderId);
        if (orderMasterDTO == null) {
            throw new Exception("用户没有该订单");
        }
        orderMasterDTO.setAuthCode(authCode);

        String result = payService.createAlipayTradePay(orderMasterDTO);
        return result;
    }

    /**
     * 退款
     *
     * @param orderId
     * @throws Exception
     */
    @RequestMapping(value = "alipay_trade_refund", method = RequestMethod.POST)
    @ResponseBody
    public String alipayTradeRefund(@RequestParam(value = "orderId") String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            throw new Exception("参数错误");
        }

        OrderMasterDTO orderMasterDTO = orderMasterService.getOrderById(orderId);
        if (orderMasterDTO == null) {
            throw new Exception("用户没有该订单");
        }

        String result = payService.tradeRefund(orderMasterDTO);
        return result;
    }
}
