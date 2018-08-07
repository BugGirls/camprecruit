package sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

public class SingleSendSms {

	 public static void smsSend(String phone,String modeid,String param) {        
	        try {
	        IClientProfile profile = DefaultProfile.getProfile("cn-qingdao", "LTAIiL3nLmMzSuIR", "Q8oLL7wTjmhK4y3z4erWSJbAiP1FLx");
	         DefaultProfile.addEndpoint("cn-qingdao", "cn-qingdao", "Sms",  "sms.aliyuncs.com");
	        IAcsClient client = new DefaultAcsClient(profile);
	        SingleSendSmsRequest request = new SingleSendSmsRequest();
	            request.setSignName("冒险家");//控制台创建的签名名称
	             request.setTemplateCode(modeid);//控制台创建的模板CODE
	            //request.setParamString("{\"name\":\"123\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
	            request.setParamString(param);
	            request.setRecNum(phone);//接收号码
	            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
	        } catch (ServerException e) {
	            e.printStackTrace();
	        }
	        catch (ClientException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void  main(String args[]) {
		 smsSend("18703996021","SMS_56665270","{\"code\":\"123\",\"product\":\"亲子淘\"}");
	}
	
}
