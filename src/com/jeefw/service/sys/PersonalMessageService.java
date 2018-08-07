package com.jeefw.service.sys;

import com.jeefw.model.sys.PersonalMessage;

import core.service.Service;

/**
 * 个人消息的业务逻辑层的接口
 * @ 
 */
public interface PersonalMessageService extends Service<PersonalMessage> {

	void creditsExpireNotice();

	void couponExpireNotice();

	void prepareExpireNotice();
}
