/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Charsets;

public class BasicHttpAuthenticationFilter extends OncePerRequestFilter {

	private static final String HeaderName = "Authorization";

	private AuthenticationManager authenticationManager;
	private RestAuthenticationFailHandler failHandler = new RestAuthenticationFailHandler();

	public BasicHttpAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest req = request;
		HttpServletResponse resp = response;
		String authentication = req.getHeader(HeaderName);
		if (authentication != null && authentication.startsWith("Basic")) {
			String credentials = authentication.substring(6, authentication.length());
			credentials = new String(Base64.getDecoder().decode(credentials), Charsets.UTF_8);
			String[] arr = credentials.split(":");
			String username = arr[0];
			String password = arr[1];
			Authentication authResult;
			try {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

				authResult = this.authenticationManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(authResult);
			} catch (InternalAuthenticationServiceException failed) {
				logger.error("An internal error occurred while trying to authenticate the user.", failed);
				failHandler.onAuthenticationFailure(req, resp, failed);
				return;
			} catch (AuthenticationException failed) {
				failHandler.onAuthenticationFailure(req, resp, failed);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

}
