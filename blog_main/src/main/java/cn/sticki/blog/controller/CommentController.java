package cn.sticki.blog.controller;

import cn.sticki.blog.exception.userException.UserArgumentException;
import cn.sticki.blog.pojo.domain.Comment;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.CommentListVO;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private AuthenticationFacade authenticationFacade;

	@Resource
	private CommentService commentService;

	/**
	 * 新增评论
	 *
	 * @param comment 必须传入被评论的博客id和评论内容
	 */
	@PostMapping
	public RestTemplate createComment(@RequestBody Comment comment) {
		if (comment.getBlogId() == null || comment.getContent() == null)
			throw new UserArgumentException();
		User user = authenticationFacade.getUser();
		comment.setUserId(user.getId());
		commentService.create(comment);
		return new RestTemplate();
	}

	/**
	 * 删除评论
	 *
	 * @param id 评论id
	 */
	@DeleteMapping
	public RestTemplate deleteComment(@RequestParam Integer id) {
		User user = authenticationFacade.getUser();
		commentService.checkAndDelete(user.getId(), id);
		return new RestTemplate();
	}

	/**
	 * 获取评论列表
	 *
	 * @param blogId 评论的博客id
	 */
	@GetMapping("/list")
	public RestTemplate getCommentList(@RequestParam Integer blogId, @RequestParam Integer page, @RequestParam(defaultValue = "3") Integer pageSize) {
		CommentListVO commentListVO = commentService.getList(blogId, page, pageSize);
		return new RestTemplate(commentListVO);
	}

}
