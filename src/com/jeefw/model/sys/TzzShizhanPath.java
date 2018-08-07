package com.jeefw.model.sys;


import java.util.Date;

import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import javax.persistence.Table; 

import org.codehaus.jackson.annotate.JsonIgnoreProperties; 
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzShizhanPathParameter;

   /**
    * TzzShizhanPath 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_shizhan_path" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzShizhanPath extends  TzzShizhanPathParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "title", length = 155 )
	private String title;

	@Column(name = "top_image", length = 255 )
	private String topImage;

	@Column(name = "image", length = 255 )
	private String image;

	@Column(name = "sketch", length = 255 )
	private String sketch;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "video_href", length = 255 )
	private String videoHref;

	@Column(name = "create_time", length = 19 )
	private Date createTime;


	public TzzShizhanPath(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}

	public void setTopImage(String topImage){
		this.topImage=topImage;
	}
	public String getTopImage(){
		return topImage;
	}

	public void setImage(String image){
		this.image=image;
	}
	public String getImage(){
		return image;
	}

	public void setSketch(String sketch){
		this.sketch=sketch;
	}
	public String getSketch(){
		return sketch;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setVideoHref(String videoHref){
		this.videoHref=videoHref;
	}
	public String getVideoHref(){
		return videoHref;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}

 

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzShizhanPath other = (TzzShizhanPath) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.title, other.title) && Objects.equal(this.topImage, other.topImage) && Objects.equal(this.image, other.image) && Objects.equal(this.sketch, other.sketch) && Objects.equal(this.content, other.content) && Objects.equal(this.videoHref, other.videoHref) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.title,this.topImage,this.image,this.sketch,this.content,this.videoHref,this.createTime);
	}
}

