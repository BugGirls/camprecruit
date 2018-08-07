<%@page import="core.util.NumberHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.alipay.config.AlipayConfig" %>
<%@page import="com.alipay.api.AlipayClient"%>
<%@page import="com.alipay.api.DefaultAlipayClient"%>
<%@page import="com.alipay.api.AlipayApiException"%>
<%@page import="com.alipay.api.response.AlipayTradeWapPayResponse"%>
<%@page import="com.alipay.api.request.AlipayTradeWapPayRequest"%>
<%@page import="com.alipay.api.domain.AlipayTradeWapPayModel" %>
<%@page import="com.alipay.api.domain.AlipayTradeCreateModel"%>
<%
/* *
 * 功能：支付宝手机网站支付接口(alipay.trade.wap.pay)接口调试入口页面
 * 版本：2.0
 * 修改日期：2016-11-01
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 请确保项目文件有可写权限，不然打印不了日志。
 */
%>
<%
if(request.getParameter("orderid")!=null&&NumberHelper.string2Float(request.getParameter("totalfee"), 0)>0){
	// 商户订单号，商户网站订单系统中唯一订单号，必填
    String out_trade_no = new String(request.getParameter("orderid").getBytes("ISO-8859-1"),"UTF-8");
	// 订单名称，必填
    //String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
	//System.out.println(subject);
    // 付款金额，必填
    String total_amount=new String(request.getParameter("totalfee").getBytes("ISO-8859-1"),"UTF-8");
    //卡券码
    //String cardcode = new String(request.getParameter("cardcode").getBytes("ISO-8859-1"),"UTF-8");
    // 商品描述，可空
   // String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
    // 超时时间 可空
  // String timeout_express="2m";
    // 销售产品码 必填
    String product_code="QUICK_WAP_PAY";
    /**********************/
    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
    //调用RSA签名方式
    AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.private_key, AlipayConfig.FORMAT, AlipayConfig.input_charset, AlipayConfig.alipay_public_key,AlipayConfig.new_sign_type);
    AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
    
    // 封装请求支付信息
    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
    model.setOutTradeNo(out_trade_no);
    model.setSubject("亲子淘产品");
    model.setTotalAmount(total_amount);
    //model.setBody(body);
    //model.setTimeoutExpress(timeout_express);
    model.setProductCode(product_code);
    alipay_request.setBizModel(model);
    // 设置异步通知地址
    alipay_request.setNotifyUrl(AlipayConfig.notify_url);
    // 设置同步地址
    alipay_request.setReturnUrl(AlipayConfig.return_url);   
    
    // form表单生产
    String form = "";
	try {
		// 调用SDK生成表单
		form = client.pageExecute(alipay_request).getBody();
		int jsindex = form.indexOf("<script>");
		String formcontent = form.substring(0, jsindex);
		formcontent= formcontent+"<script src='../../jsp/js/ap.js'></script><script>var action = document.forms[0].action;var queryParam='';Array.prototype.slice.call(document.querySelectorAll('input[type=hidden]')).forEach(function (ele) {queryParam +=  '&'+ ele.name + '=' + encodeURIComponent(ele.value)});console.log(action+queryParam);_AP.pay(action+queryParam);</script>";
		
		response.setContentType("text/html;charset=" + AlipayConfig.input_charset); 
	    response.getWriter().write(formcontent);//直接将完整的表单html输出到页面 
	    response.getWriter().flush(); 
	    response.getWriter().close();
	} catch (AlipayApiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
}
%>
<!DOCTYPE html>
<html>
	<head>
	<title>支付宝手机网站支付接口</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body >

</body>

</html>