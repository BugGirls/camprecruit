package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzUserEvaluation  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzUserEvaluationParameter extends  ExtJSBaseParameter{


	private int $userId;

	private int $eq_productId;

	private String userName;
	
	private String userPicturePath;
	
	public String getUserPicturePath() {
		return userPicturePath;
	}
	public void setUserPicturePath(String userPicturePath) {
		this.userPicturePath = userPicturePath;
	}
	public void set$userId(int $userId){
		this.$userId=$userId;
	}
	public int get$userId(){
		return $userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int get$eq_productId() {
		return $eq_productId;
	}
	public void set$eq_productId(int $eq_productId) {
		this.$eq_productId = $eq_productId;
	}

}

