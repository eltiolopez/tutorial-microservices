package com.jld.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableMethodSecurity
public class UserSecurity /*extends WebSecurityConfigurerAdapter*/ {
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequest().anyRequest().authenticated()
			.and()
			.oauth2ResourceServer().jwt();
	}
	*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer().jwt();
		return http.build();
	}
}
