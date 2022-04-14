package cn.sticki.blog.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestTemplate {

	Integer code;
	String message;
	Object data;
	Boolean status;

	public RestTemplate() {
		this.code = 200;
		this.message = "success";
		this.data = null;
		this.status = true;
	}

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

	public RestTemplate(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
		this.status = false;
	}

}
