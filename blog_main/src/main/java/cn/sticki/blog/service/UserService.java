package cn.sticki.blog.service;

import cn.sticki.blog.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

	/**
	 * 用户登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	User login(String username, String password);

	User getByUsername(String username);

}
