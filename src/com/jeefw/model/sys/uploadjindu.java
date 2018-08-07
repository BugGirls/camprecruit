package com.jeefw.model.sys;

 
import com.jeefw.model.sys.param.uploadjinduParameter;

   /**
    * TzzUserUpload 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
 public class uploadjindu extends  uploadjinduParameter{

	 private String name;
	 private int jindu;
	 
	 private int status;
	 
	 
	 private String content;
	 
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getJindu() {
		return jindu;
	}
	public void setJindu(int jindu) {
		this.jindu = jindu;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	 
}

