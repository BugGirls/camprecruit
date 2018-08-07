package com.jeefw.model.sys.param;


 

import core.support.ExtJSBaseParameter; 

   /**
    * TzzJianjie  的参数类 
    * 2015/10/24 10:04:07  tudou
    */ 
public class TzzJianjieParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7534619189510265720L;

	private String $image;
	private int $eq_id;

	private String $content;
 
	private String contentNoHTML;


	public int get$eq_id() {
		return $eq_id;
	}
	public void set$eq_id(int $eq_id) {
		this.$eq_id = $eq_id;
	}
	public void set$image(String $image){
		this.$image=$image;
	}
	public String get$image(){
		return $image;
	}

	public void set$content(String $content){
		this.$content=$content;
	}
	public String get$content(){
		return $content;
	}
	public String getContentNoHTML() {
		return contentNoHTML;
	}
	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}
 
}

