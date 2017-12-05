package l.nguyen.ms.settlement.batch;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.settlement.proxy.TransactionControllerClient;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BanksRetrieverTasklet implements Tasklet {

    public static final String BANKS_KEY = "bankIds";

    private TransactionControllerClient transactionClient;

    private List<String> bankIds;

    public BanksRetrieverTasklet(TransactionControllerClient transactionClient) {
        this.transactionClient = transactionClient;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        bankIds = transactionClient.getBankIds(new Date());
        return RepeatStatus.FINISHED;
    }

    @AfterStep
    public void saveRetrievedBanks(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put(BANKS_KEY, bankIds);
    }
}
