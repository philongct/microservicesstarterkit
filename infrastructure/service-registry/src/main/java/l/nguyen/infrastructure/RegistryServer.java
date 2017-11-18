package l.nguyen.infrastructure;

import l.nguyen.security.config.basicweb.AbstractSecurityConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@SpringBootApplication
@EnableEurekaServer
public class RegistryServer extends AbstractSecurityConfigurer {

	@Override
	public void configure(WebSecurity web) throws Exception {
		// we don't want security now
		web.ignoring().antMatchers("/**");
	}

	public static void main(String[] args) {
		SpringApplication.run(RegistryServer.class, args);
	}
}
