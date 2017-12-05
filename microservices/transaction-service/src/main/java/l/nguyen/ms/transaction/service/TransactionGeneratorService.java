package l.nguyen.ms.transaction.service;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import l.nguyen.ms.transaction.simulation.HttpRequester;
import l.nguyen.ms.transaction.simulation.SimulatedBankClient;
import l.nguyen.ms.transaction.simulation.SimulatedBankFactory;
import l.nguyen.ms.transaction.simulation.SimulatedTransactionFactory;
import lnguyen.taskexecutor.executor.ExecutorUtils;
import lnguyen.taskexecutor.executor.TaskExecutorService;
import okhttp3.OkHttpClient;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionGeneratorService.class);

    @Autowired
    private OkHttpClient httpClient;

    private TaskExecutorService bankClientExecutor;
    private TaskExecutorService requestExecutor;

    // -1 not running, 0 running, 1 done
    private int status = -1;

    private List<SimulatedBankClient> bankClients = new ArrayList<>();

    @PostConstruct
    public void init() {
        bankClientExecutor = ExecutorUtils.newConcurrentTaskExecutor(4, 10, "bankclient");
        requestExecutor = ExecutorUtils.newConcurrentTaskExecutor(8, 10, "requester");
    }

    public synchronized void startSimulation(String authHeader, int minTransactionPerBank, int maxTransactionPerBank) {
        if (status < 0) {
            status = 0;
            logger.info("Triggered with access_token {}", authHeader);

            HttpRequester requester = new HttpRequester(httpClient, authHeader);

            SimulatedBankFactory bankFactory = new SimulatedBankFactory();
            SimulatedTransactionFactory transactionFactory = new SimulatedTransactionFactory();

            int banks = Math.max(5, RandomUtils.nextInt(5) * 3);
            for (int i = 0; i < banks; ++i) {
                SimulatedBankClient bankClient = new SimulatedBankClient(
                        Math.max(minTransactionPerBank, RandomUtils.nextInt(maxTransactionPerBank/2) * 2),
                        bankFactory, transactionFactory, requester, requestExecutor);
                bankClients.add(bankClient);

                bankClientExecutor.execute(bankClient, null);
            }
        }
    }

    private int getRemained() {
        int val = 0;
        for (SimulatedBankClient bankClient : bankClients) {
            val += bankClient.getRemained();
        }

        if (val == 0)
            status = 1;

        return val;
    }

    public String getStatus() {
        switch (status) {
            case -1:
                return "Not run";
            case 0:
                return "Simulation is running: " + getRemained() + " remained";
            default:
                return "Simulation is done";
        }
    }
}
