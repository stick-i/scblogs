package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.MailDTO;
import cn.sticki.blog.service.RegisterService;
import cn.sticki.blog.util.MailUtils;
import cn.sticki.blog.util.RandomUtils;
import cn.sticki.blog.util.RedisUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Slf4j
@Service
@Transactional
public class RegisterServiceImpl extends ServiceImpl<UserSafetyMapper, UserSafety> implements RegisterService {

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private MailUtils mailUtils;

	@Resource
	private RandomUtils randomUtils;

	@Resource
	private RedisUtils redisUtils;

	@Override
	public boolean register(UserSafety userSafety) {
		User user = new User();
		user.setUsername(userSafety.getUsername());
		user.setNickname(userSafety.getUsername());
		user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		if (userMapper.insert(user) > 0) {
			log.debug("新用户注册：id->{}", user.getId());
			userSafety.setUserId(user.getId());
			if (userSafetyMapper.insert(userSafety) > 0) return true;
			else {
				log.debug("注册失败，操作回滚");
				userMapper.deleteById(user);
				throw new RuntimeException("该手机号已被注册");
			}
		}
		return false;
	}

	@Override
	public String sendMailVerify(String mailAddress) throws Exception {
		// 先判断该邮箱是否已经被注册，若已经被注册，则直接返回提示
		if (userSafetyMapper.selectByMail(mailAddress) != null) throw new UserException("该邮箱已被注册");
		String code = randomUtils.generator(6, "0123456789");
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(mailAddress);
		mailDTO.setSubject("博客校园注册验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在注册博客校园，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		redisUtils.setex("blog:sendMailVerify:" + mailAddress, 300, code);
		mailUtils.sendMail(mailDTO);  // 发送邮件
		return code;
	}

	@Override
	public boolean checkMailVerify(@NotNull String mailAddress, @NotNull String code) {
		// 此处读取缓存中的数据，并在读取成功之后从缓存中删除
		String key = "blog:sendMailVerify:" + mailAddress;
		String verify = redisUtils.get(key);
		if (code.equals(verify)) {
			redisUtils.del(key);
			return true;
		}
		return false;
	}

}
