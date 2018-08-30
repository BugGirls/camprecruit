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

import com.jeefw.model.sys.param.ProductShelfParameter;

/**
 * 货架商品
 * @author jzm
 *
 */
@Entity
@Table(name = "product_shelf")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ProductShelf extends ProductShelfParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;//ID

	@Column(name = "alliance_id")
	private String allianceId;//所属加盟商公司
	
	// 各个字段的含义请查阅文档的数据库结构部分
	@Column(name = "intoWarehouseRecord_no", length = 50)
	private String intoWarehouseRecordNo;//所属入库单号
	
	@Column(name = "productWarehouseId", length = 50)
	private String productWarehouseId;//仓库商品ID
	
	@Column(name = "onShelf_no", length = 50)
	private String onShelfNo;//所属上架单号
	
	@Column(name = "product_no", length = 50)
	private String productNo;//商品编码
	
	@Column(name = "product_name", length = 50)
	private String productName;//商品名称

	/**
	 * 商品副标题
	 */
	@Column(name = "product_sub_title", length = 255)
	private String productSubTitle;
	
	@Column(name = "product_barCode", length = 50)
	private String productBarCode;//商品条形码编码
	
	@Column(name = "num")
	private Long num;//商品数
	
	@Column(name = "sale_price", length = 10 )
	private Float salePrice;//售价/单价
	
	@Column(name = "into_price", length = 10 )
	private Float intoPrice;//进价
	
	@Column(name = "advice_price", length = 10 )
	private Float advicePrice;//建议零售价
	
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
	
	@Column(name = "shelf_location", length = 255 )
	private String shelfLocation;//货架位置
	
	@Column(name = "productionDate")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date productionDate;//生产日期
	
	@Column(name = "groundDate")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date groundDate;//上架日期
	
	@Column(name = "creater", length = 255 )
	private String creater;//操作人员
	
	@Column(name = "createrNo", length = 255 )
	private String createrNo;//操作人员工号

	/**
	 * 商品销量
	 */
	@Column(name = "sales_volume")
	private Integer salesVolume;

	private ProductInfo productInfo;

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

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

	public String getProductSubTitle() {
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle) {
		this.productSubTitle = productSubTitle;
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

	public Float getIntoPrice() {
		return intoPrice;
	}

	public void setIntoPrice(Float intoPrice) {
		this.intoPrice = intoPrice;
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

	public String getShelfLocation() {
		return shelfLocation;
	}

	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public Date getGroundDate() {
		return groundDate;
	}

	public void setGroundDate(Date groundDate) {
		this.groundDate = groundDate;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterNo() {
		return createrNo;
	}

	public void setCreaterNo(String createrNo) {
		this.createrNo = createrNo;
	}

	public Float getAdvicePrice() {
		return advicePrice;
	}

	public void setAdvicePrice(Float advicePrice) {
		this.advicePrice = advicePrice;
	}

	public String getOnShelfNo() {
		return onShelfNo;
	}

	public void setOnShelfNo(String onShelfNo) {
		this.onShelfNo = onShelfNo;
	}

	public ProductShelf() {
	}

	public String getProductWarehouseId() {
		return productWarehouseId;
	}

	public void setProductWarehouseId(String productWarehouseId) {
		this.productWarehouseId = productWarehouseId;
	}

	public String getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}

	public ProductShelf(Long id, String intoWarehouseRecordNo, String productWarehouseId, String onShelfNo,
			String productNo, String productName, String productBarCode, Long num, Float salePrice, Float intoPrice,
			Float advicePrice, String bigCategoryNo, String bigCategoryName, String smallCategoryNo,
			String smallCategoryName, String productPropertyTempNo, String image, String image1, String image2,
			String image3, String content, String brand, String shelfLocation, Date productionDate, String allianceId,
			Date groundDate, String creater, String createrNo, ProductInfo productInfo) {
		this.id = id;
		this.intoWarehouseRecordNo = intoWarehouseRecordNo;
		this.productWarehouseId = productWarehouseId;
		this.onShelfNo = onShelfNo;
		this.productNo = productNo;
		this.productName = productName;
		this.productBarCode = productBarCode;
		this.num = num;
		this.salePrice = salePrice;
		this.intoPrice = intoPrice;
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
		this.shelfLocation = shelfLocation;
		this.productionDate = productionDate;
		this.allianceId = allianceId;
		this.groundDate = groundDate;
		this.creater = creater;
		this.createrNo = createrNo;
		this.productInfo = productInfo;
	}
	
	
}
