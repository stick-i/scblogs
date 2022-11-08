package cn.sticki.user.config;

import cn.sticki.blog.client.BlogClient;
import cn.sticki.blog.dto.BlogGeneralDTO;
import cn.sticki.user.mapper.UserGeneralMapper;
import cn.sticki.user.pojo.UserGeneral;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author durance
 */
@Slf4j
@Configuration
public class UserGeneralScheduleTask {

	@Resource
	private UserService userService;

	@Resource
	private BlogClient blogClient;

	@Resource
	private UserGeneralMapper userGeneralMapper;

	/**
	 * 新增和计算核对 用户统计表 各项数据
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	private void checkUserGeneralData() {
		log.info("执行定时任务检查user_general表数据");
		// 获取全部用户信息，进行统计
		List<UserGeneral> userGeneralList = userGeneralMapper.selectList(null);
		// 1.检查 user_general 表是否遗漏用户
		Set<Integer> userIdSet = new HashSet<>();
		// 将 general表中已有的用户id存入
		for (UserGeneral user : userGeneralList) {
			userIdSet.add(user.getUserId());
		}
		List<UserView> userList = userService.list();
		List<Integer> addUserList = new ArrayList<>();
		for (UserView userView : userList) {
			// 如果能够存入 set集合，说明此用户id并未在 general表中，则存入此项id
			if (userIdSet.add(userView.getId())) {
				addUserList.add(userView.getId());
			}
		}
		// 如果有未存入的用户，统一添加统计表数据
		if (addUserList.size() != 0) {
			userGeneralMapper.insertAllUser(addUserList.toArray(new Integer[0]));
		}
		// 2.检查表中各项数据正确性
		Integer[] userAllIds = new Integer[userList.size()];
		for (int i = 0; i < userAllIds.length; i++) {
			// 存入所有用户的 id
			userAllIds[i] = userList.get(i).getId();
		}
		// 进行查询每个用户发布的博客各项数据信息
		List<BlogGeneralDTO> userBlogData = blogClient.getBlogUserGeneral(userAllIds).getData();
		for (BlogGeneralDTO blogGeneralDTO : userBlogData) {
			UserGeneral userGeneral = new UserGeneral();
			BeanUtils.copyProperties(blogGeneralDTO, userGeneral);
			// 更新数据
			userGeneralMapper.update(userGeneral, new LambdaQueryWrapper<UserGeneral>().eq(UserGeneral::getUserId, blogGeneralDTO.getUserId()));
		}

	}

}
