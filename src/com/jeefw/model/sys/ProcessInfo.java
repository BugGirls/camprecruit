package com.jeefw.model.sys;

public class ProcessInfo {
	
	
	private int itemNum;
	
	private long readSize;
	
	private long totalSize;
	
	private String show;
	
	private  float rate ;

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public long getReadSize() {
		return readSize;
	}

	public void setReadSize(long readSize) {
		this.readSize = readSize;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	

}
