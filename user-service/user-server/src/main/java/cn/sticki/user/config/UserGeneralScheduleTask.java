package cn.sticki.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author durance
 */
@Slf4j
@Component
public class UserGeneralScheduleTask {

	/**
	 * 计算核对用户统计表数据
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	private void checkUserGeneralData() {
		log.info("执行定时任务检查user_general表数据");

	}

}
