package l.nguyen.ms.transactionreporting.batch;

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

import java.util.Date;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.transactionreporting.proxy.TransactionControllerClient;
import l.nguyen.ms.transactionreporting.repository.SettlementRepository;
import lnguyen.taskexecutor.executor.ExecutorUtils;

@Configuration
public class TransactionReportingJobConfig {

    public static final int BATCH_SIZE = 100;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private JobRepository jobRepo;

    @Bean
    public Job transactionReportingJob() {
        return this.jobFactory.get("transactionReportingJob")
                .start(banksRetrievalStep(null, null))
                .next(transactionReportingStep())
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
    public Step transactionReportingStep() {
        BankPartitioner partitioner = new BankPartitioner();
        return stepFactory.get("transactionReportingStep")
                .listener(partitioner)
                .partitioner("transactionReportingStep", partitioner)
                .taskExecutor(partitionedTaskExecutor())
                .step(transactionReportingSlaveStep())
                .build();
    }

    @Bean
//    @JobScope doesn't work for slave step (BATCH-2269)
//    @JobScope
    public Step transactionReportingSlaveStep() {
        return stepFactory.get("transactionReportingSlaveStep").<CreditCardTransaction, CreditCardTransaction>chunk(BATCH_SIZE)
                .reader(transactionReader(null, null, null))
                .writer(transactionWriter(null, null,null))
                .build();
    }

    @Bean
    @StepScope
    public TransactionReader transactionReader(TransactionControllerClient transactionClient,
                                               @Value("#{jobParameters[date]}") Date date,
                                               @Value("#{stepExecutionContext[" + BankPartitioner.KEY_NAME + "]}")
                                               String bankId) {
        return new TransactionReader(transactionClient, bankId, date);
    }

    @Bean
    @StepScope
    public TransactionReportingWriter transactionWriter(SettlementRepository settlementRepository,
                                                        @Value("#{jobParameters[date]}") Date date,
                                                        @Value("#{stepExecutionContext[" + BankPartitioner.KEY_NAME + "]}") String bankId) {
        return new TransactionReportingWriter(settlementRepository, bankId, date);
    }

    @Bean
    public TaskExecutor partitionedTaskExecutor() {
        return ExecutorUtils.newConcurrentTaskExecutor(2, 60, "settlement");
    }
}
