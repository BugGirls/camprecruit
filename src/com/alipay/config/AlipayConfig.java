package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
		public static String partner = "2088521118855325";
		
		// appID，以2088开头由16位纯数字组成的字符串
			public static String APP_ID = "2016102402302582";
		
		// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
		public static String seller_id = partner;

		//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
		//public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKDIwpCNA9Ul9gz3IQvf2E1YFDPrvRXFrp/IaDf00rrh5XKeVdZynOG8zQOYypkReeMB+MsGHGe/ZIbQ0Qha+Hm5J8uXasvpZTOjolLdSI3wkgqIkre2c1SMR5nTJQ/PwRRYDaERElyQcBBFgtvEVdbtPvY1zs9+fjzzHWMbThwlAgMBAAECgYEAhXOc6lXhJH2JqGFGsJBu/GNrAV1daEDYHA8/UZKHhk4RH/2Dyab/x+3pTF75aesze9QbKIg9827MhYm0tnarXSwhSh5WB6Z8jazXV2r73AAIUVT8thnxUeaUF1069QZ2pwfsMRr6k/zkv5uXmd8Raw7v6oVRDB4EHAfAXRCEoFECQQDMR0FbsvpCnjh7kKlk7w8Itu93JehzcrUaUkgDh+CSogCmWQXuilrbsDZ+8LNn+dMuqqxgUfDILbVlkLhG96O3AkEAyX5WsUKWPNRnCRjjuOdT0ONTZ6cAcFEUzNXpFO9rgmrb52EEvx5PRGYcJpTrBEMeOrwAivFyJz6BImCc+FxXAwJAb9c/UF5AKEMJ95TvOWStHX9SwjAOAU83Gi8cYFp/OKjbpDevSLeVIRxr6boM74tW9QDamW2jX2WvH+lJCwnAJwJAHMla81ozAp/J1KfkqN8Lv3Uqzk0yp9k9ooIRGbeW4XwIzMa0meVnp7r63Jw781Dvea6wWSljiK3KXzH0Wxs+JwJBAKE6j0ugBxrEpMI4nLU2GuLBD5If/d1d4MofPS1/CKMD7KP7TUjpbGHIx7aRSQWQZ6/YoAqrg0EuyUBT3KCIUr4=";
		public static String private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzHhINf6ZPYONJKh1YKpQ6Ot9Jfb7KkJQ0k1RcgB99k5jSVFfkER48TAWaSOvgC9NK2EkmQYzXyj2CHXN1MT/+oN84vqnm2wrcbTMfDH6pNTjfesbhxbcRV3FqcWuTW1J0VYgVDF3pWrT+mok5M4gWSekexq3JaS404TiQyz8ljccBmTQDQ/PN5w5vElMEpfydo7Zek1ILv+/HfSYUoj525lrJSS9SduENj5+SUD4V4Nws09YRciCGyytR0se1Wu9Q1bm7vklFN5dl4Pu36mJB/T3/lBYJ/00jEHcXAPACyQdNxGorlzYPvB4iZnlfHdpt6ARxjxRmTuX/MqqZcG9FAgMBAAECggEBAIU+3G0gRacNS2dVJgk9e5Ep1ItGONVV4nqOExbQ1RxE3U2yIsPtnBF8+DoC/z1nbqJh7WDBKLYqo8EUiZxnq9SO9h+vbpLm5dh+f6YcVGkpx4owOp0fyQTkiOGHSGCRcfBK5HXMAlyi9rvkWCquVBFIoFwdrpCDZzU9SigkmnAjG8g675FqfLNaBie8uCLynBe+HP+psbzRwQqPnVWkJfLND44iHDT/n2010gUfEfv789gKaLqSfNWLHZmcoackfraNfPsvkQd1xYwn7NGuJRQnf0dRILatwUj8pbnusxKW/HCun7P8bgJ48/sUhI6Ub6OlxtqT5J5zQtJl6rxUJc0CgYEA9EXpOfMflcd1p+H/jTp6JIfENWnLMwJ7EMmlU6Laiwf5zkjDkgpRlbRuzV5J/hQVVTkwKMoHSar8Srmys8f/WSZ9fbDU7aw0peEiyI4oucJyxgUcUe4ovtogA3NBS7wqK4UEF+EdfvuqKss/Tf8xttxUpagFeJZwovP52HES0H8CgYEAu7dnc52bnFbSIud60NbpUyEakWCJy6X11vCw8yK1Z0oWp4cXtxxX08uSUcKDkvjM62hoqvVJNGuISbWZBDu2OAlWCW4q5NYs+4mpH2Uz6OpXpcnqU3iwN/9M2AZBHVR3jhEFFgMPzE78b1t/iNNT+bqD5J8wm+5TDAq1b5yUnjsCgYEAlB0X79VlX3JT+vnj9IRyr1fLNv/w//sPF/zMzEvA6/BoHhaszw6GrZl8XfZAM1mGHejRNMpbZOXSmJTEsJIiWLIoAWCJm5uBSNucNbNNKmWnJj7PDzazahXkt3LZMszjXQQImVocgnSil9QpjGeig1hR0yhwONAvd/8rjXvyho0CgYEApxCgssYnkym2Fbh8ltdei0ygD5iiqquGs4g9g/Z1Cntm1CyoT6CbDfVUwQ1j+m8SOcUPum9Zepzli+xiReE62H5+ox/4bveqyyeGbFmurHG8FeIxeZ6k/wFpi61tUEQ3rym4pBGPgt8dTaJaTBUA3PZBojyHj4+5O7NgLPhnULcCgYAYG1d02+CBTXTsLl93bzltnLt0UkKTgMVpiIhFD7Rb1To9cyQ9rcXce6TUyszUqHVwY37sIPyzj8BLwlYhQZ2tr9mItT5JcE96GYo2gXaXPRFp67m5t3Ui6PDoqt3AwKtHH6UTEHsgrk8fnEcGV6ECclSoTUe4LFz/N4rviwZQ2A==";
		
		// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
		//public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		public static String alipay_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuVd9jXUySmbIlwDHJH1lZlzi65kogs0oqWTN7N4sUP02Ru2a2LH8TfEIpZ9B2YggXKKZhDb28zPr3EJRHMpkBj+dGL3OTM3C/2PPL71O1Omnb9SyjPRqM2GbJ1fLyu4d8jf7z7GyUc7jVW5UclqH+lEDeCS7VDl1BisuFA7D4DN/rnIpMoSYVLKORXf0J4lSsVAbabahOOTKUFc+qoE3TnD7rroMG8d0d2/wERdPw7QQmRGaTjdius08aQZLKdD6gtOrnCCI0STSw32Q+UoHQP3dy8J5f4XC65qD3xe43yxs3aM9kIm8jRIVNvAbDv2IXpa2QDVPy57WnHQ25f1wBwIDAQAB";
		

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://www.zhihuiyingdi.com/weixin/top/order/savealiorder";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://www.zhihuiyingdi.com/weixin/top/order/alireturnurl";

		// 签名方式
		public static String sign_type = "RSA";
		
		// 签名方式
			public static String new_sign_type = "RSA2";
		
		
		// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
		public static String log_path = "D:\\";
			
		// 字符编码格式 目前支持utf-8
		public static String input_charset = "utf-8";
			
		// 支付类型 ，无需修改
		public static String payment_type = "1";
			
		// 调用的接口名，无需修改
		public static String service = "alipay.wap.create.direct.pay.by.user";

		// 请求网关地址
		public static String URL = "https://openapi.alipay.com/gateway.do";
		
		// 返回格式
		public static String FORMAT = "json";

}
