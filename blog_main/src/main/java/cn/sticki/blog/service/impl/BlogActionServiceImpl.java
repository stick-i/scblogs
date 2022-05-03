package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.UserCollectBlogMapper;
import cn.sticki.blog.mapper.UserLikeBlogMapper;
import cn.sticki.blog.pojo.dto.UserBlogActionStatusDTO;
import cn.sticki.blog.service.BlogActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BlogActionServiceImpl implements BlogActionService {

	@Resource
	private UserLikeBlogMapper likeBlogMapper;

	@Resource
	private UserCollectBlogMapper collectBlogMapper;

	@Override
	public Map<Integer, UserBlogActionStatusDTO> getUserBlogActionStatus(Integer userId, List<Integer> blogIdList) {
		Map<Integer, Integer> likeMap = likeBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIdList);
		Map<Integer, Integer> collectMap = collectBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIdList);
		HashMap<Integer, UserBlogActionStatusDTO> map = new HashMap<>();
		for (Integer blogId : blogIdList) {
			UserBlogActionStatusDTO statusDTO = new UserBlogActionStatusDTO();
			statusDTO.setLike(likeMap.containsKey(blogId));
			statusDTO.setCollect(collectMap.containsKey(blogId));
			map.put(blogId, statusDTO);
		}
		return map;
	}

}
