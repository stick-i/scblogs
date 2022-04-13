package cn.sticki.blog.service;

import cn.sticki.blog.pojo.domain.User;

public interface UserService {

	/**
	 * 用户登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	User login(String username, String password);

	User getByUsername(String username);

	/**
	 * 移除用户
	 *
	 * @param username 用户名
	 */
	boolean removeByUsername(String username);

}
