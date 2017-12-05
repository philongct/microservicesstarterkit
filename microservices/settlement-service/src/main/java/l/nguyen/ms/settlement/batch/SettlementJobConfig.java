package l.nguyen.ms.settlement.batch;

import l.nguyen.ms.settlement.proxy.TransactionControllerClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SettlementJobConfig {

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private JobRepository jobRepo;

    @Bean
    @JobScope
    public Step banksRetrievalStep(TransactionControllerClient transactionClient) {
        // Use ContextPromotionListener to promote result from BanksRetrieverTasklet to JobContext
        ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
        promotionListener.setKeys(new String[] { BanksRetrieverTasklet.BANKS_KEY });

        BanksRetrieverTasklet banksRetriever = new BanksRetrieverTasklet(transactionClient);
        return stepFactory.get("banksRetrieverStep")
                .listener(promotionListener)
                .tasklet(banksRetriever)
                .listener(banksRetriever)
                .build();
    }

    @Bean
    public Job settlementJob() {
        return this.jobFactory.get("settlementJob").start(banksRetrievalStep(null)).build();
    }
}
