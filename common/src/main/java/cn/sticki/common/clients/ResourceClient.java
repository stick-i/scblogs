package cn.sticki.common.clients;

import cn.sticki.common.pojo.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "resource-server")
public interface ResourceClient {

	/**
	 * 上传图片接口
	 *
	 * @param file 图片
	 * @return 图片链接
	 */
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<String> uploadBlogImage(MultipartFile file);

	/**
	 * 上传头像接口
	 *
	 * @param file 头像图片文件
	 * @param name 图片命名
	 * @return 是否上传成功
	 */
	@PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<Boolean> uploadAvatarImage(@RequestBody MultipartFile file, @RequestParam String name);

}
