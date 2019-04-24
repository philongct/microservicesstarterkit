package l.nguyen.ms.transactionreporting.batch;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankPartitioner implements Partitioner {

    public static final String KEY_NAME = "bankid";

    private List<String> bankIds;

    @BeforeStep
    public void getBankIds(StepExecution stepExecution) {
        bankIds = (List<String>) stepExecution
                    .getJobExecution()
                        .getExecutionContext()
                            .get(BanksRetrieverTasklet.BANKS_KEY);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap<>(gridSize);
        int i = 0;
        for (String bankId : bankIds) {
            ExecutionContext context = new ExecutionContext();
            context.put(KEY_NAME, bankId);
            map.put("partition_" + (++i), context);
        }

        return map;
    }
}
