package l.nguyen.ms.transaction.service;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidatorService {

    // percent a transaction is rejected
    private static final float REJECT_RATE = 0.1f;

    private int accepted = 1;
    private int rejected = 0;

    public synchronized boolean accept(CreditCardTransaction creditCardTransaction) {
        if (isNull(creditCardTransaction))
            return false;

        float total = accepted + rejected;
        // Reject if we're still under reject rate
        if (rejected / total < REJECT_RATE) {
            ++rejected;
            return false;
        }

        ++accepted;
        return true;
    }

    private boolean isNull(CreditCardTransaction creditCardTransaction) {
        return creditCardTransaction.getTransactionDt() == null
                || creditCardTransaction.getCardNumber() == null
                || creditCardTransaction.getMoneyAmount() == null
                || creditCardTransaction.getPaymentReason() == null;
    }
}
