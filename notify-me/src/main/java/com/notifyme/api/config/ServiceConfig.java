package com.notifyme.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.notifyme.api.dao.EventDao;
import com.notifyme.api.dao.UserDao;
import com.notifyme.api.service.EventService;
import com.notifyme.api.service.EventServiceImpl;
import com.notifyme.api.service.UserService;
import com.notifyme.api.service.UserServiceImpl;

@Configuration
public class ServiceConfig implements WebMvcConfigurer {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EventDao eventDao;
	
	@Bean
	@Autowired
	public UserService userService(UserDao userDao) {
		return new UserServiceImpl(userDao);
	}
	
	@Bean
	@Autowired
	public EventService eventService(EventDao eventDao) {
		return new EventServiceImpl(eventDao);
	}
	
}
