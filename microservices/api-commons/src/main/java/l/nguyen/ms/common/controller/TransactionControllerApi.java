package l.nguyen.ms.common.controller;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface TransactionControllerApi {

	@GetMapping(value = "/banks/{transactionDate}")
	public List<String> getBankIds(@PathVariable("transactionDate") Date transactionDate);

	@GetMapping(value = "/credits/{bankId}/{transactionDate}/{fromAuthCode}/{limit}")
	public List<CreditCardTransaction> getTransactionsByAuthCodes(@PathVariable("bankId") String bankId,
																  @PathVariable("transactionDate") Date transactionDate,
																  @PathVariable("fromAuthCode") int fromAuthCode,
																  @PathVariable("limit") int limit);
}
