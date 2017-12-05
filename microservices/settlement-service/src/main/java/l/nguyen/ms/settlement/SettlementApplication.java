package l.nguyen.ms.settlement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import l.nguyen.ms.settlement.config.AutoScanConfig;
import l.nguyen.security.config.oath2.FeignOAuth2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

@SpringBootApplication(scanBasePackageClasses = AutoScanConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
public class SettlementApplication extends FeignOAuth2Config {

	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
		return formatterRegistry -> formatterRegistry.addFormatter(new Formatter<Date>() {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			@Override
			public Date parse(String text, Locale locale) throws ParseException {
				return formatter.parse(text);
			}

			@Override
			public String print(Date object, Locale locale) {
				return formatter.format(object);
			}
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(SettlementApplication.class, args);
	}
}
