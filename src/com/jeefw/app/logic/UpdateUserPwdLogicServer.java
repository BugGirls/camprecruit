package com.jeefw.app.logic;

import org.springframework.web.context.WebApplicationContext;

import com.jeefw.app.bean.UpdateUserPwdRequestBean;
import com.jeefw.app.bean.UpdateUserPwdResponseBean;
import com.jeefw.service.sys.SysUserService;

/**
 * APP接口的业务处理逻辑封装接口的实现类（根据用户名更改密码） 
 */
public class UpdateUserPwdLogicServer implements ILogicFace<UpdateUserPwdRequestBean, UpdateUserPwdResponseBean> {

	public UpdateUserPwdResponseBean logic(WebApplicationContext wac, UpdateUserPwdRequestBean brb) {
		//SysUserService userService = (SysUserService) wac.getBean("sysUserServiceImpl");
		//UpdateUserPwdResponseBean updateUserResponseBean = new UpdateUserPwdResponseBean();
		//updateUserResponseBean.setResult(userService.updateSysUser(brb));
		 return null;
	}

}
