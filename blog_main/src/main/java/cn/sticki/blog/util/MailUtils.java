package cn.sticki.blog.util;

import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.pojo.dto.MailDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service
public class MailUtils {

	@Resource
	private JavaMailSenderImpl mailSender;//注入邮件工具类

	@Async  // todo 此处异步有点问题
	public void sendMail(MailDTO mailDTO) throws MailSendException {
		checkMail(mailDTO);
		try {
			sendMimeMail(mailDTO);
		} catch (Exception e) {
			throw new MailSendException();
		}
	}

	//检测邮件信息类
	private void checkMail(@NotNull MailDTO mailDTO) throws RuntimeException {
		if (isEmpty(mailDTO.getTo())) {
			throw new RuntimeException("邮件收信人不能为空");
		}
		if (isEmpty(mailDTO.getSubject())) {
			throw new RuntimeException("邮件主题不能为空");
		}
		if (isEmpty(mailDTO.getText())) {
			throw new RuntimeException("邮件内容不能为空");
		}
	}

	//构建复杂邮件信息类
	private void sendMimeMail(MailDTO mailDTO) throws Exception {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
		messageHelper.setFrom(mailSender.getUsername() + '(' + mailDTO.getFrom() + ')');//邮件发信人
		messageHelper.setTo(mailDTO.getTo().split(","));//邮件收信人
		messageHelper.setSubject(mailDTO.getSubject());//邮件主题
		messageHelper.setText(mailDTO.getText());//邮件内容
		if (!isEmpty(mailDTO.getCc())) {//抄送
			messageHelper.setCc(mailDTO.getCc().split(","));
		}
		if (!isEmpty(mailDTO.getBcc())) {//密送
			messageHelper.setCc(mailDTO.getBcc().split(","));
		}
		if (mailDTO.getMultipartFiles() != null) {//添加邮件附件
			for (MultipartFile multipartFile : mailDTO.getMultipartFiles()) {
				messageHelper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
			}
		}
		if (isEmpty(mailDTO.getSentDate())) {
			mailDTO.setSentDate(new Date()); //发送时间
		}
		messageHelper.setSentDate(mailDTO.getSentDate());
		mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
	}

	//保存邮件
	private MailDTO saveMail(MailDTO mailDTO) {
		//将邮件保存到数据库..
		return mailDTO;
	}

	private boolean isEmpty(@Nullable Object str) {
		return (str == null || "".equals(str));
	}

}
