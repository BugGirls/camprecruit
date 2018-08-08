package com.jeefw.service.sys.impl;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.jeefw.dao.sys.OrderMasterDao;
import com.jeefw.model.sys.OrderDetail;
import com.jeefw.model.sys.OrderMaster;
import com.jeefw.service.sys.OrderDetailSerivce;
import com.jeefw.service.sys.PayService;
import core.dto.*;
import core.enums.AlipayTradeCodeEnum;
import core.enums.OrderStatusEnum;
import core.enums.PayStatusEnum;
import core.enums.TradeTypeEnum;
import core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Hystar
 * @date 2018/7/18
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private OrderMasterDao orderMasterDao;

    @Resource
    private OrderDetailSerivce orderDetailSerivce;

    /**
     * 微信刷卡支付
     *
     * @param orderMasterDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createWechatSwipePay(OrderMasterDTO orderMasterDTO) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", PropertiesUtil.getProperty("WECHAT_APP_ID"));
        paramMap.put("mch_id", PropertiesUtil.getProperty("WECHAT_MCH_ID"));
        paramMap.put("nonce_str", WechatPayUtil.getRandomStr());
        paramMap.put("body", Const.ORDER_NAME + orderMasterDTO.getOrderId());
        paramMap.put("out_trade_no", orderMasterDTO.getOrderId());
        paramMap.put("total_fee", String.valueOf(BigDecimalUtil.mul(orderMasterDTO.getAmount().doubleValue(), new Double(100).doubleValue())));
        paramMap.put("spbill_create_ip", "1.1.1.1");
        paramMap.put("auth_code", orderMasterDTO.getAuthCode());

        String swipePayResultXml = WechatPayUtil.submit(WechatPayUtil.getXML(paramMap), Const.WECHAT_SWIPE_PAY_URL);
        WechatSwipePayDTO wechatSwipePayDTO = (WechatSwipePayDTO) XmlUtil.convertXmlStrToObject(WechatSwipePayDTO.class, swipePayResultXml);

        OrderMaster orderMaster = orderMasterDao.getByProerties("orderId", orderMasterDTO.getOrderId());
        if (wechatSwipePayDTO.getReturn_code().equals("FAIL")) {
            return wechatSwipePayDTO.getReturn_msg();
        } else if (wechatSwipePayDTO.getReturn_code().equals("SUCCESS") && wechatSwipePayDTO.getResult_code().equals("FAIL")) {
            if (wechatSwipePayDTO.getErr_code().equals("USERPAYING")) {
                while (wechatSwipePayDTO.getErr_code().equals("USERPAYING")) {
                    // 轮询请求订单状态
                    Map<String, String> orderMap = new HashMap<>();
                    orderMap.put("appid", PropertiesUtil.getProperty("WECHAT_APP_ID"));
                    orderMap.put("mch_id", PropertiesUtil.getProperty("WECHAT_MCH_ID"));
                    orderMap.put("nonce_str", WechatPayUtil.getRandomStr());
                    orderMap.put("out_trade_no", orderMasterDTO.getOrderId());

                    String orderXml = WechatPayUtil.submit(WechatPayUtil.getXML(orderMap), Const.WECHAT_QUERY_SWIPE_ORDER_URL);
                    WechatSwipeOrderDTO orderDTO = (WechatSwipeOrderDTO) XmlUtil.convertXmlStrToObject(WechatSwipeOrderDTO.class, orderXml);
                    if (orderDTO.getTrade_state() != null && orderDTO.getTrade_state().equals("SUCCESS")) {
                        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                        orderMaster.setPaymentTime(new Date());
                        orderMaster.setOpenid(orderDTO.getOpenid());
                        orderMaster.setTransactionId(orderDTO.getTransaction_id());
                        orderMaster.setTradeType(orderDTO.getTrade_type());
                        orderMasterDao.update(orderMaster);

                        return "SUCCESS";
                    }
                }
            } else {
                return wechatSwipePayDTO.getErr_code() + "：" + wechatSwipePayDTO.getErr_code_des();
            }
        } else if (wechatSwipePayDTO.getReturn_code().equals("SUCCESS") && wechatSwipePayDTO.getResult_code().equals("SUCCESS")) {
            orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
            orderMaster.setPaymentTime(new Date());
            orderMaster.setOpenid(wechatSwipePayDTO.getOpenid());
            orderMaster.setTransactionId(wechatSwipePayDTO.getTransaction_id());
            orderMaster.setTradeType(wechatSwipePayDTO.getTrade_type());
            orderMasterDao.update(orderMaster);
        }

        return "SUCCESS";
    }

    /**
     * 支付宝刷卡、声波支付方式
     *
     * @param orderMasterDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createAlipayTradePay(OrderMasterDTO orderMasterDTO) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        String outTradeNo = orderMasterDTO.getOrderId();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = Const.ORDER_NAME + outTradeNo;

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = orderMasterDTO.getAmount().toString();

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = orderMasterDTO.getAuthCode();
        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        //        String discountableAmount = "1.00";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = new StringBuilder(Const.ORDER_NAME).append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailSerivce.getByOrderId(orderMasterDTO.getOrderId());
        for (OrderDetail orderItem : orderDetailList) {
            GoodsDetail goodsDetail = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(), BigDecimalUtil.mul(orderItem.getProductPrice().doubleValue(), new Double(100).doubleValue()).longValue(), orderItem.getProductQuantity());
            goodsDetailList.add(goodsDetail);
        }

        //根据真实值填写
        String appAuthToken = "应用授权令牌";

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                //            .setAppAuthToken(appAuthToken)
                .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                .setTotalAmount(totalAmount).setStoreId(storeId)
                .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
                .setExtendParams(extendParams).setSellerId(sellerId)
                .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("config.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        System.out.println(GSON.toJson(result));
        OrderMaster orderMaster = orderMasterDao.getByProerties("orderId", orderMasterDTO.getOrderId());
        if (result.getTradeStatus().name().equals("SUCCESS")) {
            System.out.println("支付成功");
            AlipayTradePayResponse response = result.getResponse();
            if (response.getCode().equals(AlipayTradeCodeEnum.SUCCESS.getCode())) {
                orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                orderMaster.setTransactionId(response.getTradeNo());
                orderMaster.setPaymentTime(response.getGmtPayment());
                for (TradeFundBill fundBill : response.getFundBillList()) {
                    orderMaster.setTradeType(TradeTypeEnum.ALIPAY.getCode() + "_" + fundBill.getFundChannel());
                }

                orderMasterDao.update(orderMaster);
                return "SUCCESS";
            } else if (response.getCode().equals(AlipayTradeCodeEnum.FAILED.getCode())) {
                return "支付失败";
            } else if (response.getCode().equals(AlipayTradeCodeEnum.WAIT_BUYER_PAY.getCode())) {
                return "等待用户付款";
            } else if (response.getCode().equals(AlipayTradeCodeEnum.UNKNOWN.getCode())) {
                return "未知异常";
            } else {
                return "未知错误";
            }
        } else if (result.getTradeStatus().name().equals("FAILED")) {
            System.out.println(("支付宝支付失败!!!"));
            AlipayTradePayResponse response = result.getResponse();
            return "支付宝支付失败，" + response.getSubMsg();
        } else if (result.getTradeStatus().name().equals("UNKNOWN")) {
            System.out.println(("系统异常，订单状态未知!!!"));
            return "系统异常，订单状态未知";
        } else {
            System.out.println(("不支持的交易状态，交易返回异常!!!"));
            return "不支持的交易状态，交易返回异常";
        }
    }

    /**
     * 当面付2.0退款
     *
     * @param orderMasterDTO
     */
    @Override
    public String tradeRefund(OrderMasterDTO orderMasterDTO) {
        // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = orderMasterDTO.getOrderId();

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = orderMasterDTO.getAmount().toString();

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "";

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款，用户买多了";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo).setStoreId(storeId);

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("config.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);

        if (result.getTradeStatus().name().equals("SUCCESS")) {
            AlipayTradeRefundResponse response = result.getResponse();
            if (response.getCode().equals(AlipayTradeCodeEnum.SUCCESS.getCode())) {
                System.out.println("支付宝退款成功");

                // 更改订单状态
                OrderMaster orderMaster = orderMasterDao.getByProerties("orderId", orderMasterDTO.getOrderId());
                orderMaster.setOrderStatus(OrderStatusEnum.REFUND.getCode());
                orderMasterDao.update(orderMaster);
                return "SUCCESS";
            } else {
                return "未知错误";
            }
        } else if (result.getTradeStatus().name().equals("FAILED")) {
            System.out.println(("支付宝退款失败!!!"));
            AlipayTradeRefundResponse response = result.getResponse();
            return "支付宝退款失败，" + response.getSubMsg();
        } else if (result.getTradeStatus().name().equals("UNKNOWN")) {
            System.out.println(("系统异常，订单状态未知!!!"));
            return "系统异常，订单状态未知";
        } else {
            System.out.println(("不支持的交易状态，交易返回异常!!!"));
            return "不支持的交易状态，交易返回异常";
        }
    }
}
