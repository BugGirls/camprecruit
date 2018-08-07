package core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import core.util.MD5;

public class Sms {

	public static String sendsms(String phone, String pwd, String channel,String content, String tele) throws UnsupportedEncodingException {
		content = URLEncoder.encode(content, "gbk");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);// 当前的  UNIX  时间戳
		// String sendtime="";//发送时间，无表示立即发送
		String MD5pwd = MD5.crypt(pwd + "_" + timestamp + "_topsky");
		//System.out.println(timestamp);
		String httpUrl = "http://admin.sms9.net/houtai/sms.php";
		String httpArg = "cpid=" + phone + "&password=" + MD5pwd + "&channelid=" + channel + "&tele=" + tele + "&msg=" + content + "&timestamp=" + timestamp;
		System.out.println(httpUrl+httpArg); 
		//System.out.println(tele);
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
	};

}
