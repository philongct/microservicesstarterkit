package l.nguyen.ms.transactionreporting.batch;

import org.springframework.batch.item.ItemReader;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.transactionreporting.proxy.TransactionControllerClient;

public class TransactionReader implements ItemReader<CreditCardTransaction> {

    private TransactionControllerClient transactionClient;

    private String bankId;
    private Date date;

    private Iterator<CreditCardTransaction> transactionIt;
    private int lastAuthCode = 0;

    public TransactionReader(TransactionControllerClient transactionClient, String bankId, Date date) {
        this.transactionClient = transactionClient;
        this.date = date;
        this.bankId = bankId;
    }

    @Override
    public synchronized CreditCardTransaction read() throws Exception {
        if (transactionIt == null || !transactionIt.hasNext()) {
            transactionIt = newSegment();
            if (transactionIt == null)
                return null;
        }

        CreditCardTransaction cct = transactionIt.next();
        lastAuthCode = Math.max(lastAuthCode, cct.getAuthCode().getAuthCode());

        return cct;
    }

    private Iterator<CreditCardTransaction> newSegment() {
        List<CreditCardTransaction> transactionList = transactionClient
                .getTransactionsByAuthCodes(bankId, date, lastAuthCode + 1, TransactionReportingJobConfig.BATCH_SIZE);

        return transactionList.size() > 0 ? transactionList.iterator() : null;
    }
}
