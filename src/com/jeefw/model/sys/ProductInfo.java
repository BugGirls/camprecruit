package com.jeefw.model.sys;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jeefw.model.sys.param.ProductInfoParameter;

/**
 * 商品基础信息实体
 * @author jzm
 *
 */
@Entity
@Table(name = "productInfo")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ProductInfo extends ProductInfoParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7002242853693976074L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;//商品ID
	@Column(name = "name", length = 255)
	private String name;//商品名称
	@Column(name = "no", length = 50)
	private String no;//商品编码--年月日+随机随机4位
	@Column(name = "barCode", length = 255)
	private String barCode;//商品条形码
	@Column(name = "advice_price")
	private BigDecimal advicePrice;//建议零售价
	@Column(name = "bigCategoryNo", length = 255)
	private String bigCategoryNo;//商品大类--所属编码key
	@Column(name = "bigCategoryName", length = 255)
	private String bigCategoryName;//商品大类--所属编码value
	@Column(name = "smallCategoryNo", length = 255)
	private String smallCategoryNo;//商品小类--所属编码key
	@Column(name = "smallCategoryName", length = 255)
	private String smallCategoryName;//商品小类--所属编码value
	@Column(name = "productProperty_tempNo", length = 255)
	private String productPropertyTempNo;//商品规格--模板编码
	//商品图片
	@Column(name = "image", length = 255 )
	private String image;
	
	@Column(name = "image1", length = 255 )
	private String image1;
	
	@Column(name = "image2", length = 255 )
	private String image2;
	
	@Column(name = "image3", length = 255 )
	private String image3;
	@Column(name = "content", length = 500)
	private String content;//备注
	@Column(name = "brand", length = 255)
	private String brand;//品牌
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public BigDecimal getAdvicePrice() {
		return advicePrice;
	}
	public void setAdvicePrice(BigDecimal advicePrice) {
		this.advicePrice = advicePrice;
	}
	public String getBigCategoryNo() {
		return bigCategoryNo;
	}
	public void setBigCategoryNo(String bigCategoryNo) {
		this.bigCategoryNo = bigCategoryNo;
	}
	public String getSmallCategoryNo() {
		return smallCategoryNo;
	}
	public void setSmallCategoryNo(String smallCategoryNo) {
		this.smallCategoryNo = smallCategoryNo;
	}
	public String getProductPropertyTempNo() {
		return productPropertyTempNo;
	}
	public void setProductPropertyTempNo(String productPropertyTempNo) {
		this.productPropertyTempNo = productPropertyTempNo;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getBigCategoryName() {
		return bigCategoryName;
	}
	public void setBigCategoryName(String bigCategoryName) {
		this.bigCategoryName = bigCategoryName;
	}
	public String getSmallCategoryName() {
		return smallCategoryName;
	}
	public void setSmallCategoryName(String smallCategoryName) {
		this.smallCategoryName = smallCategoryName;
	}
	
	public ProductInfo(Long id, String name, String no, String barCode, BigDecimal advicePrice, String bigCategoryNo,
			String bigCategoryName, String smallCategoryNo, String smallCategoryName, String productPropertyTempNo,
			String image, String image1, String image2, String image3, String content, String brand) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.barCode = barCode;
		this.advicePrice = advicePrice;
		this.bigCategoryNo = bigCategoryNo;
		this.bigCategoryName = bigCategoryName;
		this.smallCategoryNo = smallCategoryNo;
		this.smallCategoryName = smallCategoryName;
		this.productPropertyTempNo = productPropertyTempNo;
		this.image = image;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.content = content;
		this.brand = brand;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public ProductInfo() {
	}
	
	
	
}
