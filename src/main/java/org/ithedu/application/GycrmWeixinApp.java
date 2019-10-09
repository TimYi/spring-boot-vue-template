/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(@PropertySource("classpath:server.properties"))
public class GycrmWeixinApp {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GycrmWeixinApp.class);
		application.run(args);
	}
}
