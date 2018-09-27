package com.jiurong.scrum.config;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	ConfigurationCustomizer mybatisConfiguration() {
		return new ConfigurationCustomizer() {

			@Override
			public void customize(org.apache.ibatis.session.Configuration configuration) {
				configuration.setDefaultExecutorType(ExecutorType.REUSE);
				configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
				configuration.setMapUnderscoreToCamelCase(true);
				configuration.addInterceptor(new MyInterceptor());
			}
		};
	}
}
