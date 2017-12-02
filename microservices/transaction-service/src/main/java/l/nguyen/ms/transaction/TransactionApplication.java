package l.nguyen.ms.transaction;

import l.nguyen.security.config.oath2.OAuth2ResourceServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class TransactionApplication extends OAuth2ResourceServerConfig {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}

}
