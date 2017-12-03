package l.nguyen.ms.transaction.simulation;

import java.util.Date;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class SimulatedTransactionFactory {
    public CreditCardTransaction generateRandom() {
        return new CreditCardTransaction(RandomStringUtils.randomNumeric(16),
                RandomStringUtils.randomAlphabetic(20),
                RandomUtils.nextInt(10000) * 1.0,
                new Date());
    }
}
