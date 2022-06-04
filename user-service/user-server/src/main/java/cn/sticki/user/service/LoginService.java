package cn.sticki.user.service;

import cn.sticki.user.pojo.UserLoginBO;

public interface LoginService {

	UserLoginBO login(String username, String password);

}
