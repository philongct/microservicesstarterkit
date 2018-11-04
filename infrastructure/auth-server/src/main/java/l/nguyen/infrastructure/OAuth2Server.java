package l.nguyen.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import l.nguyen.security.config.basicweb.AbstractSecurityConfigurer;
import l.nguyen.security.config.oath2.OAuth2ServerConfig;

/*
   Copyright 2015 kennybastani, http://www.kennybastani.com/2015/07/spring-cloud-docker-microservices.html

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

@SpringBootApplication
public class OAuth2Server extends OAuth2ServerConfig {

	@Configuration
	@EnableWebMvc
	protected static class BasicWebMvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		}
	}

	@Configuration
	@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
	protected static class LoginConfiguration extends AbstractSecurityConfigurer {

		@Value("${uaa.server.context-path}")
		private String uaaServerContextPath;

		@Override
		protected void configureAuthRequests(HttpSecurity http) throws Exception {
			http
				// disable csrf to make thing simple
				.csrf().disable()
				.anonymous().disable()
				.httpBasic().disable()
				.formLogin().permitAll()
			.and()
				.authorizeRequests()
				.anyRequest()
					.authenticated();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web
				.ignoring()
					.antMatchers("/auth/**", "/fonts/**", "/js/**", "/css/**");
		}

		protected AuthenticationEntryPoint authEntryPoint() {
			return (request, response, exception) -> response.sendRedirect(uaaServerContextPath + "/auth/login.html");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(OAuth2Server.class, args);
	}
}
