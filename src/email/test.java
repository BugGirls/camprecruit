package email;

import com.jeefw.model.sys.Mail;

public class test {

	public static void main(String[] args) {
		 Mail mail=new Mail();
		 mail.setMessage("测试一下！！！！");
		 mail.setSubject("测试");
		 mail.setSender("18238816369@163.com");
		 mail.setRecipient("1217490399@qq.com");
		 
		 SendMail sendMail=new SendMail("smtp.163.com");
		 String a= sendMail.send(mail, "smtp.163.com", "hityrqazjvqziays");
		 System.out.println(a);
		 
		 
	}

}
