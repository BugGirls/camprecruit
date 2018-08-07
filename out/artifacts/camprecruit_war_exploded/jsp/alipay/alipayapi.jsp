<%
/* *
 *功能：手机网站支付接口接入页
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *************************注意*****************
 *如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
 *1、开发文档中心（https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1）
 *2、商户帮助中心（https://cshall.alipay.com/enterprise/help_detail.htm?help_id=473888）
 *3、支持中心（https://support.open.alipay.com/alipay/support/index.htm）
 *如果不想使用扩展功能请把扩展功能参数赋空值。
 **********************************************
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>支付宝手机网站支付接口</title>
	</head>
	<%
		////////////////////////////////////请求参数//////////////////////////////////////

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("orderid").getBytes("ISO-8859-1"),"UTF-8");
		
        //订单名称，必填
        String subject = new String("testpay".getBytes("ISO-8859-1"),"UTF-8");
		
        //付款金额，必填
        String total_fee = new String(request.getParameter("totalfee").getBytes("ISO-8859-1"),"UTF-8");
		//String cardcode = new String(request.getParameter("cardcode").getBytes("ISO-8859-1"),"UTF-8");
        //收银台页面上，商品展示的超链接，必填
        //String show_url = new String("".getBytes("ISO-8859-1"),"UTF-8");
		
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
// 		Map<String, String> sParaTemp = new HashMap<String, String>();
// 		sParaTemp.put("service", AlipayConfig.service);
//         sParaTemp.put("partner", AlipayConfig.partner);
//         sParaTemp.put("seller_id", AlipayConfig.seller_id);
//         sParaTemp.put("_input_charset", AlipayConfig.input_charset);
// 		sParaTemp.put("payment_type", AlipayConfig.payment_type);
// 		sParaTemp.put("notify_url", AlipayConfig.notify_url);
// 		//sParaTemp.put("return_url", AlipayConfig.return_url+"?cardcode="+cardcode);
// 		sParaTemp.put("out_trade_no", out_trade_no);
// 		sParaTemp.put("subject", subject);
// 		sParaTemp.put("total_fee", total_fee);
// 		sParaTemp.put("show_url", "");
// 		//sParaTemp.put("app_pay","Y");//启用此参数可唤起钱包APP支付。
// 		//sParaTemp.put("body", "adventruepay");
// 		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
//         //如sParaTemp.put("参数名","参数值");

		
// 		//建立请求
// 		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
// 		out.println(sHtmlText);


AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.APP_ID, AlipayConfig.private_key, "json", AlipayConfig.input_charset, AlipayConfig.alipay_public_key, "RSA2");  //获得初始化的AlipayClient
AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
alipayRequest.setReturnUrl(AlipayConfig.return_url);
alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//在公共参数中设置回跳和通知地址
alipayRequest.setBizContent("{" +
  "    \"out_trade_no\":\""+out_trade_no+"\"," +
  "    \"total_amount\":"+total_fee+"," +
  "    \"subject\":\""+subject+"\"," +
  "    \"seller_id\":\""+AlipayConfig.partner+"\"," +
  "    \"product_code\":\"QUICK_WAP_PAY\"" +
  "  }");//填充业务参数
String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
response.setContentType("text/html;charset=" + AlipayConfig.input_charset);
response.getWriter().write(form);//直接将完整的表单html输出到页面

response.getWriter().flush();
	%>
	<body>
	<script src="../../jsp/js/ap.js"></script>
	   	 <script>
	   	 	//var btn=document.querySelector(".J-btn-submit");
	   	 	//btn.addEventListener("click",function(e){
	   	 	//	e.preventDefault();
	   	 	//	e.stopPropagation();
	   	 	//	e.stopImmediatePropagation();
	   	 	//	var queryParam='';
	   	 	//	Array.prototype.slice.call(document.querySelectorAll("input[type=hidden]")).forEach(function(ele){
	   	 	//		queryParam += ele.name + "=" + encodeURIComponent(ele.value)+'&';
	   	 	//	});
	   	 	//	var gotoUrl = document.querySelector("#alipaysubmit").getAttribute('action')+'?'+queryParam;
	   	 	//	_AP.pay(gotoUrl);
	   	 	//	return false;
	   	 //	},false);
	   	 
	   	 
	   			//该js用于微信上使用支付宝支付
	           window.onload = function() {
	           var queryParam = '';
	           Array.prototype.slice.call(document.querySelectorAll('input[type=hidden]')).forEach(function (ele) {
	        	   queryParam += ele.name + '=' + encodeURIComponent(ele.value) + '&';
	           });
	           var gotoUrl = document.querySelector('#alipaysubmit').getAttribute('action') + queryParam;
	           _AP.pay(gotoUrl);
	        } 
	    </script>
	
	</body>
</html>
