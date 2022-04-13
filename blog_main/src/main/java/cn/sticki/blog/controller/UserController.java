package cn.sticki.blog.controller;

import cn.sticki.blog.exception.SystemException;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.MinioUtils;
import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private HttpSession session;

	@Resource
	private UserService userService;

	@Resource
	private MinioUtils minioUtils;

	/**
	 * 获取当前用户信息
	 */
	@GetMapping
	public RestTemplate get() {
		return new RestTemplate(session.getAttribute("user"));
	}

	/**
	 * 获取用户信息
	 *
	 * @param username 用户名
	 */
	@GetMapping({"/{username}"})
	public RestTemplate getByUsername(@PathVariable String username) {
		return new RestTemplate(userService.getByUsername(username));
	}

	/**
	 * 更新用户信息
	 */
	@PutMapping
	public RestTemplate update(User user) {
		// userService.update(user);
		return new RestTemplate();
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping
	public RestTemplate delete() {
		User user = (User) session.getAttribute("user");
		return new RestTemplate(user != null && userService.removeByUsername(user.getUsername()));
	}

	/**
	 * 更新头像
	 *
	 * @param multipartFile 文件流
	 */
	@PostMapping("/avatar")
	public RestTemplate updateAvatar(MultipartFile multipartFile) throws SystemException {
		log.debug("post avatar, fileName->{}", multipartFile.getOriginalFilename());
		minioUtils.upload(multipartFile);
		return new RestTemplate();
	}

	@GetMapping("/avatar")
	public void getAvatar(String fileName, HttpServletResponse httpResponse) throws SystemException, IOException {
		log.debug("get avatar, fileName->{}", fileName);
		InputStream stream = minioUtils.download(fileName);
		InputStream ism = new BufferedInputStream(stream);
		// 调用statObject()来判断对象是否存在。
		// 如果不存在, statObject()抛出异常,
		// 否则则代表对象存在
		byte buf[] = new byte[981146];
		int length = 0;
		httpResponse.reset();
		//Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
		// Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
		// 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
		// httpResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		httpResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		httpResponse.setContentType("application/x-msdownload");
		httpResponse.setCharacterEncoding("utf-8");
		OutputStream osm = new BufferedOutputStream(httpResponse.getOutputStream());
		ism.read(buf);
		// while ((length = ism.read(buf))>0) {
		// 	osm.write(buf,0, length);
		// }
		//关闭流
		osm.close();

		//
		// byte[] temp = new byte[981146];
		// stream.read(temp);
		// stream.close();
		// HttpHeaders httpHeaders = new HttpHeaders();
		// httpHeaders.set("Content-Disposition", "attachment;filename=" + fileName);
		// return new ResponseEntity<byte[]>(temp, httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/download")
	public void MinioDownload(String fileName, HttpServletResponse response) {
		log.debug("download, fileName->{}", fileName);
		try {
			//response.setContentType(stat.contentType());
			GetObjectResponse getObjectResponse = (GetObjectResponse) minioUtils.download(fileName);
			// minioClient.getObject(
			// 		GetObjectArgs.builder().bucket(fileInfo.getBucket()).object(fileId).build());
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			IOUtils.copy(getObjectResponse, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
