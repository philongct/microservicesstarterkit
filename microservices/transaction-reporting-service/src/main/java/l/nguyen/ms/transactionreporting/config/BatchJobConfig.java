package l.nguyen.ms.transactionreporting.config;

import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import l.nguyen.ms.transactionreporting.batch.TransactionReportingJobConfig;

@Configuration
public class BatchJobConfig {
    @Bean
    public ApplicationContextFactory transactionReportingBatchContext() {
        return new GenericApplicationContextFactory(TransactionReportingJobConfig.class);
    }
}
