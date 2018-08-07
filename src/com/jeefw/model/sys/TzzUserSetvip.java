package com.jeefw.model.sys;



import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import javax.persistence.Table; 

import org.codehaus.jackson.annotate.JsonIgnoreProperties; 
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzUserSetvipParameter;

   /**
    * TzzUserSetvip 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user_setvip" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserSetvip extends  TzzUserSetvipParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = -2484509814424543176L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 20 )
	private String name;

  
	@Column(name = "min_amount", length = 20 )
	private float minAmount;

	@Column(name = "discount", length = 3 )
	private float discount;


	public TzzUserSetvip(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

 

	public void setMinAmount(float minAmount){
		this.minAmount=minAmount;
	}
	public float getMinAmount(){
		return minAmount;
	}

	public void setDiscount(float discount){
		this.discount=discount;
	}
	public float getDiscount(){
		return discount;
	}

 

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzUserSetvip other = (TzzUserSetvip) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name) &&   Objects.equal(this.minAmount, other.minAmount) && Objects.equal(this.discount, other.discount);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.name,this.minAmount,this.discount);
	}
}

