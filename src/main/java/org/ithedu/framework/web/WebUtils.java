/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.framework.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class WebUtils {

	public static void writeJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
	}
}
