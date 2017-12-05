package l.nguyen.ms.settlement.config;

import l.nguyen.ms.settlement.batch.SettlementJobConfig;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobConfig {
    @Bean
    public ApplicationContextFactory settlementBatchContext() {
        return new GenericApplicationContextFactory(SettlementJobConfig.class);
    }
}
