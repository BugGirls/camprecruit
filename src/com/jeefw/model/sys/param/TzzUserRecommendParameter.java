package com.jeefw.model.sys.param;

import java.util.Date;

import core.support.ExtJSBaseParameter;

public class TzzUserRecommendParameter extends ExtJSBaseParameter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1195792233746134703L;
	
	public TzzUserRecommendParameter(int id,int userId,int promotionId,Date createTime,String userName,String promotionName){
//		r.id,r.user_id,r.promotion_id,r.create_time,u.nickname as userName,p.`name` as promotionName
		this.id = id;
		this.userId = userId;
		this.promotionId = promotionId;
		this.createTime = createTime;
		this.userName = userName;
		this.promotionName = promotionName;
	}

	private int id;

	private int userId;
	
	private String userName;

	private int promotionId;
	
	private String promotionName;

	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
