package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.exception.userException.UserIllegalException;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.mapper.CommentBasicMapper;
import cn.sticki.blog.mapper.CommentMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.Comment;
import cn.sticki.blog.pojo.domain.CommentBasic;
import cn.sticki.blog.pojo.dto.CommentDTO;
import cn.sticki.blog.pojo.vo.CommentListVO;
import cn.sticki.blog.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private CommentBasicMapper commentBasicMapper;

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
				// 为了防止有歧义难以理解，故改写成了上面这句
				// if (parentComment.getParentId() != null) comment.setParentId(parentComment.getParentId());
				comment.setParentUserId(parentComment.getUserId());
			}
		} else {
			// 判断博客id是否存在
			LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Blog::getId, comment.getBlogId());
			exists = blogMapper.exists(wrapper);
		}
		if (!exists) throw new UserIllegalException("数据异常");
		comment.setId(null);
		comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		commentMapper.insert(comment);
		blogGeneralMapper.increaseCommentNum(comment.getId());
	}

	@Override
	public void delete(int id) {
		int count = commentMapper.deleteById(id);
		if (count == 0)
			log.warn("comment 删除异常，id->{}", id);
		else {
			// 减少数量
			blogGeneralMapper.decreaseCommentNum(id);
		}
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
			throw new UserIllegalException();
		} else {
			delete(commentId);
		}
	}

	@Override
	public CommentListVO getList(int blogId, int page, int pageSize) {
		CommentListVO vo = new CommentListVO();
		// 先查总数量
		LambdaQueryWrapper<CommentBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CommentBasic::getBlogId, blogId); // 只要博客id一致即可
		Long count = commentBasicMapper.selectCount(wrapper);
		vo.setAllCount(count);
		if (count == 0) return vo; // 若总数为0，直接返回

		// 以时间排序，只查该博客下没有父评论id的，存入父评论组数中
		wrapper.orderByDesc(CommentBasic::getCreateTime).isNull(CommentBasic::getParentId);
		IPage<CommentBasic> iPage = new Page<>(page, pageSize);
		commentBasicMapper.selectPage(iPage, wrapper);
		BeanUtil.copyProperties(iPage, vo, "records");
		// 复制父评论
		ArrayList<CommentDTO> dtoList = new ArrayList<>();
		for (CommentBasic record : iPage.getRecords()) {
			CommentDTO dto = new CommentDTO();
			dto.setInfo(record);
			dtoList.add(dto);
		}
		vo.setRecords(dtoList);

		// 查询子评论
		// 先获取父评论的id列表
		ArrayList<Integer> idList = new ArrayList<>();
		for (CommentBasic commentBasic : iPage.getRecords()) {
			idList.add(commentBasic.getId());
		}
		// 以父评论id查询所有子评论，按时间排序
		wrapper.clear();
		wrapper.eq(CommentBasic::getBlogId, blogId)
				.in(CommentBasic::getParentId, idList)
				.orderByDesc(CommentBasic::getCreateTime);
		// 查询所有子评论
		List<CommentBasic> subList = commentBasicMapper.selectList(wrapper);
		// 将子评论填充到父评论下
		for (CommentDTO commentDTO : dtoList) {
			Integer parentId = commentDTO.getInfo().getId();
			ArrayList<CommentBasic> tempSub = new ArrayList<>();
			for (CommentBasic sub : subList) {
				if (Objects.equals(parentId, sub.getParentId()))
					tempSub.add(sub);
			}
			commentDTO.setSub(tempSub);
			commentDTO.setSubCount(tempSub.size());
		}

		return vo;
	}

}
