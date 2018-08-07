package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * Emailphone  的参数类 
    * 2015/10/08 10:50:34  tudou
    */ 
public class EmailphoneParameter extends  ExtJSBaseParameter{


	private String $email;

	private String $smtp;

	private String $phone;

	private String $estate;

	private String $pstate; 


	public void set$email(String $email){
		this.$email=$email;
	}
	public String get$email(){
		return $email;
	}

	public void set$smtp(String $smtp){
		this.$smtp=$smtp;
	}
	public String get$smtp(){
		return $smtp;
	}

	public void set$phone(String $phone){
		this.$phone=$phone;
	}
	public String get$phone(){
		return $phone;
	}

	public void set$estate(String $estate){
		this.$estate=$estate;
	}
	public String get$estate(){
		return $estate;
	}

	public void set$pstate(String $pstate){
		this.$pstate=$pstate;
	}
	public String get$pstate(){
		return $pstate;
	}
 

}

