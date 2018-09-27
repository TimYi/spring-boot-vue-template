/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package com.jiurong.scrum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED)
public class FrontUserDetailsService implements UserDetailsService {

	@Autowired
	private FrontUserMapper frontUserMapper;

	@Override
	public FrontUser loadUserByUsername(String username) throws UsernameNotFoundException {
		FrontUser user = frontUserMapper.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("no user is found by this username:" + username);
		}
		return user;
	}
}
