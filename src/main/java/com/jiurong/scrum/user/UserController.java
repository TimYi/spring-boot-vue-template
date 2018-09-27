/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package com.jiurong.scrum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiurong.scrum.security.FrontUser;
import com.jiurong.scrum.security.FrontUserMapper;
import com.jiurong.scrum.web.AuthenticationUtils;

@RestController
public class UserController {

	@Autowired
	private FrontUserMapper frontUserMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value="user", method=RequestMethod.GET)
	public FrontUser userInfo() {
		return AuthenticationUtils.getCurrentUser();
	}

	@RequestMapping(value="user/nickname/{nickname}", method=RequestMethod.POST)
	public boolean nickname(@PathVariable("nickname") String nickname) {
		FrontUser currentUser = AuthenticationUtils.getCurrentUser();
		int count = frontUserMapper.updateNickname(currentUser.getId(), nickname);
		if(count > 0) {
			currentUser.setNickname(nickname);
		}
		return count > 0;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value="user/password/{password}", method=RequestMethod.POST)
	public boolean nickname(@PathVariable("password") String password, @RequestParam("oldPassword") String oldPassword) {
		FrontUser currentUser = AuthenticationUtils.getCurrentUser();
		currentUser = frontUserMapper.findByUsername(currentUser.getUsername());
		if(!currentUser.getPassword().equals(passwordEncoder.encodePassword(oldPassword, null))) {
			throw new RuntimeException("原密码错误");
		}
		String encryptedPassword = passwordEncoder.encodePassword(password, null);
		int count = frontUserMapper.updatePassword(currentUser.getId(), encryptedPassword);
		return count > 0;
	}
}
