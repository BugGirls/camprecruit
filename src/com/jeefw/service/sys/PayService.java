package com.jeefw.service.sys;

import core.dto.OrderMasterDTO;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public interface PayService {

    String createWechatSwipePay(OrderMasterDTO orderMasterDTO);

    String createAlipayTradePay(OrderMasterDTO orderMasterDTO);

    String tradeRefund(OrderMasterDTO orderMasterDTO);

}
