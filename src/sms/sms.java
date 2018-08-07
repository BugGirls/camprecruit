
package sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import core.util.MD5;

 
 
public class sms {
 
	public static void main(String[] args) throws UnsupportedEncodingException {
	 // String urlString="http://admin.sms9.net/houtai/sms.php";
	  String cpid=" ";//短信用户名 
	  String smspwd = " ";//短信密码
	  
	  String channel = " ";//通道ID
	  String content="测试一下gee";//信息内容(每70个字为1条短信，系统自动拆分，汉字内容请使用gbk格式的urlencode编码形式)
	  content=URLEncoder.encode(content, "gbk");
	  String timestamp=String.valueOf(System.currentTimeMillis()/1000L);//当前的 UNIX 时间戳
	  String tele=" "; //电话号，多个号码用半角逗号分开，最多500个号码
	 // String sendtime="";//发送时间，无表示立即发送 
	  String MD5pwd=MD5.crypt(smspwd+"_"+timestamp+"_topsky"); 
	  System.out.println(timestamp);
	  	String httpUrl = "http://admin.sms9.net/houtai/sms.php";
	 	String httpArg = "cpid="+cpid+"&password="+MD5pwd+"&channelid="+channel+"&tele="+tele+"&msg="+content+"&timestamp="+timestamp;
	 	String jsonResult = request(httpUrl, httpArg); 
	 	System.out.println(jsonResult);
	  
		//返回结果：
		//如果发送成功，则返回：success:本次发送短信编号
		//如果发送失败，则返回：error:错误描述
		//错误描述:传递参数错误=-1 用户id或密码错误=-2 通道id错误=-3 手机号码错误=-4 短信内容错误=-5 余额不足错误=-6 绑定ip错误=-7 未带签名=-8 签名字数不对=-9 通道暂停=-10 该时间禁止发送=-11 时间戳错误=-12 编码异常=-13 发送被限制=-14(由于网关限制，同一个手机号不能反复发送过多短信，验证码一分钟只能下发一条一个小时三条)
		//发送短信示例： (请注意填写相应的channelid，上面红色字体已经标明可用的channelid)
		//http://admin.sms9.net/houtai/sms.php?cpid=13029&password=ae7e1d4d342cd875fe131bd1ef73cbd4&channelid=1&tele=13800138000,13101234567&msg=testcontent&timestamp=1444611765
	 
	}
	
	public static String sendsms(String phone,String pwd,String channel,String content,String tele) throws UnsupportedEncodingException{
		// String urlString="http://admin.sms9.net/houtai/sms.php"; 
		  content=URLEncoder.encode(content, "gbk");
		  String timestamp=String.valueOf(System.currentTimeMillis()/1000L);//当前的 UNIX 时间戳 
		 // String sendtime="";//发送时间，无表示立即发送 
		  String MD5pwd=MD5.crypt(pwd+"_"+timestamp+"_topsky"); 
		  System.out.println(timestamp);
		  	String httpUrl = "http://admin.sms9.net/houtai/sms.php";
		 	String httpArg = "cpid="+phone+"&password="+MD5pwd+"&channelid="+channel+"&tele="+tele+"&msg="+content+"&timestamp="+timestamp;
		 	String jsonResult = request(httpUrl, httpArg); 
		 //	System.out.println(jsonResult);
		
		return jsonResult;
	};
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 * @throws UnsupportedEncodingException 
	 */
	public static String request(String httpUrl, String httpArg) throws UnsupportedEncodingException {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg; 
	    try {
	        URL url = new URL(httpUrl);
	     
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET"); 
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "GBK")); 
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	        	sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}	
}

