/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application.web;

import org.ithedu.application.security.FrontUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated() && !AnonymousAuthenticationToken.class.isInstance(authentication)) {
			return true;
		}
		return false;
	}

	public static FrontUser getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		FrontUser user = (FrontUser)principal;
		return user;
	}


}
