package com.jeefw.app.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeefw.app.bean.BaseResponseBean;

/**
 * APP接口的协议传输接口 
 */
public interface ITransmission {

	public String resv(HttpServletRequest request);

	public void resp(HttpServletResponse response, BaseResponseBean brb);

}
