package l.nguyen.ms.transactionreporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import l.nguyen.ms.transactionreporting.config.AutoScanConfig;
import l.nguyen.security.config.oath2.FeignOAuth2Config;

@SpringBootApplication(scanBasePackageClasses = AutoScanConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
public class TransactionReportingApplication extends FeignOAuth2Config {
	public static void main(String[] args) {
		SpringApplication.run(TransactionReportingApplication.class, args);
	}
}
