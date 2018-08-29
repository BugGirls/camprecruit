package com.jeefw.model.sys;

import javax.persistence.Column;

/**
 * 库存商品统计VO
 * @author jzm
 *
 */
public class ProductWarehouseCount {
	private String productTotalTypeNum;//商品种类总数
	private String productTotalNum;//商品总数
	private String adviceTotalAmount;//进价总额
	private String saleTotalAmount;//售价总额
	private Integer allianceId;//所属加盟商公司
	public String getProductTotalTypeNum() {
		return productTotalTypeNum;
	}
	public void setProductTotalTypeNum(String productTotalTypeNum) {
		this.productTotalTypeNum = productTotalTypeNum;
	}
	public String getProductTotalNum() {
		return productTotalNum;
	}
	public void setProductTotalNum(String productTotalNum) {
		this.productTotalNum = productTotalNum;
	}
	public String getAdviceTotalAmount() {
		return adviceTotalAmount;
	}
	public void setAdviceTotalAmount(String adviceTotalAmount) {
		this.adviceTotalAmount = adviceTotalAmount;
	}
	public String getSaleTotalAmount() {
		return saleTotalAmount;
	}
	public void setSaleTotalAmount(String saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}
	public Integer getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(Integer allianceId) {
		this.allianceId = allianceId;
	}
	public ProductWarehouseCount(String productTotalTypeNum, String productTotalNum, String adviceTotalAmount,
			String saleTotalAmount, Integer allianceId) {
		this.productTotalTypeNum = productTotalTypeNum;
		this.productTotalNum = productTotalNum;
		this.adviceTotalAmount = adviceTotalAmount;
		this.saleTotalAmount = saleTotalAmount;
		this.allianceId = allianceId;
	}
	public ProductWarehouseCount() {
	}
	
	
}
