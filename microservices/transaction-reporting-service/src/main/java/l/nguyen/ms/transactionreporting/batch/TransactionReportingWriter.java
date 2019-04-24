package l.nguyen.ms.transactionreporting.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.transactionreporting.model.SettlementBalance;
import l.nguyen.ms.transactionreporting.repository.SettlementRepository;

public class TransactionReportingWriter implements ItemStreamWriter<CreditCardTransaction> {

    private static final Logger logger = LoggerFactory.getLogger(TransactionReportingWriter.class);

    private SettlementRepository settlementRepository;

    private SettlementBalance balance;

    public TransactionReportingWriter(SettlementRepository settlementRepository, String bankId, Date date) {
        this.settlementRepository = settlementRepository;

        balance = new SettlementBalance();
        balance.setBankId(bankId);
        balance.setDate(date);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        settlementRepository.save(balance);
        logger.info("Done settlement report {}", balance);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    @Override
    public void write(List<? extends CreditCardTransaction> list) throws Exception {
        // No need synchronization because spring batch call write method in sequence
        for (CreditCardTransaction creditCardTransaction : list) {
            balance.setNumberOfTransactions(balance.getNumberOfTransactions() + 1);
            balance.setBalanceAmount(balance.getBalanceAmount() + creditCardTransaction.getMoneyAmount());
            // TODO: write to report file
        }
    }
}
