package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzShizhanFaq  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzShizhanFaqParameter extends  ExtJSBaseParameter{


	private int $pathId;

	private String $question;

	private String $answer;

	private Date $createTime;


	public void set$pathId(int $pathId){
		this.$pathId=$pathId;
	}
	public int get$pathId(){
		return $pathId;
	}

	public void set$question(String $question){
		this.$question=$question;
	}
	public String get$question(){
		return $question;
	}

	public void set$answer(String $answer){
		this.$answer=$answer;
	}
	public String get$answer(){
		return $answer;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

