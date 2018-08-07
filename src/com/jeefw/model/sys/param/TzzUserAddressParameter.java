package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzUserAddress  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzUserAddressParameter extends  ExtJSBaseParameter{

 

	private String $address;

	private String $state;
 
	public void set$address(String $address){
		this.$address=$address;
	}
	public String get$address(){
		return $address;
	}

	public void set$state(String $state){
		this.$state=$state;
	}
	public String get$state(){
		return $state;
	}

}

