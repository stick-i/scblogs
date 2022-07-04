package cn.sticki.common.tool.mybatisconfig;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 阿杆
 */
@Configuration
public class MybatisPlusDefaultConfig {

	@Bean
	public MybatisPlusInterceptor mybatisPlusDefaultInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 配置分页助手
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		// 阻止恶意的全表更新删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}

}
