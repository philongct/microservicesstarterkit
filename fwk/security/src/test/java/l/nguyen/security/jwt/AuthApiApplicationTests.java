package l.nguyen.security.jwt;

import l.nguyen.security.config.BasicJwtWebSecurityConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthApiApplicationTests.Config.class, BasicJwtWebSecurityConfigurer.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthApiApplicationTests {

	@Configuration
	@EnableAutoConfiguration
	@ComponentScan("l.nguyen.security.controller")
	public static class Config {
	}

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() throws Exception {
//		HttpResponse<JsonNode> res = Unirest.get(getUrl("/api/hello"))
//				.header("accept", MediaType.APPLICATION_JSON_VALUE)
//				.asJson();
//		System.out.println(res.getStatusText());
	}

	private String getUrl(String resource) {
		return "http://127.0.0.1:" + port + resource;
	}
}
