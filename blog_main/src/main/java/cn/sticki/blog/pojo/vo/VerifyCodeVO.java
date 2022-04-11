package cn.sticki.blog.pojo.vo;

import lombok.Data;

@Data
public class VerifyCodeVO {

	String verifyCode;

	String receiver;

	Long sendTime;

}
