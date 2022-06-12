package cn.sticki.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.client.BlogClient;
import cn.sticki.blog.dto.BlogDTO;
import cn.sticki.comment.exception.CommentException;
import cn.sticki.comment.mapper.CommentMapper;
import cn.sticki.comment.pojo.Comment;
import cn.sticki.comment.pojo.CommentBO;
import cn.sticki.comment.pojo.CommentListVO;
import cn.sticki.comment.pojo.CommentVO;
import cn.sticki.comment.service.CommentService;
import cn.sticki.common.result.RestResult;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 阿杆
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Resource
	private UserClient userClient;

	@Resource
	private BlogClient blogClient;

	@Override
	public void create(Comment comment) {
		// 检查博客是否存在，检查父评论id是否在该博客下
		boolean exists = false;
		if (comment.getParentId() != null) {
			// 判断博客id和父评论id是否正确
			LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Comment::getBlogId, comment.getBlogId()).eq(Comment::getId, comment.getParentId());
			Comment parentComment = commentMapper.selectOne(wrapper);
			if (parentComment != null) {
				exists = true;
				// 如果父评论还有上一级评论，则设置父评论的上一级评论为当前评论的父评论，如此，所有子评论的父评论id都一定是一级评论
				comment.setParentId(parentComment.getParentId() != null ? parentComment.getParentId() : parentComment.getId());
				comment.setParentUserId(parentComment.getUserId());
			}
		} else {
			// 根据id判断博客是否存在
			RestResult<BlogDTO> result = blogClient.getBlogInfo(comment.getBlogId());
			exists = result.getData() != null;
		}
		if (!exists) {
			throw new CommentException("数据异常");
		}
		comment.setId(null);
		comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		commentMapper.insert(comment);
		/*
			todo 博客评论数增加
			blogGeneralMapper.increaseCommentNum(comment.getId())
			博客服务已经拆分出去了，这里不能调用博客的数据库
		*/
		log.info("博客评论增加，blogId={},commentId={}", comment.getBlogId(), comment.getId());
	}

	@Override
	public void delete(int id) {
		commentMapper.deleteById(id);
		// todo 减少数量
		// blogGeneralMapper.decreaseCommentNum(id);
	}

	@Override
	public boolean checkPublisher(int userId, int commentId) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getUserId, userId).eq(Comment::getId, commentId);
		return commentMapper.exists(wrapper);
	}

	@Override
	public void checkAndDelete(int userId, int commentId) {
		if (!checkPublisher(userId, commentId)) {
			log.warn("非法删除评论，id->{},commentId->{}", userId, commentId);
			throw new CommentException("非法删除评论");
		} else {
			delete(commentId);
		}
	}

	@Override
	public CommentListVO getList(int blogId, int page, int pageSize) {
		CommentListVO vo = new CommentListVO();
		// 先查总数量
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		// 只要博客id一致即可
		wrapper.eq(Comment::getBlogId, blogId);
		Long count = commentMapper.selectCount(wrapper);
		vo.setAllCount(count);
		if (count == 0) {
			// 若总数为0，直接返回
			return vo;
		}

		// 以时间排序(也可以按id倒序)，只查该博客下没有父评论id的，存入父评论组数中
		wrapper.orderByDesc(Comment::getId).isNull(Comment::getParentId);
		IPage<Comment> iPage = new Page<>(page, pageSize);
		commentMapper.selectPage(iPage, wrapper);
		BeanUtil.copyProperties(iPage, vo, "records");
		// 收集有关评论的用户id，便于等会查询用户信息
		Set<Integer> userIdList = new HashSet<>();
		// 收集父评论id，用于查子评论
		List<Integer> parentIdList = new ArrayList<>();
		// 复制父评论
		List<CommentVO> dtoList = new ArrayList<>();
		for (Comment record : iPage.getRecords()) {
			CommentVO commentVO = new CommentVO();
			commentVO.setInfo(BeanUtil.copyProperties(record, CommentBO.class));
			dtoList.add(commentVO);
			// 不用获取parentId，因为它们必为null
			userIdList.add(record.getUserId());
			parentIdList.add(record.getId());
		}

		// 查询子评论
		// 以父评论id查询所有子评论，按时间排序(也可以按id倒序)
		wrapper.clear();
		wrapper.eq(Comment::getBlogId, blogId).in(Comment::getParentId, parentIdList).orderByDesc(Comment::getId);
		// 查询所有子评论
		log.debug("查询子评论sql,{}", wrapper.getSqlSelect());
		List<Comment> subList = commentMapper.selectList(wrapper);
		List<CommentBO> subListBO = new ArrayList<>();
		for (Comment comment : subList) {
			subListBO.add(BeanUtil.copyProperties(comment, CommentBO.class));
		}
		// 将子评论列表转为map list，便于填充到父评论下，顺便获取用户id列表
		Map<Integer, List<CommentBO>> listMap = new HashMap<>();
		for (CommentBO commentBO : subListBO) {
			if (!listMap.containsKey(commentBO.getParentId())) {
				listMap.put(commentBO.getParentId(), new ArrayList<>());
			}
			listMap.get(commentBO.getParentId()).add(commentBO);
			userIdList.add(commentBO.getUserId());
			userIdList.add(commentBO.getParentUserId());
		}

		// 将子评论填充到父评论下
		for (CommentVO commentVO : dtoList) {
			Integer parentId = commentVO.getInfo().getId();
			if (listMap.containsKey(parentId)) {
				commentVO.setSub(listMap.get(parentId));
				commentVO.setSubCount((long) commentVO.getSub().size());
			} else {
				commentVO.setSub(new ArrayList<>());
				commentVO.setSubCount(0L);
			}
		}

		// 获取并设置用户属性，垃圾代码。。。
		RestResult<Map<Integer, UserDTO>> result = userClient.getUserList(userIdList);
		if (result.getStatus()) {
			Map<Integer, UserDTO> userMap = result.getData();
			for (CommentVO commentVO : dtoList) {
				CommentBO info = commentVO.getInfo();
				info.setNickname(userMap.get(info.getUserId()).getNickname());
				info.setAvatarUrl(userMap.get(info.getUserId()).getAvatarUrl());
				if (commentVO.getSub() == null) {
					continue;
				}
				for (CommentBO sub : commentVO.getSub()) {
					sub.setNickname(userMap.get(sub.getUserId()).getNickname());
					sub.setAvatarUrl(userMap.get(sub.getUserId()).getAvatarUrl());
					sub.setParentNickname(userMap.get(sub.getParentUserId()).getNickname());
				}
			}
		}
		vo.setRecords(dtoList);
		return vo;
	}

}
