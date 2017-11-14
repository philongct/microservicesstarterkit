package l.nguyen.ms.transaction;

import l.nguyen.security.config.oath2.OAuth2ResourceServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CreditCardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardTransactionApplication.class, args);
	}
}
