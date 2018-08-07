package com.jeefw.model.sys;

import java.util.List;

public class DbMetaDetail {
	
	private String columnName;
	
	private List <Object> columnValue;
	
	private String columnType;
	
	private String columnType1;
	
	public String getColumnType1() {
		return columnType1;
	}
	public void setColumnType1(String columnType1) {
		this.columnType1 = columnType1;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public List<Object> getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(List<Object> columnValue) {
		this.columnValue = columnValue;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	

}
