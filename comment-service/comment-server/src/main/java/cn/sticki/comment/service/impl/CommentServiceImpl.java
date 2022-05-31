package cn.sticki.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.client.BlogClient;
import cn.sticki.blog.dto.BlogDTO;
import cn.sticki.comment.exception.CommentException;
import cn.sticki.comment.mapper.CommentMapper;
import cn.sticki.comment.mapper.CommentViewMapper;
import cn.sticki.comment.pojo.Comment;
import cn.sticki.comment.pojo.CommentListVO;
import cn.sticki.comment.pojo.CommentVO;
import cn.sticki.comment.pojo.CommentView;
import cn.sticki.comment.service.CommentService;
import cn.sticki.common.result.RestResult;
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
	private CommentViewMapper commentViewMapper;

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
		if (!exists) throw new CommentException("数据异常");
		comment.setId(null);
		comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		commentMapper.insert(comment);
		// todo 博客评论数增加
		// blogGeneralMapper.increaseCommentNum(comment.getId());
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
		LambdaQueryWrapper<CommentView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CommentView::getBlogId, blogId); // 只要博客id一致即可
		Long count = commentViewMapper.selectCount(wrapper);
		vo.setAllCount(count);
		if (count == 0) return vo; // 若总数为0，直接返回

		// 以时间排序，只查该博客下没有父评论id的，存入父评论组数中
		wrapper.orderByDesc(CommentView::getCreateTime).isNull(CommentView::getParentId);
		IPage<CommentView> iPage = new Page<>(page, pageSize);
		commentViewMapper.selectPage(iPage, wrapper);
		BeanUtil.copyProperties(iPage, vo, "records");
		// 复制父评论
		ArrayList<CommentVO> dtoList = new ArrayList<>();
		for (CommentView record : iPage.getRecords()) {
			CommentVO dto = new CommentVO();
			dto.setInfo(record);
			dtoList.add(dto);
		}
		vo.setRecords(dtoList);

		// 查询子评论
		// 先获取父评论的id列表
		ArrayList<Integer> idList = new ArrayList<>();
		for (CommentView CommentView : iPage.getRecords()) {
			idList.add(CommentView.getId());
		}
		// 以父评论id查询所有子评论，按时间排序
		wrapper.clear();
		wrapper.eq(CommentView::getBlogId, blogId)
				.in(CommentView::getParentId, idList)
				.orderByDesc(CommentView::getCreateTime);
		// 查询所有子评论
		List<CommentView> subList = commentViewMapper.selectList(wrapper);
		// 将子评论填充到父评论下
		for (CommentVO CommentVO : dtoList) {
			Integer parentId = CommentVO.getInfo().getId();
			ArrayList<CommentView> tempSub = new ArrayList<>();
			for (CommentView sub : subList) {
				if (Objects.equals(parentId, sub.getParentId()))
					tempSub.add(sub);
			}
			CommentVO.setSub(tempSub);
			CommentVO.setSubCount(tempSub.size());
		}

		return vo;
	}

}
