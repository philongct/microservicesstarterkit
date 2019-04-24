package l.nguyen.ms.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;

public interface TransactionControllerApi {

	@GetMapping(value = "/banks/{transactionDate}")
	List<String> getBankIds(@PathVariable("transactionDate") Date transactionDate);

	@GetMapping(value = "/credits/{bankId}/{transactionDate}/{fromAuthCode}/{limit}")
	List<CreditCardTransaction> getTransactionsByAuthCodes(@PathVariable("bankId") String bankId,
                                                          @PathVariable("transactionDate") Date transactionDate,
                                                          @PathVariable("fromAuthCode") int fromAuthCode,
                                                          @PathVariable("limit") int limit);
}
