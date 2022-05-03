package cn.sticki.blog.service;

import cn.sticki.blog.pojo.dto.UserBlogActionStatusDTO;

import java.util.List;
import java.util.Map;

public interface BlogActionService {

	Map<Integer, UserBlogActionStatusDTO> getUserBlogActionStatus(Integer userId, List<Integer> blogIdList);

}
