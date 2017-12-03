package l.nguyen.ms.transaction.simulation;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class SimulatedBankFactory {

    private Set<String> generated = new TreeSet<>();

    public SimulatedBank generateRandom() {
        String bankId;
        do {
            bankId = RandomStringUtils.randomNumeric(16);
        } while (generated.contains(bankId));

        generated.add(bankId);
        return new SimulatedBank(bankId);
    }
}
