package lnguyen.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableBatchProcessing(modular = true)
@PropertySource(value = "classpath:fwk/batch.properties")
public class BatchAutoConfig {


}
