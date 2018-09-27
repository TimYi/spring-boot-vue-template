/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package com.jiurong.scrum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jiurong.gycrm.security.BasicHttpAuthenticationFilter;
import com.jiurong.gycrm.security.RestAccessDeniedHandler;
import com.jiurong.gycrm.security.RestAuthenticationEntryPoint;
import com.jiurong.gycrm.security.RestAuthenticationFailHandler;
import com.jiurong.gycrm.security.RestLogoutSuccessHandler;
import com.jiurong.scrum.security.FrontAuthenticationSuccessHandler;
import com.jiurong.scrum.security.FrontUserDetailsService;

/**
 * 
 * @author ytm
 *
 */
@SuppressWarnings("deprecation")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
@Order()
public class FrontWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private FrontUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/regist/**", "/captcha/**", "/sms/**", "/enums/**", "/wechat/**", "/login/**")
		.permitAll()
		.anyRequest()
		.hasRole("USER")
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(new RestAuthenticationEntryPoint())
		.accessDeniedHandler(new RestAccessDeniedHandler())
		.and()
		.formLogin()
		.permitAll()
		.successHandler(new FrontAuthenticationSuccessHandler())
		.failureHandler(new RestAuthenticationFailHandler())
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessHandler(new RestLogoutSuccessHandler())
		.and()
		.csrf()
		.disable()
		.cors();

		AuthenticationManager authenticationManager = authenticationManagerBean();
		BasicHttpAuthenticationFilter filter = new BasicHttpAuthenticationFilter(authenticationManager);
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
