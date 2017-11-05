package l.nguyen.security.jwt;

import l.nguyen.security.config.BasicJwtWebSecurityConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan("l.nguyen.security.controller")
@Import(BasicJwtWebSecurityConfigurer.class)
public class JwtTestConfig {
}
