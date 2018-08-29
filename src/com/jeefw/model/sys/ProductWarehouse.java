package com.jeefw.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jeefw.model.sys.param.ProductWarehouseParameter;

/**
 * 库存商品
 * @author jzm
 *
 */
@Entity
@Table(name = "product_warehouse")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ProductWarehouse extends ProductWarehouseParameter {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7202938252485082105L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;//ID
	
	// 各个字段的含义请查阅文档的数据库结构部分
	@Column(name = "intoWarehouseRecord_no", length = 50)
	private String intoWarehouseRecordNo;//所属入库单号
	
	@Column(name = "product_no", length = 50)
	private String productNo;//商品编码
	
	@Column(name = "product_name", length = 50)
	private String productName;//商品名称
	
	@Column(name = "product_barCode", length = 50)
	private String productBarCode;//商品条形码编码
	
	@Column(name = "num")
	private Long num;//商品数
	
	@Column(name = "sale_price", length = 10 )
	private Float salePrice;//售价/单价
	
	@Column(name = "advice_price", length = 10 )
	private Float advicePrice;//进价
	
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
	
	@Column(name = "storage_location", length = 255 )
	private String storageLocation;//库存位置
	
	@Column(name = "productionDate")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date productionDate;//生产日期
	
	@Column(name = "alliance_id")
	private String allianceId;//所属加盟商公司
	
	private ProductInfo productInfo;
	private IntoWarehouseRecord intoWarehouseRecord;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIntoWarehouseRecordNo() {
		return intoWarehouseRecordNo;
	}
	public void setIntoWarehouseRecordNo(String intoWarehouseRecordNo) {
		this.intoWarehouseRecordNo = intoWarehouseRecordNo;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBarCode() {
		return productBarCode;
	}
	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public Float getAdvicePrice() {
		return advicePrice;
	}
	public void setAdvicePrice(Float advicePrice) {
		this.advicePrice = advicePrice;
	}
	public String getBigCategoryNo() {
		return bigCategoryNo;
	}
	public void setBigCategoryNo(String bigCategoryNo) {
		this.bigCategoryNo = bigCategoryNo;
	}
	public String getBigCategoryName() {
		return bigCategoryName;
	}
	public void setBigCategoryName(String bigCategoryName) {
		this.bigCategoryName = bigCategoryName;
	}
	public String getSmallCategoryNo() {
		return smallCategoryNo;
	}
	public void setSmallCategoryNo(String smallCategoryNo) {
		this.smallCategoryNo = smallCategoryNo;
	}
	public String getSmallCategoryName() {
		return smallCategoryName;
	}
	public void setSmallCategoryName(String smallCategoryName) {
		this.smallCategoryName = smallCategoryName;
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
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	
	public String getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public IntoWarehouseRecord getIntoWarehouseRecord() {
		return intoWarehouseRecord;
	}
	public void setIntoWarehouseRecord(IntoWarehouseRecord intoWarehouseRecord) {
		this.intoWarehouseRecord = intoWarehouseRecord;
	}
	public ProductWarehouse(Long id, String intoWarehouseRecordNo, String productNo, String productName,
			String productBarCode, Long num, Float salePrice, Float advicePrice, String bigCategoryNo,
			String bigCategoryName, String smallCategoryNo, String smallCategoryName, String productPropertyTempNo,
			String image, String image1, String image2, String image3, String content, String brand,
			String storageLocation, Date productionDate, String allianceId, ProductInfo productInfo,
			IntoWarehouseRecord intoWarehouseRecord) {
		super();
		this.id = id;
		this.intoWarehouseRecordNo = intoWarehouseRecordNo;
		this.productNo = productNo;
		this.productName = productName;
		this.productBarCode = productBarCode;
		this.num = num;
		this.salePrice = salePrice;
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
		this.storageLocation = storageLocation;
		this.productionDate = productionDate;
		this.allianceId = allianceId;
		this.productInfo = productInfo;
		this.intoWarehouseRecord = intoWarehouseRecord;
	}
	public ProductWarehouse() {
		super();
	}
	
	
}
