package cn.sticki.user.service.impl;

import cn.sticki.common.tool.utils.RandomUtils;
import cn.sticki.message.client.MessageClient;
import cn.sticki.message.pojo.MailDTO;
import cn.sticki.user.config.UserConfig;
import cn.sticki.user.exception.UserException;
import cn.sticki.user.mapper.UserMapper;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserRegisterBO;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.service.RegisterService;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author 阿杆
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterServiceImpl extends ServiceImpl<UserSafetyMapper, UserSafety> implements RegisterService {

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private MessageClient messageClient;

	@Resource
	private UserMapper userMapper;

	@CreateCache(name = "user:register:mailVerifyCode:", expire = 300)
	private Cache<String, String> cache;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public void sendMailVerify(String mailAddress) {
		// 先判断该邮箱是否已经被注册，若已经被注册，则直接返回提示
		if (userSafetyMapper.selectByMail(mailAddress) != null) {
			throw new UserException("该邮箱已被注册");
		}
		String code = RandomUtils.generator(6, "0123456789");
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(mailAddress);
		mailDTO.setSubject("博客校园注册验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在注册博客校园，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		cache.put(mailAddress, code, 300, TimeUnit.SECONDS);
		// 发送邮件return
		messageClient.sendMail(mailDTO);
	}

	@Override
	public boolean register(UserRegisterBO userRegisterBO) {
		// 判断一下手机号，因为暂时没有核实手机号码的验证码，所以就先将就一下。
		LambdaQueryWrapper<UserSafety> wrapper = new LambdaQueryWrapper<>();
		String mobile = userRegisterBO.getMobile();
		wrapper.eq(UserSafety::getMobile, mobile);
		if (mobile == null || userSafetyMapper.exists(wrapper)) {
			throw new UserException("该手机号已被注册");
		}
		User user = new User();
		user.setSchoolCode(userRegisterBO.getSchoolCode());
		user.setUsername(userRegisterBO.getUsername());
		user.setNickname(userRegisterBO.getUsername());
		user.setAvatarUrl(UserConfig.DefaultAvatar);
		user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		if (userMapper.insert(user) > 0) {
			log.info("新用户注册：id->{}", user.getId());
			UserSafety userSafety = new UserSafety();
			BeanUtils.copyProperties(userRegisterBO, userSafety);
			userSafety.setUserId(user.getId());
			// 密码加密后再存入数据库
			userSafety.setPassword(passwordEncoder.encode(userRegisterBO.getPassword()));
			if (userSafetyMapper.insert(userSafety) > 0) {
				return true;
			} else {
				log.info("注册失败，操作回滚");
				throw new UserException("注册失败，数据异常");
			}
		}
		return false;
	}

	@Override
	public boolean checkMailVerify(@NotNull String mailAddress, @NotNull String code) {
		// 此处读取缓存中的数据，并在读取成功之后从缓存中删除
		String verify = cache.get(mailAddress);
		return code.equals(verify);
		// if (code.equals(verify)) {
		// 	cache.remove(mailAddress);
		// 	return true;
		// }
		// return false;
	}

}
