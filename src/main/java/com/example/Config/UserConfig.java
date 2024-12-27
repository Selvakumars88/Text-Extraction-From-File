package com.example.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.UserPojo.UserResueme;

@Configuration
public class UserConfig {
	@Bean
public UserResueme userConfigaration() {
	return new UserResueme(); 
}
}
