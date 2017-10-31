package lnguyen.batch.impl;

import java.util.Map;

import lnguyen.batch.JobExecutionCallBack;
import lnguyen.batch.BatchJobManager;
import lnguyen.taskexecutor.executor.ExtendedTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;

public class BatchJobManagerImpl implements BatchJobManager {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobManagerImpl.class);

    private JobRepository jobRepo;
    private JobRegistry jobRegistry;
    private JobLauncher jobLauncher;

    private ExtendedTaskExecutor taskExecutor;

    public BatchJobManagerImpl(JobRegistry jobRegistry, JobRepository jobRepo, JobLauncher jobLauncher,
                               ExtendedTaskExecutor taskExecutor) {
        this.jobRegistry = jobRegistry;
        this.jobRepo = jobRepo;
        this.jobLauncher = jobLauncher;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void launchJob(String jobName, Map<String, JobParameter> params, JobExecutionCallBack executionCallBack) throws NoSuchJobException {
        final Job job = jobRegistry.getJob(jobName);
        taskExecutor.execute(() -> {
            // job launcher has default sync task executor, so it it block until job complete
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
            if (executionCallBack != null) {
                executionCallBack.completed(jobExecution);
            }
        }, e -> {
            JobExecution jobExecution = jobRepo.getLastJobExecution(jobName, new JobParameters(params));
            if (executionCallBack != null) {
                executionCallBack.error(jobExecution, e);
            } else {
                logger.error("Error with job execution. Last status {} ", jobExecution, e);
            }
        });

    }
}
