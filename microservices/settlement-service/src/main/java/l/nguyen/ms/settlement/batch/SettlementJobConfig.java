package l.nguyen.ms.settlement.batch;

import java.util.Date;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.settlement.proxy.TransactionControllerClient;
import l.nguyen.ms.settlement.repository.SettlementRepository;
import lnguyen.taskexecutor.executor.ExecutorUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class SettlementJobConfig {

    public static final int BATCH_SIZE = 100;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private JobRepository jobRepo;

    @Bean
    public Job settlementJob() {
        return this.jobFactory.get("settlementJob")
                .start(banksRetrievalStep(null, null))
                .next(settlementStep())
                .build();
    }

    @Bean
    @JobScope
    public Step banksRetrievalStep(TransactionControllerClient transactionClient,
                                   @Value("#{jobParameters[date]}") Date date) {
        // Use ContextPromotionListener to promote result from BanksRetrieverTasklet to JobContext
        ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
        promotionListener.setKeys(new String[] { BanksRetrieverTasklet.BANKS_KEY });

        BanksRetrieverTasklet banksRetriever = new BanksRetrieverTasklet(transactionClient, date);
        return stepFactory.get("banksRetrieverStep")
                .listener(promotionListener)
                .tasklet(banksRetriever)
                .listener(banksRetriever)
                .build();
    }

    @Bean
    @JobScope
    public Step settlementStep() {
        BankSettlementPartitioner partitioner = new BankSettlementPartitioner();
        return stepFactory.get("settlementStep")
                .listener(partitioner)
                .partitioner("settlementStep", partitioner)
                .taskExecutor(partitionedTaskExecutor())
                .step(settlementSlaveStep())
                .build();
    }

    @Bean
//    @JobScope doesn't work for slave step (BATCH-2269)
//    @JobScope
    public Step settlementSlaveStep() {
        return stepFactory.get("settlementSlaveStep").<CreditCardTransaction, CreditCardTransaction>chunk(BATCH_SIZE)
                .reader(transactionReader(null, null, null))
                .writer(transactionWriter(null, null,null))
                .build();
    }

    @Bean
    @StepScope
    public TransactionReader transactionReader(TransactionControllerClient transactionClient,
                                               @Value("#{jobParameters[date]}") Date date,
                                               @Value("#{stepExecutionContext[" + BankSettlementPartitioner.KEY_NAME + "]}")
                                               String bankId) {
        return new TransactionReader(transactionClient, bankId, date);
    }

    @Bean
    @StepScope
    public SettlementWriter transactionWriter(SettlementRepository settlementRepository,
                                              @Value("#{jobParameters[date]}") Date date,
                                              @Value("#{stepExecutionContext[" + BankSettlementPartitioner.KEY_NAME + "]}") String bankId) {
        return new SettlementWriter(settlementRepository, bankId, date);
    }

    @Bean
    public TaskExecutor partitionedTaskExecutor() {
        return ExecutorUtils.newConcurrentTaskExecutor(2, 60, "settlement");
    }
}
