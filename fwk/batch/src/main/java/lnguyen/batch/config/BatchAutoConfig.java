package lnguyen.batch.config;

import lnguyen.batch.BatchJobManager;
import lnguyen.batch.impl.BatchJobManagerImpl;
import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.ExtendedTaskExecutor;
import lnguyen.taskexecutor.executor.impl.ExtendedConcurrentTaskExecutor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableBatchProcessing(modular = true)
@PropertySource(value = "classpath:fwk/batch.properties")
public class BatchAutoConfig {
    @Bean
    @Lazy
    public BatchJobManager batchJobManager(JobRepository jobRepository, JobRegistry jobRegistry, JobLauncher jobLauncher) {
        ExtendedTaskExecutor taskExecutor = ExecutorUtils.newConcurrentTaskExecutor(4, 60, "batchjob");
        return new BatchJobManagerImpl(jobRegistry, jobRepository, jobLauncher, taskExecutor);
    }
}
