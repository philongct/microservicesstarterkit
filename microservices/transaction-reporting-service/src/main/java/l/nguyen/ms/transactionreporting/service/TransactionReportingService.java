package l.nguyen.ms.transactionreporting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import l.nguyen.ms.transactionreporting.proxy.TransactionControllerClientRx;
import lnguyen.batch.BatchJobManager;
import lnguyen.batch.JobExecutionCallBack;

@Component
public class TransactionReportingService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionReportingService.class);

    @Autowired
    private BatchJobManager batchJobManager;

    @Autowired
    private TransactionControllerClientRx transactionClientRx;

    private boolean running = false;

    public String getStatus() {
        if (running)
            return "Running";

        return "Not Running";
    }

    public synchronized String startReporting() {
        transactionClientRx.getBankIds(new Date()).subscribe(a -> {
            logger.info("\nRx call result {}", a);
            if (a.size() > 0) {
                startJob(a);
            }
        });

        return getStatus();
    }

    private synchronized void startJob(List<String> bankIds) {
        if (running)
            return;

        running = true;

        try {
            Map<String, JobParameter> jobParams = new HashMap<>();
            jobParams.put("banks", new JobParameter((long) bankIds.size()));
            jobParams.put("date", new JobParameter(new Date()));

            batchJobManager.launchJob("transactionReportingJob", jobParams,
                    new JobExecutionCallBack() {

                        @Override
                        public void completed(JobExecution jobExecution) {
                            running = false;
                        }

                        @Override
                        public void error(JobExecution jobExecution, Exception cause) {
                            running = false;
                        }
                    });
        } catch (NoSuchJobException e) {
            logger.error("Launching Job failed", e);
            running = false;
        }
    }
}
