package l.nguyen.ms.settlement.config;

import l.nguyen.security.config.basicweb.AbstractSecurityConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig extends AbstractSecurityConfigurer {

    @Override
    protected void configureAuthRequests(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
