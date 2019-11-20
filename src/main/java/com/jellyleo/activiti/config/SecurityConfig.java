package com.jellyleo.activiti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * 功能描述: Spring Security验证配置
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		// 禁用CSRF保护
		security.csrf().disable();
	}
}
