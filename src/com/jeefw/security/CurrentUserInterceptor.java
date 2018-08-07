package com.jeefw.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeefw.model.sys.SysUser;
import com.jeefw.service.sys.SysUserService;

/**
 * A Spring MVC interceptor that adds the currentUser into the request as a request attribute before the JSP is rendered. This operation is assumed to be fast because the User should be cached in the Hibernate
 * second-level cache.
 */
@Component
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private SysUserService sysUserService;

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		// Add the current user into the request
		final Long currentUserId = (Long) SecurityUtils.getSubject().getPrincipal();
		SysUser currentUser = sysUserService.get(currentUserId);
		if (currentUser != null) {
			httpServletRequest.setAttribute("currentUser", currentUser);
		}
	}

}
