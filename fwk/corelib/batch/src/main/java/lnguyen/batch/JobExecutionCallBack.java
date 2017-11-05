package lnguyen.batch;

import org.springframework.batch.core.JobExecution;

public interface JobExecutionCallBack {
    void completed(JobExecution jobExecution);

    void error(JobExecution jobExecution, Exception cause);
}
