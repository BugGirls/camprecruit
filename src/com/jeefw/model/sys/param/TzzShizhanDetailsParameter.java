package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzShizhanDetails  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzShizhanDetailsParameter extends  ExtJSBaseParameter{


	private int $tixikuId;

	private int $courseId;


	public void set$tixikuId(int $tixikuId){
		this.$tixikuId=$tixikuId;
	}
	public int get$tixikuId(){
		return $tixikuId;
	}

	public void set$courseId(int $courseId){
		this.$courseId=$courseId;
	}
	public int get$courseId(){
		return $courseId;
	}

}

