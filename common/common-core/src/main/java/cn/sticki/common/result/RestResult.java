package cn.sticki.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿杆
 */
@Data
@AllArgsConstructor
public class RestResult<T> implements Serializable {

	/**
	 * 状态码
	 */
	Integer code;

	/**
	 * 响应信息
	 */
	String message;

	/**
	 * 响应数据
	 */
	T data;

	/**
	 * 请求状态，操作成功为true，否则为false
	 */
	Boolean status;

	public RestResult() {
		this.code = 200;
		this.message = "success";
		this.data = null;
		this.status = true;
	}

	public RestResult(T data) {
		this.code = 200;
		this.message = "success";
		this.data = data;
		this.status = true;
	}

	public RestResult(Boolean status) {
		this.code = 200;
		this.message = "success";
		this.data = null;
		this.status = status;
	}

	public RestResult(Boolean status, String message) {
		this.code = 200;
		this.message = message;
		this.data = null;
		this.status = status;
	}

	public RestResult(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
		this.status = false;
	}

	public static <T> RestResult<T> ok() {
		return new RestResult<>();
	}

	public static <T> RestResult<T> ok(T data) {
		return new RestResult<>(200, "success", data, true);
	}

	public static <T> RestResult<T> fail() {
		return new RestResult<>(400, "fail", null, false);
	}

	public static <T> RestResult<T> fail(String errorMessage) {
		return new RestResult<>(400, errorMessage, null, false);
	}

}
