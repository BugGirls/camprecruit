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

import com.jeefw.model.sys.param.ProductInfoParameter;

/**
 * 入库记录——商品明细单
 * @author jzm
 *
 */
@Entity
@Table(name = "intoWarehouse_record_detail")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class IntoWarehouseRecordDatail extends ProductInfoParameter {


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
	private String product_no;//商品编码
	
	@Column(name = "seqence")
	private Long seqence;//序号
	
	@Column(name = "num")
	private Long num;//商品数
	
	@Column(name = "sale_price", length = 10 )
	private Float salePrice;//售价/单价
	
	@Column(name = "content", length = 255 )
	private String content;//备注
	
	@Column(name = "storage_location", length = 255 )
	private String storageLocation;//库存位置
	
	@Column(name = "productionDate")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date productionDate;//生产日期
	
	private ProductInfo productInfo;
	private IntoWarehouseRecord intoWarehouseRecord;
	
	public String getIntoWarehouseRecordNo() {
		return intoWarehouseRecordNo;
	}
	public void setIntoWarehouseRecordNo(String intoWarehouseRecordNo) {
		this.intoWarehouseRecordNo = intoWarehouseRecordNo;
	}
	public String getProduct_no() {
		return product_no;
	}
	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public IntoWarehouseRecordDatail() {
	}
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	public Long getSeqence() {
		return seqence;
	}
	public void setSeqence(Long seqence) {
		this.seqence = seqence;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public IntoWarehouseRecordDatail(Long id, String intoWarehouseRecordNo, String product_no, Long seqence, Long num,
			Float salePrice, String content, String storageLocation, Date productionDate, ProductInfo productInfo,
			IntoWarehouseRecord intoWarehouseRecord) {
		super();
		this.id = id;
		this.intoWarehouseRecordNo = intoWarehouseRecordNo;
		this.product_no = product_no;
		this.seqence = seqence;
		this.num = num;
		this.salePrice = salePrice;
		this.content = content;
		this.storageLocation = storageLocation;
		this.productionDate = productionDate;
		this.productInfo = productInfo;
		this.intoWarehouseRecord = intoWarehouseRecord;
	}
	
	
}
