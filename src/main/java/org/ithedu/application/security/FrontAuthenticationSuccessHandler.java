/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ithedu.framework.web.WebUtils;
import org.ithedu.utils.SnakeJsonSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class FrontAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    FrontUser frontUser = (FrontUser) authentication.getPrincipal();
    WebUtils.writeJsonResponse(response, SnakeJsonSerializer.serialize(frontUser));
  }
}
