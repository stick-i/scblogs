package cn.sticki.user.service;

import cn.sticki.user.pojo.UserView;

public interface LoginService {

	UserView login(String username, String password);

}
