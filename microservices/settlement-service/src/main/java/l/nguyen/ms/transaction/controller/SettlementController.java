package l.nguyen.ms.transaction.controller;

import java.util.ArrayList;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.transaction.proxy.TransactionControllerClient;
import l.nguyen.ms.transaction.proxy.TransactionControllerClientRx;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettlementController {

	private static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

	@Autowired
	private TransactionControllerClientRx transactionClientRx;

	@Autowired
	private TransactionControllerClient transactionClient;

	@GetMapping("/{requestBank}")
	public List<CreditCardTransaction> getAllTransactions(@PathVariable String requestBank) {
		transactionClientRx.getGeneratedAuthCodes("afdfsf", 100, 300).subscribe(a -> {
			logger.info("\nRx call result {}", a);
		});

		List<CreditCardTransaction> transactions = new ArrayList<>();
		int start = RandomUtils.nextInt(100);
		transactionClient.getGeneratedAuthCodes(requestBank, start, start + 100).forEach(code -> {
			transactions.add(new CreditCardTransaction(requestBank, Integer.toString(code.getAuthCode()), -1.0, code.getGeneratedDt()));
		});

		return transactions;
	}
}
