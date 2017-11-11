package l.nguyen.security.config;

import l.nguyen.security.support.TimeProvider;
import l.nguyen.security.support.jwt.JwtAuthenticationSuccessHandler;
import l.nguyen.security.support.jwt.JwtAuthenticationTokenFilter;
import l.nguyen.security.support.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class BasicJwtWebSecurityConfigurer extends AbstractSecurityConfigurer {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	@Override
	protected void configureAuthRequests(HttpSecurity http) throws Exception {
		super.configureAuthRequests(http);
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				// Use form login to create jwt token
				.formLogin()
					.successHandler(new JwtAuthenticationSuccessHandler(jwtTokenUtil()))
			.and()
				// Do not put security filter as bean otherwise Spring will treat it global filter
				// and it will filter twice
				.addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenUtil()), BasicAuthenticationFilter.class);
	}

	@Bean
	public JwtTokenUtil jwtTokenUtil() {
		return new JwtTokenUtil(timeProvider(), secret, expiration);
	}

	@Bean
	public TimeProvider timeProvider() {
		return new TimeProvider();
	}
}
