/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application.config;

import org.ithedu.application.security.FrontAuthenticationSuccessHandler;
import org.ithedu.application.security.FrontUserDetailsService;
import org.ithedu.framework.security.BasicHttpAuthenticationFilter;
import org.ithedu.framework.security.RestAccessDeniedHandler;
import org.ithedu.framework.security.RestAuthenticationEntryPoint;
import org.ithedu.framework.security.RestAuthenticationFailHandler;
import org.ithedu.framework.security.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** @author ytm */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@Order()
public class FrontWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  @Autowired private FrontUserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean(name = "authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
