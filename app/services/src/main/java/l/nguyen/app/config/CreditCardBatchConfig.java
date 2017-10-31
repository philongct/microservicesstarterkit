package l.nguyen.app.config;

import l.nguyen.app.batch.CreditCardImportJobConfig;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditCardBatchConfig {

    @Bean
    public ApplicationContextFactory creditCardJobContext() {
        return new GenericApplicationContextFactory(CreditCardImportJobConfig.class);
    }
}
