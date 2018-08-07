package com.jeefw.app.bean;

import java.util.HashMap;
import java.util.Map;

public class BackUpDb {
	/*
	 * 1 : 数据库备份。
	 * 2  ：
	 */
	private int  flage ;
	
	private boolean  isActive;

	private String content;
	
	private Map <String , Integer> fileProgress = new HashMap<String , Integer>();

	

	public Map<String, Integer> getFileProgress() {
		return fileProgress;
	}

	public void setFileProgress(Map<String, Integer> fileProgress) {
		this.fileProgress = fileProgress;
	}

	public int getFlage() {
		return flage;
	}

	public void setFlage(int flage) {
		this.flage = flage;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
