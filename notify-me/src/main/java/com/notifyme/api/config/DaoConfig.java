package com.notifyme.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.notifyme.api.dao.EventDao;
import com.notifyme.api.dao.EventDaoImpl;
import com.notifyme.api.dao.UserDao;
import com.notifyme.api.dao.UserDaoImpl;

@Configuration
public class DaoConfig implements WebMvcConfigurer {

	@Bean
	@Autowired
	public EventDao eventDao() {
		return new EventDaoImpl();
	}

	@Bean
	@Autowired
	public UserDao UserDao() {
		return new UserDaoImpl();
	}
}
