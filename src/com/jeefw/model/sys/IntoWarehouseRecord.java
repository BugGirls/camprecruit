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

import com.jeefw.model.sys.param.IntoWarehouseRecordParameter;

/**
 * 入库记录单
 * @author jzm
 *
 */
@Entity
@Table(name = "intoWarehouse_record")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class IntoWarehouseRecord extends IntoWarehouseRecordParameter {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8337891286901469910L;

	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;//ID
	
	@Column(name = "no", length = 50)
	private String no;//入库单号---年月日+4位随机数
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;//制单时间

	@Column(name = "creater", length = 255 )
	private String creater;//制单人/操作人员
	
	@Column(name = "title", length = 255 )
	private String title;//标题
	
	@Column(name = "into_totalAmount", length = 10 )
	private Float intoTotalAmount;//进货总价
	
	@Column(name = "sale_totalAmount", length = 10 )
	private Float saleTotalAmount;//销售总价
	
	@Column(name = "into_num")
	private Long intoNum;//入库商品总数
	
	@Column(name = "into_typeNum")
	private Long intoTypeNum;//入库商品种类总数
	
	@Column(name = "content", length = 255 )
	private String content;//备注
	
	@Column(name = "supplier", length = 255 )
	private String supplier;//供应商名称
	
	@Column(name = "alliance_id")
	private Integer allianceId;//所属加盟商公司

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Long getIntoNum() {
		return intoNum;
	}

	public void setIntoNum(Long intoNum) {
		this.intoNum = intoNum;
	}

	public Long getIntoTypeNum() {
		return intoTypeNum;
	}

	public void setIntoTypeNum(Long intoTypeNum) {
		this.intoTypeNum = intoTypeNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(Integer allianceId) {
		this.allianceId = allianceId;
	}

	public IntoWarehouseRecord() {
	}

	public Float getIntoTotalAmount() {
		return intoTotalAmount;
	}

	public void setIntoTotalAmount(Float intoTotalAmount) {
		this.intoTotalAmount = intoTotalAmount;
	}

	public Float getSaleTotalAmount() {
		return saleTotalAmount;
	}

	public void setSaleTotalAmount(Float saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}

	public IntoWarehouseRecord(Long id, String no, Date createtime, String creater, String title, Float intoTotalAmount,
			Float saleTotalAmount, Long intoNum, Long intoTypeNum, String content, String supplier,
			Integer allianceId) {
		super();
		this.id = id;
		this.no = no;
		this.createtime = createtime;
		this.creater = creater;
		this.title = title;
		this.intoTotalAmount = intoTotalAmount;
		this.saleTotalAmount = saleTotalAmount;
		this.intoNum = intoNum;
		this.intoTypeNum = intoTypeNum;
		this.content = content;
		this.supplier = supplier;
		this.allianceId = allianceId;
	}

}
