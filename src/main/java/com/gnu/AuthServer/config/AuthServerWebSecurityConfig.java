package com.gnu.AuthServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gnu.AuthServer.AuthInnerFilter;

@Configuration
@EnableWebSecurity
public class AuthServerWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(AuthServerWebSecurityConfig.class);
	final Marker REQUEST_MARKER = MarkerFactory.getMarker("HTTP_REQUEST");

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.inMemoryAuthentication()
		.withUser("username").password("p").authorities("read", "write"); // role과 authority의 차이는 앞에 prefix (ROLE_) 가 자동으로 붙냐 안 붙냐 차이 정도
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().disable().httpBasic().and().authorizeRequests()
		.antMatchers("/oauth/authorize").authenticated()
		.antMatchers("/oauth/token").authenticated()
		.anyRequest().authenticated().and()
		.csrf().disable()
		.addFilterBefore(new AuthInnerFilter(), BasicAuthenticationFilter.class);
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

}