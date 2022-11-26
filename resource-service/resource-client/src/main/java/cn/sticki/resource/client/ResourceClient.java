package cn.sticki.resource.client;

import cn.sticki.common.result.RestResult;
import cn.sticki.resource.client.fuse.ResourceClientFuse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 阿杆
 */
@FeignClient(value = "resource-server", fallback = ResourceClientFuse.class, contextId = "ResourceClient")
public interface ResourceClient {

	/**
	 * 上传图片接口
	 *
	 * @param file 图片
	 * @return 图片链接
	 */
	@PostMapping(value = "/private/resource/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<String> uploadBlogImage(@RequestPart MultipartFile file);

	/**
	 * 上传头像接口
	 *
	 * @param file 头像图片文件
	 * @param name 图片命名
	 * @return 是否上传成功
	 */
	@PostMapping(value = "/private/resource/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<String> uploadAvatarImage(@RequestPart MultipartFile file, @RequestParam String name);

	/**
	 * 通过院校代码获取名称
	 *
	 * @param schoolCode 院校代码
	 * @return 高校名称
	 */
	@GetMapping("/resource/university/name")
	RestResult<String> getUniversityName(@RequestParam Integer schoolCode);

}
