/*
 * Copyright 2016-2018 the original author or authors.
 *
 * All right reserved by JiurongTech
 */

package org.ithedu.application.user;

import org.ithedu.application.security.FrontUser;
import org.ithedu.application.security.FrontUserMapper;
import org.ithedu.application.web.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired private FrontUserMapper frontUserMapper;
  @Autowired private PasswordEncoder passwordEncoder;

  @RequestMapping(value = "user", method = RequestMethod.GET)
  public FrontUser userInfo() {
    return AuthenticationUtils.getCurrentUser();
  }

  @RequestMapping(value = "user/nickname/{nickname}", method = RequestMethod.POST)
  public boolean nickname(@PathVariable("nickname") String nickname) {
    FrontUser currentUser = AuthenticationUtils.getCurrentUser();
    int count = frontUserMapper.updateNickname(currentUser.getId(), nickname);
    if (count > 0) {
      currentUser.setNickname(nickname);
    }
    return count > 0;
  }

  @RequestMapping(value = "user/password/{password}", method = RequestMethod.POST)
  public boolean nickname(
      @PathVariable("password") String password, @RequestParam("oldPassword") String oldPassword) {
    FrontUser currentUser = AuthenticationUtils.getCurrentUser();
    currentUser = frontUserMapper.findByUsername(currentUser.getUsername());
    if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
      throw new RuntimeException("原密码错误");
    }
    String encryptedPassword = passwordEncoder.encode(password);
    int count = frontUserMapper.updatePassword(currentUser.getId(), encryptedPassword);
    return count > 0;
  }
}
