package l.nguyen.ms.transaction.simulation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import lnguyen.taskexecutor.executor.TaskExecutorService;
import lnguyen.taskexecutor.task.Task;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulatedBankClient implements Task {

    private static final Logger logger = LoggerFactory.getLogger(SimulatedBankClient.class);

    private SimulatedBank simulatedBank;
    private SimulatedTransactionFactory transactionFactory;

    private HttpRequester requester;
    private TaskExecutorService taskExecutorService;

    private AtomicInteger limit;

    public SimulatedBankClient(int limit,
                               SimulatedBankFactory bankFactory,
                               SimulatedTransactionFactory transactionFactory,
                               HttpRequester httpRequester,
                               TaskExecutorService taskExecutorService) {
        this.limit = new AtomicInteger(limit);
        this.simulatedBank = bankFactory.generateRandom();
        this.transactionFactory = transactionFactory;

        this.requester = httpRequester;
        this.taskExecutorService = taskExecutorService;
    }

    private void requestAuthCode(CreditCardTransaction cct, CountDownLatch latch) {
        Function<Object, Void> callback = (response) -> {
            limit.decrementAndGet();
            latch.countDown();
            return null;
        };

        try {
            requester.request(simulatedBank.getBankId(), cct, callback);
        } catch (Exception e) {
            callback.apply(null);
            logger.error("Error when requesting at {}: {}", limit.get(), cct);
        }
    }

    @Override
    public void run() throws Exception {
        int batchSize = Math.min(limit.get(), 100);
        while (limit.get() > 0) {
            CountDownLatch countDown = new CountDownLatch(batchSize);
            for (int i = 0; i < batchSize; ++i) {
                requestAuthCode(transactionFactory.generateRandom(), countDown);
            }

            countDown.await();
        }
    }

    public int getRemained() {
        return limit.get();
    }
}
