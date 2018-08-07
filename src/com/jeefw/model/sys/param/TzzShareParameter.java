package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzShare  的参数类 
    * 2015/11/11 14:51:19  tudou
    */ 
public class TzzShareParameter extends  ExtJSBaseParameter{

	private String $name;

	private String $title;

	private String flvfile;

	private String pdffile;
	
	private String descript;
	
	private int $eq_userId;
	
	
	
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getFlvfile() {
		return flvfile;
	}
	public void setFlvfile(String flvfile) {
		this.flvfile = flvfile;
	}
	public String getPdffile() {
		return pdffile;
	}
	public void setPdffile(String pdffile) {
		this.pdffile = pdffile;
	}
	public int get$eq_userId() {
		return $eq_userId;
	}
	public void set$eq_userId(int $eq_userId) {
		this.$eq_userId = $eq_userId;
	}
	public void set$name(String $name){
		this.$name=$name;
	}
	public String get$name(){
		return $name;
	}

	public void set$title(String $title){
		this.$title=$title;
	}
	public String get$title(){
		return $title;
	}




}

