/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package com.jiurong.scrum.security;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FrontUserMapper {

	FrontUser findByUsername(String username);

	int updateNickname(@Param("id") int id, @Param("nickname") String nickname);

	int updatePassword(@Param("id") int id, @Param("password") String password);
}
