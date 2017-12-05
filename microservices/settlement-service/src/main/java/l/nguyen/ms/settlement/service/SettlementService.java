package l.nguyen.ms.settlement.service;

import java.util.*;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.settlement.proxy.TransactionControllerClientRx;
import lnguyen.batch.BatchJobManager;
import lnguyen.batch.JobExecutionCallBack;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettlementService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementService.class);

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

    public synchronized String startSettlement() {
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

            batchJobManager.launchJob("settlementJob", jobParams,
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
