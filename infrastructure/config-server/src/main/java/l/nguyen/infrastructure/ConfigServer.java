package l.nguyen.infrastructure;

import l.nguyen.security.config.basicweb.AbstractSecurityConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;


/**
 * The {@link EnableConfigServer} annotation defines that this application will
 * serve as the REST based API for providing external configuration. <br>
 * <br>
 * 
 * The external repository from where the configuration will be picked up is
 * defined in the {application.yml} file.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer extends AbstractSecurityConfigurer {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // we don't want security now
        web.ignoring().antMatchers("/**");
    }

	public static void main(String[] args) {
		// force using native config
		System.setProperty("spring.profiles.active", "native");
		SpringApplication.run(ConfigServer.class, args);
	}
}
