package cn.sticki.blog.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestTemplate {

	Integer code;
	String message;
	Object data;
	Boolean status;

	public RestTemplate(Object data) {
		this.code = 200;
		this.message = "success";
		this.data = data;
		this.status = true;
	}

	public RestTemplate(Boolean status) {
		this.code = 200;
		this.message = "success";
		this.data = null;
		this.status = status;
	}

	public RestTemplate(Boolean status, String message) {
		this.code = 200;
		this.message = message;
		this.data = null;
		this.status = status;
	}

}
